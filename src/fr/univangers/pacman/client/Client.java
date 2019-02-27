package fr.univangers.pacman.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class Client implements Runnable {

	private Socket so;
	private static final Logger LOGGER = LogManager.getLogger("Client"); 
	private static final int MAX_TRY = 3;
	private static final String TITLE = "Client Pacman";
	
	private Client(Socket so) {
		this.so = so;
	}
	
	public static Client getInstance() {
		Client client = null;
		int nTry = MAX_TRY;
		boolean connect = false;
		int noRetry = JOptionPane.YES_OPTION;
		while(nTry-- > 0 && !connect && noRetry == JOptionPane.YES_OPTION) {
			try {
				Map<String, String> logininformation = login();
				Socket so = new Socket(logininformation.get("host"), Integer.valueOf(logininformation.get("port")));
				connect = connect(so, logininformation.get("user"), logininformation.get("pass"));
		        if(!connect) {
		        	noRetry = displayMessage(nTry, "Mots de passe/Identifiant incorect");
		        } else {
		        	client = new Client(so);
		        }
			} catch (UnknownHostException e) {
				noRetry = displayMessage(nTry, "Hote n'existe pas");
				LOGGER.warn("Hote n'existe pas");
			} catch (IOException e) {
				noRetry = displayMessage(nTry, "Connexion refusé");
				LOGGER.warn("Connexion refusé");
			}
		}
		return client;
	}
	
	private static int displayMessage(int nTry, String message) {
		int result = JOptionPane.NO_OPTION;
		if(nTry > 0)
			result = JOptionPane.showConfirmDialog(new JFrame(), 
			new JLabel(message + ". Voulez-vous réessayer ?"), TITLE, 
			JOptionPane.YES_NO_OPTION,
			JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(new JFrame(), 
	    	new JLabel(message), TITLE, 
	    	JOptionPane.ERROR_MESSAGE);
		return result;
	}
	
	private static Map<String, String> login() {
	    Map<String, String> logininformation = new HashMap<>();

	    JPanel panel = new JPanel(new BorderLayout(5, 5));
	    panel.add(new JLabel("Paramètre du client", SwingConstants.RIGHT), BorderLayout.NORTH);

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("Adresse hôte", SwingConstants.RIGHT));
	    label.add(new JLabel("Port", SwingConstants.RIGHT));
	    label.add(new JLabel("Pseudo", SwingConstants.RIGHT));
	    label.add(new JLabel("Mot de passe", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2)); 
	    JTextField host = new JTextField();
	    host.setText("localhost");
	    controls.add(host);
	   
	    JTextField port = new JTextField();
	    port.setText("4400");
	    controls.add(port);
	    
	    JTextField username = new JTextField();
	    controls.add(username);
	    
	    JPasswordField password = new JPasswordField();
	    controls.add(password);
	    
	    panel.add(controls, BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(new JFrame(), panel, TITLE, JOptionPane.QUESTION_MESSAGE);

	    logininformation.put("host", host.getText());
	    logininformation.put("port", port.getText());
	    logininformation.put("user", username.getText());
	    logininformation.put("pass", new String(password.getPassword()));
	    return logininformation;
	}
	
	private static boolean connect(Socket so, String user, String pass) {
		boolean result = false;
		try {
			PrintWriter output = new PrintWriter(so.getOutputStream(), true);
			output.println(user);
	        output.println(pass);
			BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
	        result = Boolean.valueOf(input.readLine());
		} catch (IOException e) {
			return false;
		}
		return result;
	}

	@Override
	public void run() {
		try {
			PacmanClientController pcc = new PacmanClientController(so);
			Maze maze = new Maze("res/layouts/bigSearch_onePacman_oneGhost.lay");
			PacmanClient game = new PacmanClient(999, so);
			ViewCommande vc = new ViewCommande(game);
			vc.setGameController(pcc);
			new ViewGame(game, pcc, maze);
			game.launch();
		} catch (Exception e) {
			LOGGER.warn("Problème lancement pacman");
		}
	}
	
}
