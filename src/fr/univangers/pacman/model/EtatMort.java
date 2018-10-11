package fr.univangers.pacman.model;

public class EtatMort implements Etat{

	Agent agent;
	private int etat;
	
		
	public EtatMort(Agent agent) {
		this.agent=agent;
		this.etat=MORT;
	}

	@Override
	public int getEtat() {
		// TODO Auto-generated method stub
		return etat;
	}

}
