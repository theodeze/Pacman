package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.List;

public interface AgentAction extends Serializable {
	
	public void move(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls);
	public void goUp();
	public void goLeft();
	public void goDown();
	public void goRight();

}
