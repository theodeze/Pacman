package fr.univangers.pacman.model.beans;

import com.google.gson.Gson;

public class PacmanGameSettings {
	public enum Mode {
		AUTO("Auto"),
		ONEPLAYER("Un joueur"),
		TWOPLAYERC("Deux joueurs (Comperatif)"),
		TWOPLAYERO("Deux joueurs (Opposition)");

	    private final String text;
		Mode(final String text) {
	        this.text = text;
	    }
		@Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum StrategyPacman {
		ASTAR("A*"),
		RANDOM("Basique"),
		BASIC("Aléatoire"),
		NONE("Rien");

	    private final String text;
	    StrategyPacman(final String text) {
	        this.text = text;
	    }
		@Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum StrategyGhost {
		ASTAR("A* (Difficile)"),
		TRACKING("Pister (Normale)"),
		RANDOM("Basique (Facile)"),
		BASIC("Aléatoire (Facile)"),
		NONE("Rien (Paisible)");

	    private final String text;
	    StrategyGhost(final String text) {
	        this.text = text;
	    }
		@Override
	    public String toString() {
	        return text;
	    }
	}
	
	private Mode mode;
	private StrategyGhost strategyGhost;
	private StrategyPacman strategyPacman;
	private int maxTurn;
	private String nameMaze;
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static PacmanGameSettings fromJson(String json) {
		return new Gson().fromJson(json, PacmanGameSettings.class);
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public StrategyGhost getStrategyGhost() {
		return strategyGhost;
	}
	
	public void setStrategyGhost(StrategyGhost strategyGhost) {
		this.strategyGhost = strategyGhost;
	}
	
	public StrategyPacman getStrategyPacman() {
		return strategyPacman;
	}
	
	public void setStrategyPacman(StrategyPacman strategyPacman) {
		this.strategyPacman = strategyPacman;
	}
	
	public int getMaxTurn() {
		return maxTurn;
	}
	
	public void setMaxTurn(int maxTurn) {
		this.maxTurn = maxTurn;
	}
	
	public String getNameMaze() {
		return nameMaze;
	}
	
	public void setNameMaze(String nameMaze) {
		this.nameMaze = nameMaze;
	}
}
