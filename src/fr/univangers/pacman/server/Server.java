package fr.univangers.pacman.server;

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
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.LoginInformation;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.beans.ServerInformation;
import fr.univangers.pacman.view.dialog.DialogServer;

public class Server implements Runnable {
	private static final Logger LOGGER = LogManager.getLogger("Server"); 
	private static final String TITLE = "Serveur Pacman";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final int MAX_TRY = 3;
	private ServerSocket sso;
	private ServerInformation serverInformation;

	private Server(ServerSocket sso, ServerInformation serverInformation) {
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
		this.serverInformation = serverInformation;
	}
	
	public static Server getInstance() {
		Server server = null;
		int nTry = MAX_TRY;
		while(server == null && nTry-- > 0) {
			try {
				ServerInformation serverInformation = DialogServer.show();
				server = new Server(new ServerSocket(serverInformation.getPort()), serverInformation);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Le seveur n'a pas démarre\n" + e.getLocalizedMessage(), 
						TITLE, JOptionPane.ERROR_MESSAGE);
				LOGGER.warn("Le seveur n'a pas démarre");
			}
		}
		return server;
	}
	
	@Override
	public void run() {
		try {
			while(!sso.isClosed()) {
				Socket so = sso.accept();
				String token = connect(so);
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
				if(token.isEmpty() && serverInformation.isNeedAuthentication()) {
					output.println(false);
					so.close();
				} else {
					output.println(true);
					PacmanGameSettings settings = PacmanGameSettings.fromJson(input.readLine());
					launchPacmanGame(so, token, settings);
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
			LoginInformation login = LoginInformation.fromJson(input.readLine());
	        
	        String url = "http://localhost:8080/Pacman_Score/Authentification";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			
			String urlParameters = 
					  "username=" + login.getUsername() + "&"
					+ "password=" + login.getPassword();
			
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
	
	private void launchPacmanGame(Socket so, String token, PacmanGameSettings settings) {
		try {
			Maze maze = new Maze(settings.getNameMaze());
			PacmanServer ps = new PacmanServer(settings, maze, so, token);
			PacmanServerController psc = new PacmanServerController(ps, so);
			new Thread(psc).start();
		} catch (Exception e) {
			LOGGER.warn("Problème lors du lancement du pacman");
		}
	}
	
}
