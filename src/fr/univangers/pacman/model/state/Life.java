package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;

public class Life implements State {

	private static final long serialVersionUID = 4688284123963873980L;
	private static final Status status = Status.LIFE;
	Agent agent;
	
	
	public Life(Agent agent) {
		this.agent = agent;
	}

	@Override
	public Status status() {
		return status;
	}

	@Override
	public void action() {
		agent.move();
	}
	
	@Override
	public void vulnerability() {
		if(agent.type() == Type.GHOST) {
			agent.inversion();
		}
	}

	@Override
	public void stopVulnerability() {
		//
	}

	@Override
	public boolean isDeath() {
		return false;
	}

	@Override
	public boolean isLife() {
		return true;
	}

	@Override
	public boolean isVulnerable() {
		return false;
	}
	
}
