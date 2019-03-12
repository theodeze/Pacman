package fr.univangers.pacman.model.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;

public class PacmanGameState extends GameState {
	public enum Winner {
		NOWINNER,
		GHOSTWINNER,
		PACMANWINNER
	}
	
	private int nbLifePacmans = 3;
	private Winner winner = Winner.NOWINNER;
	private int score = 0;
	private List<PositionAgent> positionPacmans = new ArrayList<>();
	private List<Position> positionGhosts = new ArrayList<>();
	private List<Position> positionFoods = new ArrayList<>();
	private List<Position> positionCapsules = new ArrayList<>();
	private List<Boolean> ghostsScarred = new ArrayList<>();
	private String currentSong;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

	public static PacmanGameState fromJson(String json) {
		return new Gson().fromJson(json, PacmanGameState.class);
	}
	
	public int getNbLifePacmans() {
		return nbLifePacmans;
	}
	
	public void setNbLifePacmans(int nbLifePacmans) {
		this.nbLifePacmans = nbLifePacmans;
	}
	
	public void incNbLifePacmans(int nbLifePacmans) {
		this.nbLifePacmans -= nbLifePacmans;
	}
	
	public Winner getWinner() {
		return winner;
	}
	
	public void setWinner(Winner winner) {
		this.winner = winner;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void incScore(int score) {
		this.score += score;
	}
	
	public List<PositionAgent> getPositionPacmans() {
		return positionPacmans;
	}
	
	public void setPositionPacmans(List<PositionAgent> positionPacmans) {
		this.positionPacmans = positionPacmans;
	}
	
	public List<Position> getPositionGhosts() {
		return positionGhosts;
	}
	
	public void setPositionGhosts(List<Position> positionGhosts) {
		this.positionGhosts = positionGhosts;
	}
	
	public List<Position> getPositionFoods() {
		return positionFoods;
	}
	
	public void setPositionFoods(List<Position> positionFoods) {
		this.positionFoods = positionFoods;
	}
	
	public List<Position> getPositionCapsules() {
		return positionCapsules;
	}
	
	public void setPositionCapsules(List<Position> positionCapsules) {
		this.positionCapsules = positionCapsules;
	}
	
	public List<Boolean> getGhostsScarred() {
		return ghostsScarred;
	}
	
	public void addGhostsScarred(boolean isScarred) {
		ghostsScarred.add(isScarred);
	}
	
	public void setGhostsScarred(List<Boolean> ghostsScarred) {
		this.ghostsScarred = ghostsScarred;
	}

	public String getCurrentSong() {
		return currentSong;
	}

	public void setCurrentSong(String currentSong) {
		this.currentSong = currentSong;
	}

}
