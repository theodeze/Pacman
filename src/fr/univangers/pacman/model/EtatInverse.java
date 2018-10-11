package fr.univangers.pacman.model;

public class EtatInverse implements Etat{

	Agent agent;
	int etat;
	
	
	public EtatInverse(Agent agent) {
		this.agent=agent;
		this.etat=INVERSE;
	}

	@Override
	public int getEtat() {
		// TODO Auto-generated method stub
		return etat;
	}

}
