package fr.univangers.pacman.model;

public class EtatVie implements Etat{
	Agent agent;
	int etat;
	
	public EtatVie(Agent agent) {
		this.agent=agent;
		this.etat=VIVANT;
	}

	@Override
	public int getEtat() {
		// TODO Auto-generated method stub
		return etat;
	}
	
}
