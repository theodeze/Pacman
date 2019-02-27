package fr.univangers.pacman.model.game;

import java.util.ArrayList;
import java.util.List;

import fr.univangers.pacman.model.Model;
import fr.univangers.pacman.model.gamestate.GameState;
import fr.univangers.pacman.view.View;

/**
 * Classe abstraite Game gère les tours et la fin de partie
 */
public abstract class Game implements Model, Runnable {

	private static final long serialVersionUID = -5712731745471369390L;
	
	private List<View> views = new ArrayList<>();
	private boolean isRunning = false;
	private boolean isOver = false;
	private int time = 1000;
	private GameState state = new GameState();
	private int maxTurn;
	
	public void over() {
		isOver=true;
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public Game(int maxTurn) {
		this.maxTurn = maxTurn;
	}
	
	// Méthodes concrètes 
	
	public void init() {
		getState().setNbTurn(0);
		initializeGame();
	}
	
	public void step() {
		if(state.getNbTurn() < maxTurn) {
			getState().incNbTurn(1);
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
