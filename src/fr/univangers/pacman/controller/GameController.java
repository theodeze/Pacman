package fr.univangers.pacman.controller;

import java.io.Serializable;

import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Interface GameController
 */
public interface GameController extends Serializable {

	public enum Commande {
		TIME,
		PAUSE,
		RESTART,
		RUN,
		STEP,
		MOVE1,
		MOVE2
	}
	
	public void setTime(int time);
	public void pause();
	public void restart();
	public void launch();
	public void step();
	
	public void movePlayer1(Dir dir);
	public void movePlayer2(Dir dir);
	
}
