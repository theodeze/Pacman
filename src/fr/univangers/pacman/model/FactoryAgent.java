package fr.univangers.pacman.model;

import fr.univangers.pacman.model.strategy.AstarEscapeStrategy;
import fr.univangers.pacman.model.strategy.AstarAttackStrategy;
import fr.univangers.pacman.model.strategy.EscapeStrategy;
import fr.univangers.pacman.model.strategy.TrackingStrategy;
import fr.univangers.pacman.model.strategy.NearestAttackStrategy;
import fr.univangers.pacman.model.strategy.NoneStrategy;
import fr.univangers.pacman.model.strategy.PlayerStrategy;
import fr.univangers.pacman.model.strategy.RandomStrategy;


/**
 * La classe FactoryAgent permet de créer un Agent de chaque type et
 * d'implémenter une stratégie à chaque agent correspondant (elles
 * peuvent dépendre du hasard, de l'algorithme A*, ou permettre la
 * fuite)
 */
public class FactoryAgent {

	private FactoryAgent() {
	    throw new IllegalStateException("Factory class");
	}	
	
	public static Agent createPacmanBasic(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new NearestAttackStrategy(), new EscapeStrategy());
		return agt;
	}	
	
	public static Agent createPacmanNone(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new NoneStrategy(), new NoneStrategy());
		return agt;
	}
	
	public static Agent createGhostNone(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new NoneStrategy(), new NoneStrategy());
		return agt;
	}
	
	public static Agent createGhostBasic(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new NearestAttackStrategy(), new EscapeStrategy());
		return agt;
	}
	
	public static Agent createPacmanPlayer(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new PlayerStrategy(), new PlayerStrategy());
		return agt;
	}

	public static Agent createGhostPlayer(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new PlayerStrategy(), new PlayerStrategy());
		return agt;
	}
	
	public static Agent createPacmanRandom(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new RandomStrategy(), new RandomStrategy());
		return agt;
	}
	
	public static Agent createGhostRandom(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new RandomStrategy(), new RandomStrategy());
		return agt;
	}
	
	public static Agent createPacmanAstar(PositionAgent position) {
		Agent agt = new Agent(Agent.Type.PACMAN, position);
		agt.setStrategy(new AstarAttackStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
	public static Agent createGhostTracking(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new TrackingStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
	public static Agent createGhostAstar(Position position) {
		Agent agt = new Agent(Agent.Type.GHOST, position);
		agt.setStrategy(new AstarAttackStrategy(), new AstarEscapeStrategy());
		return agt;
	}
	
}
