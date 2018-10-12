package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;

public class Vulnerable implements State {

	private static final long serialVersionUID = -8735708242622564562L;
	private static final Status status = Status.VULNERABLE;
	Agent agent;

	public Vulnerable(Agent agent) {
		this.agent = agent;
	}

	@Override
	public Status status() {
		return status;
	}

	@Override
	public void action(boolean[][] walls) {
		agent.move(walls);
	}
	
	@Override
	public void vulnerability() {
		//
	}

	@Override
	public void stopVulnerability() {
		agent.vivant();
	}

	@Override
	public boolean isDeath() {
		return false;
	}

	@Override
	public boolean isLife() {
		return false;
	}

	@Override
	public boolean isVulnerable() {
		return true;
	}

}
