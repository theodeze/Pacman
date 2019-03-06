package fr.univangers.pacman.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.game.PacmanGame;

public class PacmanServer extends PacmanGame {

	private static final long serialVersionUID = -3170763958222800378L;
	private static final Logger LOGGER = LogManager.getLogger("Server");
	
	private Socket so;
	private String token;
	
	public PacmanServer(PacmanGameSettings settings, Maze maze, Socket so, String token) {
		super(settings, maze);
		this.so = so;
		this.token = token;
	}
	
	@Override
	public void gameOver() {
		super.gameOver();
		PostRequest.sendPartie(state, token);
	}
	
	@Override
	public void notifyViews() {
		if(so != null && !so.isClosed()) {
			try {
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				output.println(state.toJson());
				state.setCurrentSong("");
			} catch (IOException e) {
				LOGGER.warn("Erreur envoie");
			}
		}
	}
}
