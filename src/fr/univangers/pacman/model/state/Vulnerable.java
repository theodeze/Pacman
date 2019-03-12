package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;

/**
 *  Classe qui sert à définir l'état Vulnerable
 */

public class Vulnerable implements State {

	private static final long serialVersionUID = -8735708242622564562L;
	private static final int END_TURN_VUNERABLE = 20;
	private int nbTurnVulnerable = 0;
	Agent agent;

	public Vulnerable(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Rénitialise le compteur de tours de vunérabilité
	 */
	public void resetTurnVulnerable() {
		nbTurnVulnerable = 0;
	}

	/**
	 * L'agent ce déplace et quite l'état vulnerable au bout d'un certain nombre de tours
	 */
	@Override
	public void action(List<Position> positionPacmans, List<Position> positionGhosts, 
			List<Position> positionFoods, List<List<Boolean>> walls) {
		if(nbTurnVulnerable >=  END_TURN_VUNERABLE) {
			resetTurnVulnerable();
			agent.alive();
		} else {
			nbTurnVulnerable++;
		}
		agent.move(positionPacmans, positionGhosts, positionFoods, walls);
	}
	
	/**
	 * Ne fais rien car l'agent est vulnerable
	 */
	@Override
	public void vulnerability() {
		//
	}
	
	/**
	 * Renvoie si l'état est dead
	 * @return vrai si dead faux sinon
	 */
	@Override
	public boolean isDeath() {
		return false;
	}
	
	/**
	 * Renvoie si l'état est alive
	 * @return vrai si alive faux sinon
	 */
	@Override
	public boolean isLife() {
		return false;
	}

	/**
	 * Renvoie si l'état est vulnerable
	 * @return vrai si vulnerable faux sinon
	 */
	@Override
	public boolean isVulnerable() {
		return true;
	}

}
