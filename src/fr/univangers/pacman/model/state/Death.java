package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;

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
	public void action() {
		if(nbTurnDeath >= 20) {
			nbTurnDeath = 0;
			agent.vivant();
		} else { 
			nbTurnDeath++;
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
