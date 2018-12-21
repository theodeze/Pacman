package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 * Stratégie de fuites
 */
public class EscapeStrategy implements Strategy {
	
	private static final long serialVersionUID = 4316826769530063482L;
	
	/**
	 * Calcul des distances vis-à-vis des enemies pour s'en éloigner le plus possible
	 */
	private int averageDistanceEnemies(PositionAgent position, List<PositionAgent> enemies) {
		int averageDistance = 0;
		for(PositionAgent enemie : enemies) {
			averageDistance += Math.abs(position.getX() - enemie.getX()) 
					+ Math.abs(position.getY() - enemie.getY());
		}
		return averageDistance;
	}
	
	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {
		PositionAgent position = agent.position();
		int currentAverageDistance = averageDistanceEnemies(position, enemies);
		int testAverageDistancePacman = 0;
		
		PositionAgent newPosition = agent.position();
		
		List<PositionAgent> positions = new ArrayList<>();
		PositionAgent cp1 = new PositionAgent(newPosition);
		cp1.setX(cp1.getX() + 1);
		positions.add(cp1);
		PositionAgent cp2 = new PositionAgent(newPosition);
		cp2.setX(cp2.getX() - 1);
		positions.add(cp2);
		PositionAgent cp3 = new PositionAgent(newPosition);
		cp3.setY(cp3.getY() + 1);
		positions.add(cp3);
		PositionAgent cp4 = new PositionAgent(newPosition);
		cp4.setY(cp4.getY() - 1);
		positions.add(cp4);
		for(PositionAgent currentPosition : positions)	
			if(!walls[position.getX()][position.getY()]) {
				testAverageDistancePacman = averageDistanceEnemies(currentPosition, enemies);
				if(currentAverageDistance < testAverageDistancePacman) {
					currentAverageDistance = testAverageDistancePacman;
					newPosition = currentPosition;
				}
			}
	
		
		agent.setPosition(newPosition);
	}

}
