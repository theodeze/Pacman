package fr.univangers.pacman.controller;

import java.io.Serializable;

public interface GameController extends Serializable {

	public void setTime(int time);
	public void pause();
	public void restart();
	public void run();
	public void step();
	
}
