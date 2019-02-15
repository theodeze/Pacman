package fr.univangers.pacman.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class PacmanServer extends PacmanGame {

	private static final long serialVersionUID = -3170763958222800378L;
	private static final Logger LOGGER = LogManager.getLogger("Server"); 
	
	private Socket so;
	
	public PacmanServer(int maxTurn, Maze maze, StrategyPacman strategyPacman, 
			StrategyGhost strategyGhost, Mode mode, Socket so) {
		super(maxTurn, maze, strategyPacman, strategyGhost, mode);
		this.so = so;
	}
	
	@Override
	public void notifyViews() {
		if(so != null && !so.isClosed()) {
			try {
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				output.println(TypeData.GHOSTS_SCARRED.toString() + "///" +
					new Gson().toJson(getGhostsScarred()));
				output.flush();
				output.println(TypeData.NB_LIFE_PACMANS.toString() + "///" +
					new Gson().toJson(getNbLifePacmans()));
				output.flush();
				output.println(TypeData.POSITION_FOODS.toString() + "///" +
					new Gson().toJson(getPositionFoods()));
				output.flush();
				output.println(TypeData.POSITION_GHOSTS.toString()  + "///" +
					new Gson().toJson(getPositionGhosts()));
				output.flush();
				output.println(TypeData.POSITION_PACMANS.toString() + "///" +
					new Gson().toJson(getPositionPacmans()));
				output.flush();
				output.println(TypeData.SCORE.toString()  + "///" +
					new Gson().toJson(getScore()));
				output.flush();
				output.println(TypeData.WINNER.toString() + "///" +
					new Gson().toJson(getWinner()));
				output.flush();
			} catch (IOException e) {
				LOGGER.warn("Erreur envoie");
			}
		}
	}
}
