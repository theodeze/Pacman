package fr.univangers.pacman.model.state;

import java.io.Serializable;

public interface State extends Serializable {

	public enum Status  {
		DEATH, LIFE, VULNERABLE
	}

	public Status status();
	public void action();
	public void vulnerability();
	public void stopVulnerability();
	public boolean isDeath();
	public boolean isLife();
	public boolean isVulnerable();
	
}
