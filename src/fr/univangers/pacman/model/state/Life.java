package fr.univangers.pacman.model.state;

import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.Agent.Type;

/**
 * Vivant représente l'état par default d'un agent
 */
public class Life implements State {

	private static final long serialVersionUID = 4688284123963873980L;
	Agent agent;
	
	public Life(Agent agent) {
		this.agent = agent;
	}

	/**
	 * Déplace l'agent
	 */
	@Override
	public void action(List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, 
			List<PositionAgent> positionFoods, boolean[][] walls) {
		agent.move(positionPacmans, positionGhosts, positionFoods, walls);
	}
	
	/**
	 * Si l'agent est un fantome il dévient vunérable
	 */
	@Override
	public void vulnerability() {
		if(agent.type() == Type.GHOST) {
			agent.inversion();
		}
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
		return true;
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
