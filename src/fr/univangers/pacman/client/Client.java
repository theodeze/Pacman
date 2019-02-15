package fr.univangers.pacman.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanClientController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanClient;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameGetter.Mode;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyGhost;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyPacman;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class Client implements Runnable {

	private Socket so;
	private static final Logger LOGGER = LogManager.getLogger("Client"); 
	
	private Client(Socket so) {
		this.so = so;
	}
	
	public static Client getInstance(String host, int port) {
		Client client = null;
		try {
			Socket so = new Socket(host, port);
			connect(so, "user", "password");
			client = new Client(so);
		} catch (UnknownHostException e) {
			LOGGER.warn("Hote n'existe pas");
		} catch (IOException e) {
			LOGGER.warn("Connexion refusé");
		}
		return client;
	}
	
	private static boolean connect(Socket so, String username, String password) {
		try {
			PrintWriter output = new PrintWriter(so.getOutputStream(), true);
			output.println(username);
	        output.println(password);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void run() {
		try {
			PacmanClientController pcc = new PacmanClientController(so);
			Maze maze = new Maze("res/layouts/contestClassic.lay");
			PacmanClient game = new PacmanClient(999, so);
			ViewCommande vc = new ViewCommande(game);
			vc.setGameController(pcc);
			new ViewGame(game, pcc, maze);
			game.launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
