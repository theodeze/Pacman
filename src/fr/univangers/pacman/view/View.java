package fr.univangers.pacman.view;

import java.io.Serializable;

/**
 * Interface View est utilisée dans la classe ViewCommande
 * et ViewGame
 */

public interface View extends Serializable {

	/**
	 * Met à jour la vue à chaque appel
	 */
	public void update();
	
}
