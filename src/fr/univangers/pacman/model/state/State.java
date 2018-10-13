package fr.univangers.pacman.model.state;

import java.io.Serializable;
import java.util.List;

import fr.univangers.pacman.model.PositionAgent;

public interface State extends Serializable {

	public enum Status  {
		DEATH, LIFE, VULNERABLE
	}

	public Status status();
	public void action(List<PositionAgent> positionPacmans, boolean[][] walls);
	public void vulnerability();
	public void stopVulnerability();
	public boolean isDeath();
	public boolean isLife();
	public boolean isVulnerable();
	
}
