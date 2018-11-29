package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

public class Vulnerable implements State {

	private static final long serialVersionUID = -8735708242622564562L;
	private static final int endTurnVulnerable = 20;
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
	public void action(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, 
			List<PositionAgent> positionFoods, boolean[][] walls) {
		if(nbTurnVulnerable >=  endTurnVulnerable) {
			resetTurnVulnerable();
			agent.vivant();
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
	 * Renvoie si l'état est mort
	 * @return vrai si mort faux sinon
	 */
	@Override
	public boolean isDeath() {
		return false;
	}
	
	/**
	 * Renvoie si l'état est vivant
	 * @return vrai si vivant faux sinon
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
