package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;

public class Vulnerable implements State {

	private static final long serialVersionUID = -8735708242622564562L;
	Agent agent;
	int etat;

	public Vulnerable(Agent agent) {
		this.agent=agent;
		this.etat=INVERSE;
	}

	@Override
	public int getEtat() {
		return etat;
	}

}
