package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 *	Stratégie utilisée lorsque les fantômes sont morts 
 *	(ne sont pas encore revenus à la vie)
 */

public class NoneStrategy implements Strategy {

	private static final long serialVersionUID = 973545336472026490L;

	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {
		// Ne fait rien !
	}

}
