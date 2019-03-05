package fr.univangers.pacman.model.strategy;

import java.util.Collections;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;

/**
 * Stratégie de l'aglgorithme A* attaque
 */
public class AstarAttackStrategy extends AstarStrategy {

	private static final long serialVersionUID = -4845423502376948832L;

	@Override
	public void move(Agent agent, List<Position> targets, List<Position> friends, 
			List<Position> enemies, List<List<Boolean>> walls) {
		if(targets.isEmpty()) {
			return;
		}
		Position newPosition = findPath(agent.position(), targets, friends, enemies, walls).getValue();
		if(newPosition.equals(agent.position())) {
			newPosition  = findPath(agent.position(), targets, Collections.emptyList(), enemies, walls).getValue();
		}
        agent.setPosition(newPosition);
	}
}
