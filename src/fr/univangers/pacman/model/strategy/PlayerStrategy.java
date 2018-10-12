package fr.univangers.pacman.model.strategy;

import fr.univangers.pacman.model.PositionAgent;

public class PlayerStrategy implements Strategy {

	private static final long serialVersionUID = -3421485434310896297L;

	@Override
	public PositionAgent move(PositionAgent position, boolean[][] walls) {
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
		return walls[newPosition.getX()][newPosition.getY()] ? position : newPosition;
	}

}
