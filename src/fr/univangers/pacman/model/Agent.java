package fr.univangers.pacman.model;

import java.io.Serializable;

import fr.univangers.pacman.model.state.State;
import fr.univangers.pacman.model.state.Vulnerable;
import fr.univangers.pacman.model.strategy.Strategy;
import fr.univangers.pacman.model.state.Death;
import fr.univangers.pacman.model.state.Life;

public class Agent implements AgentAction, Serializable {
	
	private static final long serialVersionUID = 1968499836498466437L;
	
	public enum Type { PACMAN, GHOST }

	private State vunerable;
	private State life;
	private State death;
	
	private State currentState;
	
	private Strategy strategy;
	
	private Type type;
	private PositionAgent position;
	
	public PositionAgent position() {
		return position;
	}

	public Type type() {
		return type;
	}
	
	public Agent(Type type, PositionAgent position) {
		this.type = type;
		this.position = position;
		this.life = new Life(this);
		this.death = new Death(this);
		this.vunerable = new Vulnerable(this);
		vivant();	
		}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void setPosition(PositionAgent position) {
		this.position=position;
	}
	
	public void action(boolean[][] walls) {
		currentState.action(walls);
	}
	
	public void vulnerability() {
		currentState.vulnerability();
	}
	
	public void stopVulnerability() {
		currentState.stopVulnerability();
	}

	public boolean isDeath() {
		return currentState.isDeath();
	}

	public boolean isLife() {
		return currentState.isLife();
	}

	public boolean isVulnerable() {
		return currentState.isVulnerable();
	}
	
	@Override
	public void move(boolean[][] walls) {
		position = strategy.move(position, walls);
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
	
	
	public void vivant() {
		currentState = life;
	}

	public void mort() {
		currentState = death;
	}
	
	public void inversion() {
		currentState = vunerable;
	}
	
	public State getEtatActuel() {
		return currentState;
	}

}
