package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;

/**
 *	Stratégie où l'agent ne bouge pas
 */

public class NoneStrategy implements Strategy {

	private static final long serialVersionUID = 973545336472026490L;

	@Override
	public void move(Agent agent, List<Position> targets, List<Position> friends, 
			List<Position> enemies, List<List<Boolean>> walls) {
		// Ne fait rien !
	}

}
