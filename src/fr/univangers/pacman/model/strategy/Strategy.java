package fr.univangers.pacman.model.strategy;

import java.io.Serializable;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;

/**
 *	Interface Strategy utilisée dans toutes les stratégies 
 */

public interface Strategy extends Serializable {


	/**
	 * Permet le déplacement de l'agent en fonction des murs et de la position des agents alliés/ennemies
	 */
	public void move(Agent agent, List<Position> targets, List<Position> friends, List<Position> enemies, List<List<Boolean>> walls);
	
}
