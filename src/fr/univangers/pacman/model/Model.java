package fr.univangers.pacman.model;

import java.io.Serializable;

import fr.univangers.pacman.view.View;

/**
 * Interface implémentée dans la classe Abstraite Game
 */

public interface Model extends Serializable{

	public void addView(View view);
	public void removeView(View view);
	public void notifyViews();
	
}
