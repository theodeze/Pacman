package fr.univangers.pacman.controller;

import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.game.PacmanGame;

/**
 * Classe qui permet de lier l'interface graphique du Pacman et le jeu Pacman
 * On a des fonctions qui permettent de mettre à jour le temps, et en fonction du bouton l'utilisant,
 * mettre en pause, relancer le jeu à l'état courant, relancer la partie depuis le début ou avancer
 * d'un tour et de contrôler le joueur
 */
public class PacmanGameController implements GameController {
	
	private static final long serialVersionUID = 7744355889303690019L;
	private PacmanGame pacmanGame;

	public PacmanGameController(PacmanGame pacmanGame) {
		this.pacmanGame = pacmanGame;
	}

	@Override
	public void setTime(int time) {
		pacmanGame.setTime(time);
	}
	
	@Override
	public void pause() {
		pacmanGame.stop();
	}

	@Override
	public void restart() {
		pacmanGame.init();
	}

	@Override
	public void launch() {
		pacmanGame.launch();
	}

	@Override
	public void step() {
		pacmanGame.step();
	}

	@Override
	public void movePlayer1(Dir dir) {
		pacmanGame.movePacmanPlayer1(dir);
	}

	@Override
	public void movePlayer2(Dir dir) {
		pacmanGame.movePacmanPlayer2(dir);
	}
}
