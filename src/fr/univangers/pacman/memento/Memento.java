package fr.univangers.pacman.memento;

import fr.univangers.pacman.model.PacmanGame;

public class Memento {
	
	private PacmanGame pacmanGame;
	
	public Memento(PacmanGame pacman) {
		this.pacmanGame=pacman;
	}
	
	public PacmanGame getGame() {
		return pacmanGame;
	}

}
