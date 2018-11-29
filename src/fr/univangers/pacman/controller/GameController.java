package fr.univangers.pacman.controller;

import java.io.Serializable;

import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Interface de PacmanGameController
 */

public interface GameController extends Serializable {
	
	public void setTime(int time);
	public void pause();
	public void restart();
	public void run();
	public void step();
	
	public void movePlayer1(Dir dir);
	public void movePlayer2(Dir dir);
	
}
