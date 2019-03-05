package fr.univangers.pacman.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.game.PacmanGame;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;
import fr.univangers.pacman.view.dialog.DialogSettings;

public class TestPacmanGame {
	private static final Logger LOGGER = LogManager.getLogger("Pacman"); 

	public static void main(String[] args) {
		try {
			PacmanGameSettings settings = DialogSettings.show();
			Maze maze = new Maze(settings.getNameMaze());
			PacmanGame game = new PacmanGame(settings, maze);
			PacmanGameController gameController = new PacmanGameController(game);
			ViewCommande commande = new ViewCommande(game);
			commande.setGameController(gameController);
			new ViewGame(game, gameController, maze);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}
