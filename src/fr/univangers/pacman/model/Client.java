package fr.univangers.pacman.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanClientController;
import fr.univangers.pacman.model.PacmanGame.Mode;
import fr.univangers.pacman.model.PacmanGame.StrategyGhost;
import fr.univangers.pacman.model.PacmanGame.StrategyPacman;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class Client implements Runnable {

	private Socket so;
	private final static Logger LOGGER = LogManager.getLogger("Client"); 
	
	private Client(Socket so) {
		this.so = so;
	}
	
	public static Client getInstance(String host, int port) {
		Client client = null;
		try {
			Socket so = new Socket(host, port);
			client = new Client(so);
		} catch (UnknownHostException e) {
			LOGGER.warn("Hote n'existe pas");
		} catch (IOException e) {
			LOGGER.warn("Connexion refusé");
		}
		return client;
	}

	@Override
	public void run() {
		try {
			PacmanClientController pcc = new PacmanClientController(so);
			Maze maze = new Maze("res/layouts/bigMaze.lay");
			PacmanGame game = new PacmanGame(999, maze, StrategyPacman.ASTAR, StrategyGhost.BASIC,  Mode.ONEPLAYER);
			ViewCommande vc = new ViewCommande(game);
			vc.setGameController(pcc);
			new ViewGame(game, pcc, maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
