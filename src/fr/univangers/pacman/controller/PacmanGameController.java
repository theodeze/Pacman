package fr.univangers.pacman.controller;

import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanGameController implements GameController {
	
	private static final long serialVersionUID = 7744355889303690019L;
	private PacmanGame pacmanGame;
	
	public PacmanGameController(PacmanGame pacmanGame) {
		this.pacmanGame = pacmanGame;
	}

	@Override
	public void setTime(int time) {
		pacmanGame.setTime(time);
	}
	
	@Override
	public void pause() {
		pacmanGame.stop();
	}

	@Override
	public void restart() {
		pacmanGame.init();
	}

	@Override
	public void run() {
		pacmanGame.launch();
	}

	@Override
	public void step() {
		pacmanGame.step();
	}

	@Override
	public void movePlayer1(Dir dir) {
		pacmanGame.movePacmanPlayer1(dir);
	}

	@Override
	public void movePlayer2(Dir dir) {
		pacmanGame.movePacmanPlayer1(dir);
	}


}
