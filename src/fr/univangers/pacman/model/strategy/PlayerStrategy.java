package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 * Stratégie correspondant au contrôle par un utilisateur 
 */

public class PlayerStrategy implements Strategy {

	private static final long serialVersionUID = -3421485434310896297L;

	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {		PositionAgent position = agent.position();
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		switch(position.getDir()) {
		case EAST:
			newPosition.setX(position.getX() + 1);
			break;
		case NORTH:
			newPosition.setY(position.getY() - 1);
			break;
		case SOUTH:
			newPosition.setY(position.getY() + 1);
			break;
		case WEST:
			newPosition.setX(position.getX() - 1);
			break;
		default:
			break;	
		}
		agent.setPosition(walls[newPosition.getX()][newPosition.getY()] ? position : newPosition);
	}

}
