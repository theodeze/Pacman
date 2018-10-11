package fr.univangers.pacman.model.state;

import fr.univangers.pacman.model.Agent;

public class Death implements State {

	private static final long serialVersionUID = 3749566683811598L;
	Agent agent;
	private int etat;
	
		
	public Death(Agent agent) {
		this.agent=agent;
		this.etat=MORT;
	}

	@Override
	public int getEtat() {
		return etat;
	}

}
