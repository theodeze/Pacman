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
		PacmanGame pg = new PacmanGame(250, new Maze(""), StrategyPacman.ASTAR, StrategyGhost.TRACKING, Mode.ONEPLAYER);
		PacmanServerController psc = new PacmanServerController(pg, so);
		new Thread(psc).start();
	}
	
}
