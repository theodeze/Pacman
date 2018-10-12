package fr.univangers.pacman.model.strategy;

import java.io.Serializable;

import fr.univangers.pacman.model.PositionAgent;

public interface Strategy extends Serializable {

	public PositionAgent move(PositionAgent position, boolean[][] walls);
	
}
