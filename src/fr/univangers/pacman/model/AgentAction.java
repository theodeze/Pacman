package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface AgentAction représente toutes les actions possibles pour un agent
 */
public interface AgentAction extends Serializable {
	
	public void move(List<Position> positionPacmans, List<Position> positionGhosts, List<Position> positionFoods, 
			List<List<Boolean>> walls);
	public void goUp();
	public void goLeft();
	public void goDown();
	public void goRight();

}
