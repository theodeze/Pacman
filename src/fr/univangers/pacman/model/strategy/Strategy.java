package fr.univangers.pacman.model.strategy;

import java.io.Serializable;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

public interface Strategy extends Serializable {

	public void move(Agent agent, List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls);
	
}
