package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;

public class EscapeStrategy implements Strategy {

	private static final long serialVersionUID = 4316826769530063482L;

	private int averageDistancePacman(PositionAgent position, List<PositionAgent> positionPacmans) {
		int averageDistance = 0;
		for(PositionAgent positionPacman : positionPacmans) {
			averageDistance += Math.abs(position.getX() - positionPacman.getX()) 
					+ Math.abs(position.getY() - positionPacman.getY());
		}
		return averageDistance;
	}
	
	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {
		PositionAgent position = agent.position();
		PositionAgent testPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		int currentAverageDistance = averageDistancePacman(position, enemies);
		int testAverageDistancePacman = 0;
		
		if(!walls[position.getX() + 1][position.getY()]) {
			testPosition.setX(position.getX() + 1);
			testPosition.setY(position.getY());
			testAverageDistancePacman = averageDistancePacman(testPosition, enemies);
			if(currentAverageDistance < testAverageDistancePacman) {
				currentAverageDistance = testAverageDistancePacman;
				newPosition = testPosition;
				newPosition.setDir(Dir.EAST);
			}
		}
		
		if(!walls[position.getX()][position.getY() - 1]) {
			testPosition.setX(position.getX());
			testPosition.setY(position.getY() - 1);
			testAverageDistancePacman = averageDistancePacman(testPosition, enemies);
			if(currentAverageDistance < testAverageDistancePacman) {
				currentAverageDistance = testAverageDistancePacman;
				newPosition = testPosition;
				newPosition.setDir(Dir.NORTH);
			}
		}
		
		if(!walls[position.getX()][position.getY() + 1]) {
			testPosition.setX(position.getX());
			testPosition.setY(position.getY() + 1);
			testAverageDistancePacman = averageDistancePacman(testPosition, enemies);
			if(currentAverageDistance < testAverageDistancePacman) {
				currentAverageDistance = testAverageDistancePacman;
				newPosition = testPosition;
				newPosition.setDir(Dir.SOUTH);
			}
		}
		
		if(!walls[position.getX() - 1][position.getY()]) {
			testPosition.setX(position.getX() - 1);
			testPosition.setY(position.getY());
			testAverageDistancePacman = averageDistancePacman(testPosition, enemies);
			if(currentAverageDistance < testAverageDistancePacman) {
				newPosition = testPosition;
				newPosition.setDir(Dir.WEST);
			}	
		}
		
		agent.setPosition(newPosition);
	}

}
