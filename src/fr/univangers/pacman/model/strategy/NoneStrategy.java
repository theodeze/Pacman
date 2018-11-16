package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

public class NoneStrategy implements Strategy {

	private static final long serialVersionUID = 973545336472026490L;

	@Override
	public void move(Agent agent, List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		//
	}

}
