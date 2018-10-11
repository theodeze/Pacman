package fr.univangers.pacman.controller;

import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.SimpleGame;

public class SimpleGameController implements GameController {

	private static final long serialVersionUID = -8685945274791593960L;
	private SimpleGame simpleGame;
	
	public SimpleGameController(SimpleGame simpleGame) {
		this.simpleGame = simpleGame;
	}

	@Override
	public void setTime(int time) {
		simpleGame.setTime(time);
	}
	
	@Override
	public void pause() {
		simpleGame.stop();
	}

	@Override
	public void restart() {
		simpleGame.init();
	}

	@Override
	public void run() {
		simpleGame.launch();
	}

	@Override
	public void step() {
		simpleGame.step();
	}

	@Override
	public void movePlayer1(Dir dir) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void movePlayer2(Dir dir) {
		throw new UnsupportedOperationException();
	}

}
