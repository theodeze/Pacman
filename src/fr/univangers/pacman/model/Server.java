package fr.univangers.pacman.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanServerController;
import fr.univangers.pacman.model.PacmanGame.Mode;
import fr.univangers.pacman.model.PacmanGame.StrategyGhost;
import fr.univangers.pacman.model.PacmanGame.StrategyPacman;
import fr.univangers.pacman.view.ViewGame;

public class Server implements Runnable {

	private ServerSocket sso;
	private final static Logger LOGGER = LogManager.getLogger("Server"); 

	private Server(ServerSocket sso) {
		this.sso = sso;
	}
	
	public static Server getInstance(int port) {
		Server server = null;
		try {
			server = new Server(new ServerSocket(port));
		} catch (IOException e) {
			LOGGER.warn("Le seveur n'a pas démarre");
		}
		return server;
	}
	
	@Override
	public void run() {
		try {
			while(!sso.isClosed()) {
				Socket so = sso.accept();
				try {
					launchPacmanGame(so);
				} catch (Exception e) {
					LOGGER.warn("Problème lors du lancement du pacman");
				}
			}
		} catch(IOException e) {
			LOGGER.error("Serveur à crash");
		}
	}
	
	private void launchPacmanGame(Socket so) throws Exception {
		Maze maze = new Maze("res/layouts/bigMaze.lay");
		PacmanGame pg = new PacmanGame(250, maze, StrategyPacman.ASTAR, StrategyGhost.TRACKING, Mode.ONEPLAYER);
		PacmanServerController psc = new PacmanServerController(pg, so);
		ViewGame vg = new ViewGame(pg, psc, maze);
		new Thread(psc).start();
	}
	
}
