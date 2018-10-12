package fr.univangers.pacman.model;

import java.io.Serializable;

public interface AgentAction extends Serializable {
	
	public void move(boolean[][] walls);
	public void goUp();
	public void goLeft();
	public void goDown();
	public void goRight();

}
