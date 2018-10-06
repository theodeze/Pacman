package fr.univangers.pacman.model;

import java.io.Serializable;

public class Agent implements AgentAction, Serializable {
	
	private static final long serialVersionUID = 1968499836498466437L;

	public enum Type { PACMAN, GHOST }
	
	private Type type;
	private PositionAgent position;
	
	public Agent(Type type) {
		this.type = type;
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
