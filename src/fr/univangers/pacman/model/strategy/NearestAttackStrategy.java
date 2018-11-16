package fr.univangers.pacman.model.strategy;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;

public class NearestAttackStrategy implements Strategy {

	private static final long serialVersionUID = 1704735151191179215L;

	private PositionAgent nearest(PositionAgent position, List<PositionAgent> positionPacmans) {
		if(positionPacmans.isEmpty())
			return position;
		
		PositionAgent nearest = positionPacmans.get(0);
		int currentDistance = Math.abs(position.getX() - nearest.getX()) 
				+ Math.abs(position.getY() - nearest.getY());
		int testDistance = 0;
		
		for(PositionAgent positionPacman : positionPacmans) {
			testDistance = Math.abs(position.getX() - positionPacman.getX()) 
					+ Math.abs(position.getY() - positionPacman.getY());
			if(testDistance < currentDistance) {
				currentDistance = testDistance;
				nearest = positionPacman;
			}
		}
		
		return nearest;
	}
	
	@Override
	public void move(Agent agent, List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		PositionAgent position = agent.position();
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		PositionAgent positionPacman = nearest(position, positionPacmans);
		
		if((positionPacman.getX() > position.getX()) && (!walls[position.getX() + 1][position.getY()])) {
			newPosition.setX(position.getX() + 1);
			newPosition.setY(position.getY());
			newPosition.setDir(Dir.EAST);
		} else if ((positionPacman.getY() < position.getY()) && (!walls[position.getX()][position.getY() - 1])) {
			newPosition.setX(position.getX());
			newPosition.setY(position.getY() - 1);
			newPosition.setDir(Dir.NORTH);
		} else if ((positionPacman.getY() > position.getY()) && (!walls[position.getX()][position.getY() + 1])) {
			newPosition.setX(position.getX());
			newPosition.setY(position.getY() + 1);
			newPosition.setDir(Dir.SOUTH);
		} else if ((positionPacman.getX() < position.getX()) && (!walls[position.getX() - 1][position.getY()])) {
			newPosition.setX(position.getX() - 1);
			newPosition.setY(position.getY());
			newPosition.setDir(Dir.WEST);
		}
		
		agent.setPosition(newPosition);
	}

}
