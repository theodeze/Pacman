package fr.univangers.pacman.test;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class TestPacmanGame {

	public static void main(String[] args) {
		Maze maze;
		try {
			maze = new Maze("res/layouts/originalClassic.lay");
		} catch (Exception e) {
			return;
		}
		PacmanGame pacmanGame = new PacmanGame(250,maze);
		PacmanGameController pacmanGameController = new PacmanGameController(pacmanGame);
		ViewCommande viewCommande = new ViewCommande(pacmanGame); 
		viewCommande.setGameController(pacmanGameController);
		new ViewGame(pacmanGame, pacmanGameController, maze);
	}

}
