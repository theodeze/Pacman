package fr.univangers.pacman.model.state;

import java.io.Serializable;
import java.util.List;

import fr.univangers.pacman.model.Position;

/**
 * Interface pour définir l'Etat d'un agent
 */
public interface State extends Serializable {
	
	/**
	 * Action à effectuer
	 * @param positionPacmans
	 * @param positionGhosts
	 * @param list
	 */
	public void action(List<Position> positionPacmans, List<Position> positionGhosts, List<Position> positionFoods, List<List<Boolean>> list);
	
	/**
	 * Passe le status de l'agent à vunerable
	 */
	public void vulnerability();
	
	/**
	 * Renvoie si l'état est mort
	 * @return vrai si mort faux sinon
	 */
	public boolean isDeath();
	
	/**
	 * Renvoie si l'état est vivant
	 * @return vrai si vivant faux sinon
	 */
	public boolean isLife();

	/**
	 * Renvoie si l'état est vulnerable
	 * @return vrai si vulnerable faux sinon
	 */
	public boolean isVulnerable();
	
}
