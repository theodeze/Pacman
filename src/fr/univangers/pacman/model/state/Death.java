package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;
import fr.univangers.pacman.model.PositionAgent;

public class Death implements State {

	private static final long serialVersionUID = 3749566683811598L;
	private static final int endTurnDeath = 20;
	private int nbTurnDeath = 0;
	Agent agent;
		
	public Death(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Rénitialise le compteur de tours de mort
	 */
	public void resetTurnDeath() {
		nbTurnDeath = 0;
	}
	
	/**
	 * Si l'agent est un fantome alors l'agent attend un nombre de tours avant de revivre 
	 */
	@Override
	public void action(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		if(agent.type() == Type.GHOST) {
			if(nbTurnDeath >= endTurnDeath) {
				resetTurnDeath();
				agent.resetPosition();
				agent.vivant();
			} else { 
				nbTurnDeath++;
			}
		}
	}
	
	/**
	 * Ne fais rien car l'agent est mort
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
		return true;
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
		return false;
	}

}
