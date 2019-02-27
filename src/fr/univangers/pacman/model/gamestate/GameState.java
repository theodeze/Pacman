package fr.univangers.pacman.model.gamestate;

import java.io.Serializable;

public class GameState implements Serializable {

	private static final long serialVersionUID = 4823785680432798302L;
	private int nbTurn = 0;

	public int getNbTurn() {
		return nbTurn;
	}
	
	public void incNbTurn(int nbTurn) {
		this.nbTurn += nbTurn;
	}

	public void setNbTurn(int nbTurn) {
		this.nbTurn = nbTurn;
	}
	
}
