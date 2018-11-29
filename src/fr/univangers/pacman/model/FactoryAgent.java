package fr.univangers.pacman.model;

import fr.univangers.pacman.model.strategy.AstarStrategy;
import fr.univangers.pacman.model.strategy.EscapeStrategy;
import fr.univangers.pacman.model.strategy.PlayerStrategy;
import fr.univangers.pacman.model.strategy.RandomStrategy;

public class FactoryAgent {

	private FactoryAgent() {
	    throw new IllegalStateException("Factory class");
	}	
	
	public static Agent createPacmanPlayer(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new PlayerStrategy(), new PlayerStrategy());
		return agt;
	}

	public static Agent createGhostPlayer(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new PlayerStrategy(), new PlayerStrategy());
		return agt;
	}
	
	public static Agent createPacmanRandom(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new RandomStrategy(), new RandomStrategy());
		return agt;
	}
	
	public static Agent createGhostRandom(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new RandomStrategy(), new RandomStrategy());
		return agt;
	}
	
	public static Agent createPacmanAstar(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new AstarStrategy(), new EscapeStrategy());
		return agt;
	}
	
	public static Agent createGhostAstar(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new AstarStrategy(), new EscapeStrategy());
		return agt;
	}
	
}
