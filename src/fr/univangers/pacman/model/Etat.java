package fr.univangers.pacman.model;

public interface Etat {

	public final static int VIVANT=0;
	public final static int MORT=1;
	public final static int INVERSE=2;

	public int getEtat();
	
}
