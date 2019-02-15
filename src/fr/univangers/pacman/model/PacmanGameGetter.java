package fr.univangers.pacman.model;

import java.util.List;

public interface PacmanGameGetter {
	
	enum Mode {
		AUTO,
		ONEPLAYER,
		TWOPLAYERC,
		TWOPLAYERO
	}
	
	enum StrategyPacman {
		ASTAR,
		RANDOM,
		BASIC,
		NONE
	}
	
	enum StrategyGhost {
		ASTAR,
		TRACKING,
		RANDOM,
		BASIC,
		NONE
	}
	
	enum Winner {
		NOWINNER,
		GHOSTWINNER,
		PACMANWINNER
	}
	
	enum TypeData {
		NB_LIFE_PACMANS,
		SCORE,
		WINNER,
		POSITION_PACMANS,
		POSITION_GHOSTS,
		POSITION_FOODS,
		GHOSTS_SCARRED
	}

	int getNbLifePacmans();
	int getScore();
	Winner getWinner();
	List<PositionAgent> getPositionPacmans();
	List<PositionAgent> getPositionGhosts();
	List<PositionAgent> getPositionFoods();
	List<Boolean> getGhostsScarred();
	
}
