package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Première stratégie implémentée
 */

public class NearestAttackStrategy implements Strategy {

	private static final long serialVersionUID = 1704735151191179215L;

	private Position nearest(PositionAgent position, List<Position> targets) {
		if(targets.isEmpty())
			return position;
		
		Position nearest = targets.get(0);
		int currentDistance = Math.abs(position.getX() - nearest.getX()) 
				+ Math.abs(position.getY() - nearest.getY());
		int testDistance = 0;
		
		for(Position target : targets) {
			testDistance = Math.abs(position.getX() - target.getX()) 
					+ Math.abs(position.getY() - target.getY());
			if(testDistance < currentDistance) {
				currentDistance = testDistance;
				nearest = target;
			}
		}
		
		return nearest;
	}
	
	@Override
	public void move(Agent agent, List<Position> targets, List<Position> friends, 
			List<Position> enemies, List<List<Boolean>> walls) {
		PositionAgent position = agent.position();
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		Position target = nearest(position, targets);
		
		if((target.getX() > position.getX()) && (!walls.get(position.getY()).get(position.getX() + 1))) {
			newPosition.setX(position.getX() + 1);
			newPosition.setY(position.getY());
			newPosition.setDir(Dir.EAST);
		} else if ((target.getY() < position.getY()) && (!walls.get(position.getY() - 1).get(position.getX()))) {
			newPosition.setX(position.getX());
			newPosition.setY(position.getY() - 1);
			newPosition.setDir(Dir.NORTH);
		} else if ((target.getY() > position.getY()) && (!walls.get(position.getY() + 1).get(position.getX()))) {
			newPosition.setX(position.getX());
			newPosition.setY(position.getY() + 1);
			newPosition.setDir(Dir.SOUTH);
		} else if ((target.getX() < position.getX()) && (!walls.get(position.getY()).get(position.getX() - 1))) {
			newPosition.setX(position.getX() - 1);
			newPosition.setY(position.getY());
			newPosition.setDir(Dir.WEST);
		}
		
		agent.setPosition(newPosition);
	}

}
