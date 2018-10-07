package fr.univangers.pacman.model;

import java.io.Serializable;

public class Agent implements AgentAction, Serializable {
	
	private static final long serialVersionUID = 1968499836498466437L;

	public enum Type { PACMAN, GHOST }
	
	private Type type;
	private PositionAgent position;
	
	public PositionAgent position() {
		return position;
	}
	
	public Agent(Type type, PositionAgent position) {
		this.type = type;
		this.position = position;
	}
	
	public PositionAgent newPosition() {
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		switch(newPosition.getDir()) {
		case EAST:
			newPosition.setX(newPosition.getX() + 1);
			break;
		case NORTH:
			newPosition.setY(newPosition.getY() - 1);
			break;
		case SOUTH:
			newPosition.setY(newPosition.getY() + 1);
			break;
		case WEST:
			newPosition.setX(newPosition.getX() - 1);
			break;
		default:
			break;
		}
		return newPosition;
	}
	
	@Override
	public void move() {
		position = newPosition();
	}
	
	@Override
	public void goUp() {
		position.setDir(PositionAgent.Dir.NORTH);
	}

	@Override
	public void goLeft() {
		position.setDir(PositionAgent.Dir.WEST);
	}

	@Override
	public void goDown() {
		position.setDir(PositionAgent.Dir.SOUTH);
	}

	@Override
	public void goRight() {
		position.setDir(PositionAgent.Dir.EAST);
	}
	

}
