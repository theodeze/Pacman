package fr.univangers.pacman.server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.gamestate.PacmanGameState.Mode;
import fr.univangers.pacman.model.gamestate.PacmanGameState.StrategyGhost;
import fr.univangers.pacman.model.gamestate.PacmanGameState.StrategyPacman;

public class Server implements Runnable {

	private ServerSocket sso;
	private static final Logger LOGGER = LogManager.getLogger("Server"); 
	private static final String TITLE = "Serveur Pacman";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final int MAX_TRY = 3;

	private Server(ServerSocket sso) {
		JFrame frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setSize(new Dimension(360, 80));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1,1));

        Dimension windowSize = frame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;
        frame.setLocation(dx, dy); 
        
        frame.add(new JLabel("Le seveur écoute sur le port " + sso.getLocalPort(), SwingConstants.CENTER));
        frame.setVisible(true);
		
		this.sso = sso;
	}
	
	public static Server getInstance() {
		Server server = null;
		int nTry = MAX_TRY;
		while(server == null && nTry-- > 0) {
			try {
				server = new Server(new ServerSocket(whichPort()));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Le seveur n'a pas démarre\n" + e.getLocalizedMessage(), 
						TITLE, JOptionPane.ERROR_MESSAGE);
				LOGGER.warn("Le seveur n'a pas démarre");
			}
		}
		return server;
	}
	
	private static int whichPort() {
	    JPanel panel = new JPanel(new BorderLayout(5, 5));
	    panel.add(new JLabel("Paramètre du serveur", SwingConstants.RIGHT), BorderLayout.NORTH);

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("Port", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2)); 
	   
	    JSpinner port = new JSpinner(new SpinnerNumberModel(4400,0,65535,1));
	    controls.add(port);
	    
	    panel.add(controls, BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(new JFrame(), panel, TITLE, JOptionPane.QUESTION_MESSAGE);

	    return (int) port.getValue();
	}
	
	@Override
	public void run() {
		try {
			while(!sso.isClosed()) {
				Socket so = sso.accept();
				String token = connect(so);
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				if(token.isEmpty()) {
					output.println(false);
					so.close();
				} else {
					output.println(true);
					launchPacmanGame(so, token);
				}
			}
		} catch(IOException e) {
			LOGGER.error("Serveur à crash");
		}
	}
	
	private String connect(Socket so) {
		String token = "";
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
	        String username = input.readLine();
	        String password = input.readLine();
	        
	        String url = "http://localhost:8080/Pacman_Score/Authentification";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			String urlParameters = 
					  "username=" + username + "&"
					+ "password=" + password;
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			if(responseCode == 200) {
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

		        LOGGER.info(token);
				token = new Gson().fromJson(response.toString(), String.class);
			} else {
				LOGGER.warn("Mots de passe ou identifiant incorrect");
			}
		} catch (IOException e) {
			LOGGER.warn("Erreur lecture mot de passe/identifiant");
		}
		return token;
	}
	
	private void launchPacmanGame(Socket so, String token) {
		try {
			Maze maze = new Maze("res/layouts/bigSearch_onePacman_oneGhost.lay");
			PacmanServer ps = new PacmanServer(250, maze, StrategyPacman.ASTAR, StrategyGhost.TRACKING, Mode.AUTO, so, token);
			PacmanServerController psc = new PacmanServerController(ps, so);
			new Thread(psc).start();
		} catch (Exception e) {
			LOGGER.warn("Problème lors du lancement du pacman");
		}
	}
	
}
