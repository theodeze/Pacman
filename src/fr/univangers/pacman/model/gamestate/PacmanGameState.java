package fr.univangers.pacman.model.gamestate;

import java.util.ArrayList;
import java.util.List;

import fr.univangers.pacman.model.PositionAgent;

public class PacmanGameState extends GameState {

	public enum Mode {
		AUTO,
		ONEPLAYER,
		TWOPLAYERC,
		TWOPLAYERO
	}
	
	public enum StrategyPacman {
		ASTAR,
		RANDOM,
		BASIC,
		NONE
	}
	
	public enum StrategyGhost {
		ASTAR,
		TRACKING,
		RANDOM,
		BASIC,
		NONE
	}
	
	public enum Winner {
		NOWINNER,
		GHOSTWINNER,
		PACMANWINNER
	}
	
	private static final long serialVersionUID = 7036825388150731904L;
	private int nbLifePacmans = 3;
	private Winner winner = Winner.NOWINNER;
	private int score = 0;
	private List<PositionAgent> positionPacmans = new ArrayList<>();
	private List<PositionAgent> positionGhosts = new ArrayList<>();
	private List<PositionAgent> positionFoods = new ArrayList<>();
	private List<PositionAgent> positionCapsules = new ArrayList<>();
	private List<Boolean> ghostsScarred = new ArrayList<>();
	private String currentSong;
	
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
	
	public void addPositionPacmans(PositionAgent position) {
		positionPacmans.add(position);
	}
	
	public void clearPositionPacmans() {
		positionPacmans.clear();
	}
	
	public void setPositionPacmans(List<PositionAgent> positionPacmans) {
		this.positionPacmans = positionPacmans;
	}
	
	public List<PositionAgent> getPositionGhosts() {
		return positionGhosts;
	}
	
	public void addPositionGhosts(PositionAgent position) {
		positionGhosts.add(position);
	}
	
	public void clearPositionGhosts() {
		positionGhosts.clear();
	}
	
	public void setPositionGhosts(List<PositionAgent> positionGhosts) {
		this.positionGhosts = positionGhosts;
	}
	
	public List<PositionAgent> getPositionFoods() {
		return positionFoods;
	}
	
	public void addPositionFoods(PositionAgent position) {
		positionFoods.add(position);
	}
	
	public void clearPositionFoods() {
		positionFoods.clear();
	}
	
	public void setPositionFoods(List<PositionAgent> positionFoods) {
		this.positionFoods = positionFoods;
	}
	
	public List<PositionAgent> getPositionCapsules() {
		return positionCapsules;
	}
	
	public void addPositionCapsules(PositionAgent position) {
		positionCapsules.add(position);
	}
	
	public void clearPositionCapsules() {
		positionCapsules.clear();
	}
	
	public void setPositionCapsules(List<PositionAgent> positionCapsules) {
		this.positionCapsules = positionCapsules;
	}
	
	public List<Boolean> getGhostsScarred() {
		return ghostsScarred;
	}
	
	public void addGhostsScarred(boolean isScarred) {
		ghostsScarred.add(isScarred);
	}
	
	public void clearGhostsScarred() {
		ghostsScarred.clear();
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
