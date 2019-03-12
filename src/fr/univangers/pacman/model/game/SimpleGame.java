package fr.univangers.pacman.model.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleGame extends Game {

	private static final long serialVersionUID = -2434019721506786915L;
	private static final Logger LOGGER = LogManager.getLogger("Game"); 

	public SimpleGame(int maxTurn) {
		super(maxTurn);
	}

	@Override
	public void initializeGame() {
		LOGGER.info("Initialize Game");
	}

	@Override
	public void takeTurn() {
		LOGGER.info("Take Turn");
	}

	@Override
	public void gameOver() {
		LOGGER.info("Game Over");
	}

}
