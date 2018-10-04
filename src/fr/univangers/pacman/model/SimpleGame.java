package fr.univangers.pacman.model;

public class SimpleGame extends Game {

	private static final long serialVersionUID = -2434019721506786915L;

	public SimpleGame(int maxTurn) {
		super(maxTurn);
	}

	@Override
	public void initializeGame() {
		System.out.println("Initialize Game");
	}

	@Override
	public void takeTurn() {
		System.out.println("Take Turn");
	}

	@Override
	public void gameOver() {
		System.out.println("Game Over");
	}

}
