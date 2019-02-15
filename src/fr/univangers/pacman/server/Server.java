package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanServerController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameGetter.Mode;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyGhost;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyPacman;
import fr.univangers.pacman.model.PacmanServer;
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
					if(!connect(so))
						so.close();
					launchPacmanGame(so);
				} catch (Exception e) {
					LOGGER.warn("Problème lors du lancement du pacman");
				}
			}
		} catch(IOException e) {
			LOGGER.error("Serveur à crash");
		}
	}
	
	private boolean connect(Socket so) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
	        String username = input.readLine();
	        String password = input.readLine();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	private void launchPacmanGame(Socket so) throws Exception {
		Maze maze = new Maze("res/layouts/contestClassic.lay");
		PacmanServer ps = new PacmanServer(250, maze, StrategyPacman.ASTAR, StrategyGhost.TRACKING, Mode.AUTO, so);
		PacmanServerController psc = new PacmanServerController(ps, so);
		new Thread(psc).start();
	}
	
}
