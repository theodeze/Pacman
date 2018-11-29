package fr.univangers.pacman.model.strategy;

import java.io.Serializable;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 *	Interface Strategy utilisée dans toutes les stratégies 
 */

public interface Strategy extends Serializable {
	
	/**
	 * Permet le déplacement de l'agent en fonction des murs et de la position des agents alliés/ennemies
	 */
	public void move(Agent agent, List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls);
	
}
