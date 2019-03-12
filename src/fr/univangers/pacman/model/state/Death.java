package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Agent.Type;
import fr.univangers.pacman.model.Position;

/**
 * Classe qui sert à définir l'état Death
 */

public class Death implements State {

	private static final long serialVersionUID = 3749566683811598L;
	private static final int END_TURN_DEATH = 20;
	private int nbTurnDeath = 0;
	Agent agent;
		
	public Death(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Rénitialise le compteur de tours de dead
	 */
	public void resetTurnDeath() {
		nbTurnDeath = 0;
	}
	
	/**
	 * Si l'agent est un fantome alors l'agent attend un nombre de tours avant de revivre 
	 */
	@Override
	public void action(List<Position> positionPacmans, List<Position> positionGhosts, 
			List<Position> positionFoods, List<List<Boolean>> walls) {
		if(agent.type() == Type.GHOST) {
			if(nbTurnDeath >= END_TURN_DEATH) {
				resetTurnDeath();
				agent.resetPosition();
				agent.dead();
			} else { 
				nbTurnDeath++;
			}
		}
	}
	
	/**
	 * Ne fais rien car l'agent est dead
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
		return true;
	}
	
	/**
	 * Renvoie si l'état est dead
	 * @return vrai si dead faux sinon
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
