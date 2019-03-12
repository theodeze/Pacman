package fr.univangers.pacman.model.beans;

import com.google.gson.Gson;

public class GameState {
	private int nbTurn = 0;

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static GameState fromJson(String json) {
		return new Gson().fromJson(json, GameState.class);
	}

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
