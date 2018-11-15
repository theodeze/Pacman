package fr.univangers.pacman.model;

import java.util.ArrayList;
import java.util.List;

import fr.univangers.pacman.view.View;

public abstract class Game implements Model, Runnable {

	private static final long serialVersionUID = -5712731745471369390L;
	
	private List<View> views = new ArrayList<>();
	private boolean isRunning = false;
	private boolean isOver = false;
	private int time = 1000;
	private int nbTurn = 0;
	private int maxTurn;
	
	/* PACMAN */
	private List<PositionAgent> positionPacmans = new ArrayList<>();
	private List<PositionAgent> positionGhosts = new ArrayList<>();
	private boolean ghostsScarred;
	
	public void over() {
		isOver=true;
	}
	
	public int nbTurn() {
		return nbTurn;
	}
	
	public void resetNbTurn() {
		nbTurn = 0;
		notifyViews();
	}
	
	public void incNbTurn() {
		nbTurn++;
		notifyViews();
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void clearPositionPacman() {
		positionPacmans.clear();
	}
	
	public void addPositionPacman(PositionAgent position) {
		positionPacmans.add(position);
	}
	
	public List<PositionAgent> positionPacmans() {
		return positionPacmans;
	}
	
	public void clearPositionGhosts() {
		positionGhosts.clear();
	}
	
	public void addPositionGhosts(PositionAgent position) {
		positionGhosts.add(position);
	}
	
	public List<PositionAgent> positionGhosts() {
		return positionGhosts;
	}
	
	public boolean ghostsScarred() {
		return ghostsScarred;
	}
	
	public void setGhostsScarred(boolean ghostsScarred) {
		this.ghostsScarred = ghostsScarred;
	}
	
	public Game(int maxTurn) {
		this.maxTurn = maxTurn;
	}
	
	// Méthodes concrètes 
	
	public void init() {
		resetNbTurn();
		initializeGame();
	}
	
	public void step() {
		if(nbTurn < maxTurn) {
			incNbTurn();
			takeTurn();
		} else {
			isOver = true;
		}
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public void launch() {
		isRunning = true;
		isOver = false;
		Thread thread = new Thread(this);  
		thread.start();
	}
	
	@Override
	public void run() {
		while(!isOver && isRunning) {
			step();
			try {
				Thread.sleep((long)time);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if(isOver) {
			gameOver();
		}
		isRunning = false;
	}

	@Override
	public void addView(View view) {
		views.add(view);
	}

	@Override
	public void removeView(View view) {
		views.remove(view);
	}

	@Override
	public void notifyViews() {
		for(View view : views) {
			view.update();
		}
	}
	
	// Méthodes abstraites
	
	public abstract void initializeGame();
	public abstract void takeTurn();
	public abstract void gameOver();

}
