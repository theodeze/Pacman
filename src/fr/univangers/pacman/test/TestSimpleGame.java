package fr.univangers.pacman.test;

import fr.univangers.pacman.controller.SimpleGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.game.SimpleGame;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class TestSimpleGame {

	public static void main(String[] args) {
		Maze maze;
		try {
			maze = new Maze("res/layouts/originalClassic.lay");
		} catch (Exception e) {
			return;
		}
		SimpleGame simpleGame = new SimpleGame(25);
		SimpleGameController simpleGameController = new SimpleGameController(simpleGame);
		ViewCommande viewCommande = new ViewCommande(simpleGame); 
		viewCommande.setGameController(simpleGameController);
		new ViewGame(simpleGame, simpleGameController, maze);
	}

}
