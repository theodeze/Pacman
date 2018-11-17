package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;
import fr.univangers.pacman.model.PositionAgent;

public class Death implements State {

	private static final long serialVersionUID = 3749566683811598L;
	private static final Status status = Status.DEATH;
	Agent agent;
	private int nbTurnDeath = 0;
	
		
	public Death(Agent agent) {
		this.agent = agent;
	}

	@Override
	public Status status() {
		return status;
	}
	
	@Override
	public void action(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		if(agent.type() == Type.GHOST) {
			if(nbTurnDeath >= 20) {
				nbTurnDeath = 0;
				agent.resetPosition();
				agent.vivant();
			} else { 
				nbTurnDeath++;
			}
		}
	}
	
	@Override
	public void vulnerability() {
		//
	}

	@Override
	public void stopVulnerability() {
		//
	}

	@Override
	public boolean isDeath() {
		return true;
	}

	@Override
	public boolean isLife() {
		return false;
	}

	@Override
	public boolean isVulnerable() {
		return false;
	}

}
