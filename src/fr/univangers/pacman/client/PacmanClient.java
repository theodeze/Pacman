package fr.univangers.pacman.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import fr.univangers.pacman.model.game.Game;
import fr.univangers.pacman.model.gamestate.PacmanGameState;

public class PacmanClient extends Game {
	
	private static final long serialVersionUID = -5821764757690849185L;
	private static final Logger LOGGER = LogManager.getLogger("Client"); 
	private Socket so;
	private PacmanGameState state = new PacmanGameState();
	
	public PacmanClient(int maxTurn, Socket so) {
		super(maxTurn);
		this.so = so;
	}
	
	@Override
	public void run() {
		while(!so.isClosed()) {
			readData();
			notifyViews();
		}
	}
	
	private void readData() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
			String data = input.readLine();
			state = new Gson().fromJson(data, PacmanGameState.class);
		} catch (IOException e) {
			LOGGER.warn("Données incorrectes");
		}
	}

	@Override
	public PacmanGameState getState() {
		return state;
	}

	@Override
	public void initializeGame() {
		/* USELESS */
	}

	@Override
	public void takeTurn() {
		/* USELESS */
	}

	@Override
	public void gameOver() {
		/* USELESS */
	}
	
}
