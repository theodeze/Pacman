package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import fr.univangers.pacman.model.state.State;
import fr.univangers.pacman.model.state.Vulnerable;
import fr.univangers.pacman.model.strategy.NoneStrategy;
import fr.univangers.pacman.model.strategy.Strategy;
import fr.univangers.pacman.model.state.Death;
import fr.univangers.pacman.model.state.Life;

/**
 * La classe Agent représente un Pacman ou un Fantôme
 */
public class Agent implements AgentAction, Serializable {
	
	private static final long serialVersionUID = 1968499836498466437L;
	
	public enum Type { PACMAN, GHOST }

	private Vulnerable vunerable;
	private Life life;
	private Death death;
	
	private State currentState;

	private Strategy lifeStrategy;
	private Strategy vunerableStrategy;
	private Strategy deathStrategy;
	
	private Strategy currentStrategy;
	
	private Type type;
	private PositionAgent positionInit;
	private PositionAgent position;
	
	public void resetPosition() {
		position = positionInit;
	}
	
	public PositionAgent position() {
		return position;
	}

	public Type type() {
		return type;
	}
	
	public Agent(Type type, PositionAgent position) {
		this.type = type;
		this.positionInit = position;
		this.position = position;
		this.life = new Life(this);
		this.death = new Death(this);
		this.vunerable = new Vulnerable(this);
		alive();	
	}
	
	public Agent(Type type, Position position) {
		this.type = type;
		this.positionInit = new PositionAgent(position);
		this.position = new PositionAgent(position);
		this.life = new Life(this);
		this.death = new Death(this);
		this.vunerable = new Vulnerable(this);
		alive();	
	}
	
	public void setStrategy(Strategy lifeStrategy, Strategy vunerableStrategy) {
		this.currentStrategy = lifeStrategy;
		this.lifeStrategy = lifeStrategy;
		this.vunerableStrategy = vunerableStrategy;
		this.deathStrategy = new NoneStrategy();
	}
	
	public void switchLifeStrategy() {
		currentStrategy = lifeStrategy;
	}
	
	public void switchVunerableStrategy() {
		currentStrategy = vunerableStrategy;
	}
	
	public void switchDeathStrategy() {
		currentStrategy = deathStrategy;
	}
	
	public void setPosition(PositionAgent position) {
		position.updateDir(this.position);
		this.position=position;
	}
	
	public void setPosition(Position position) {
		PositionAgent p = new PositionAgent(position);
		p.updateDir(this.position);
		this.position = p;
	}
	
	public void action(List<Position> positionPacmans, List<Position> positionGhosts, 
			List<Position> positionFoods, List<List<Boolean>> list) {
		currentState.action(positionPacmans, positionGhosts, positionFoods, list);
	}
	
	public void vulnerability() {
		currentState.vulnerability();
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
	public void move(List<Position> positionPacmans, List<Position> positionGhosts, 
			List<Position> positionFoods, List<List<Boolean>> walls) {
		switch(type) {
		case GHOST:
			if(isDeath())
				currentStrategy.move(this, Collections.emptyList(), Collections.emptyList(), 
						Collections.emptyList(), walls);
			else if(isLife())
				currentStrategy.move(this, positionPacmans, positionGhosts, 
						Collections.emptyList(), walls);
			else if(isVulnerable())
				currentStrategy.move(this, Collections.emptyList(), positionGhosts, 
						positionPacmans, walls);
			break;
		case PACMAN:
			if(isDeath())
				currentStrategy.move(this, Collections.emptyList(), Collections.emptyList(), 
						Collections.emptyList(), walls);
			else if(isLife())
				currentStrategy.move(this, positionFoods, positionPacmans, 
						positionGhosts, walls);
			break;
		default:
			currentStrategy.move(this, Collections.emptyList(), Collections.emptyList(), 
					Collections.emptyList(), walls);
			break;
		}
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
	
	public void alive() {
		switchLifeStrategy();
		currentState = life;
	}

	public void dead() {
		switchDeathStrategy();
		vunerable.resetTurnVulnerable();
		currentState = death;
	}
	
	public void inversion() {
		switchVunerableStrategy();
		currentState = vunerable;
	}
	
	public State getCurrentState() {
		return currentState;
	}

}
