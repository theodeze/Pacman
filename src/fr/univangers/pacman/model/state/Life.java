package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;

public class Life implements State {

	private static final long serialVersionUID = 4688284123963873980L;
	Agent agent;
	int etat;
	
	
	public Life(Agent agent) {
		this.agent=agent;
		this.etat=VIVANT;
	}

	@Override
	public int getEtat() {
		return etat;
	}
	
}
