package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.List;

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

	private Strategy currentStrategy;
	private Strategy lifeStrategy;
	private Strategy vunerableStrategy;
	
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
	
	public void setStrategy(Strategy lifeStrategy, Strategy vunerableStrategy) {
		this.currentStrategy = lifeStrategy;
		this.lifeStrategy = lifeStrategy;
		this.vunerableStrategy = vunerableStrategy;
	}
	
	public void switchLifeStrategy() {
		currentStrategy = lifeStrategy;
	}
	
	public void switchVunerableStrategy() {
		currentStrategy = vunerableStrategy;
	}
	
	public void setPosition(PositionAgent position) {
		this.position=position;
	}
	
	public void action(List<PositionAgent> positionPacmans, boolean[][] walls) {
		currentState.action(positionPacmans, walls);
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
	public void move(List<PositionAgent> positionPacmans, boolean[][] walls) {
		currentStrategy.move(this, positionPacmans, walls);
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
		switchLifeStrategy();
		currentState = life;
	}

	public void mort() {
		currentState = death;
	}
	
	public void inversion() {
		switchVunerableStrategy();
		currentState = vunerable;
	}
	
	public State getEtatActuel() {
		return currentState;
	}

}
