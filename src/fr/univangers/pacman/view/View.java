package fr.univangers.pacman.view;

import java.io.Serializable;


/**
 * Interface Model équivalent au observer dans le patern Observer
 */
public interface View extends Serializable {

	/**
	 * Met à jour la vue à chaque appel
	 */
	public void update();
	
}
