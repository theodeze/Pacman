package fr.univangers.pacman.test;


import fr.univangers.pacman.memento.Creator;
import fr.univangers.pacman.memento.Guardian;
import fr.univangers.pacman.memento.Memento;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.view.ViewEndGame;

public class TestPacmanGame {

	public static void main(String[] args) {
		
		Creator creatorGame = new Creator();
		
		Memento memento=creatorGame.createMemento();
		Guardian guard = new Guardian();		
		guard.addMemento(memento);	
		
		PacmanGame pacmanGame=creatorGame.getGame();
		
		
			
		
	}
}
