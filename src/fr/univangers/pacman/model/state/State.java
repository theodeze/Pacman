package fr.univangers.pacman.model.state;

import java.io.Serializable;

public interface State extends Serializable {

	public static final int VIVANT=0;
	public static final int MORT=1;
	public static final int INVERSE=2;

	public int getEtat();
	
}
