package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 *	Stratégie où l'agent ne bouge pas
 */

public class NoneStrategy implements Strategy {

	private static final long serialVersionUID = 973545336472026490L;

	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {
		// Ne fait rien !
	}

}
