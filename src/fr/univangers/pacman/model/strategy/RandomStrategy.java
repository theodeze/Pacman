package fr.univangers.pacman.model.strategy;

import java.util.List;
import java.util.Random;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Stratégie de déplacement qui choisi une nouvelle position aléatoire parmi celle disponible
 */
public class RandomStrategy implements Strategy {

	private static final long serialVersionUID = 2758435520227921703L;
	private Random random = new Random();

	@Override
	public void move(Agent agent, List<Position> targets, List<Position> friends, 
			List<Position> enemies, List<List<Boolean>> walls) {
		PositionAgent position = agent.position();
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		do {
			switch(random.nextInt(4)) {
			case 0:
				newPosition.setX(position.getX() + 1);
				newPosition.setY(position.getY());
				newPosition.setDir(Dir.EAST);
				break;
			case 1:
				newPosition.setX(position.getX());
				newPosition.setY(position.getY() - 1);
				newPosition.setDir(Dir.NORTH);
				break;
			case 2:
				newPosition.setX(position.getX());
				newPosition.setY(position.getY() + 1);
				newPosition.setDir(Dir.SOUTH);
				break;
			case 3:
				newPosition.setX(position.getX() - 1);
				newPosition.setY(position.getY());
				newPosition.setDir(Dir.WEST);
				break;
			default:
				break;
			}
		} while(walls.get(newPosition.getY()).get(newPosition.getX()));
		agent.setPosition(newPosition);
	}

}
