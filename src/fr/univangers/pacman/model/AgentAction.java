package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface de la classe Agent
 *
 */

public interface AgentAction extends Serializable {
	
	public void move(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, List<PositionAgent> positionFoods, boolean[][] walls);
	public void goUp();
	public void goLeft();
	public void goDown();
	public void goRight();

}
