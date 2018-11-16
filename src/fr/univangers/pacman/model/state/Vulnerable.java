package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

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
	public void action(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		agent.move(positionPacmans, positionGhosts, walls);
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
