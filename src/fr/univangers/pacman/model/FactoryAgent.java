package fr.univangers.pacman.model;

import fr.univangers.pacman.model.strategy.AstarEscapeStrategy;
import fr.univangers.pacman.model.strategy.AstarStrategy;
import fr.univangers.pacman.model.strategy.EscapeStrategy;
import fr.univangers.pacman.model.strategy.ExplorerStrategy;
import fr.univangers.pacman.model.strategy.NoneStrategy;
import fr.univangers.pacman.model.strategy.PlayerStrategy;
import fr.univangers.pacman.model.strategy.RandomStrategy;


/**
 * La classe FactoryAgent permet de créer un Agent de chaque type et
 * d'implémenter une stratégie à chaque agent correspondant (elles
 * peuvent dépendre du hasard, de l'algorithme A*, ou permettre la
 * fuite)
 *
 */

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
		agt.setStrategy(new AstarStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
	public static Agent createGhostExplorer(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new ExplorerStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
	public static Agent createGhostAstar(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new AstarStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
}
