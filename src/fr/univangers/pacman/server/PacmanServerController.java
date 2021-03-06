package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.game.PacmanGame;

public class PacmanServerController extends PacmanGameController implements Runnable {

	private static final long serialVersionUID = -3141416675700419097L;
	private static final Logger LOGGER = LogManager.getLogger("Server"); 
	private Socket so;
	
	public PacmanServerController(PacmanGame pacmanGame, Socket so) {
		super(pacmanGame);
		this.so = so;
	}

	public void newCommande() {
		try {
			BufferedReader input = new BufferedReader(
					new InputStreamReader(so.getInputStream()));
			String cmd = input.readLine();
			if(cmd == null)
				throw new IOException();
			LOGGER.info("Commande " + cmd + " reçus");
			switch(Commande.valueOf(cmd)) {
			case MOVE1:
				movePlayer1(Dir.valueOf(input.readLine()));
				break;
			case MOVE2:
				movePlayer2(Dir.valueOf(input.readLine()));
				break;
			case PAUSE:
				pause();
				break;
			case RESTART:
				restart();
				break;
			case RUN:
				launch();
				break;
			case STEP:
				step();
				break;
			case TIME:
				setTime(Integer.valueOf(input.readLine()));
				break;
			default:
				LOGGER.warn("Commande incompris " + cmd);
				break;
			}
		} catch (IOException e) {
			try {
				so.close();
			} catch (IOException e1) {
				LOGGER.warn("Connexion perdus");
			}
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Commande incomprise");
		}
	}

	@Override
	public void run() {
		while(!so.isClosed()) {
			newCommande();
		}
	}
	
}
