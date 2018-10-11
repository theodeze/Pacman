package fr.univangers.pacman.model;

import java.io.Serializable;

public class Agent implements AgentAction, Serializable {
	
	private static final long serialVersionUID = 1968499836498466437L;
	
	public enum Type { PACMAN, GHOST }
	
	private Etat etatVie;
	private Etat etatMort;
	private Etat etatInverse;
	
	private Etat etatActuel;
	
	private Type type;
	private PositionAgent position;
	private PacmanGame game;
	private int nbTurnDeath;
	
	public PositionAgent position() {
		return position;
	}

	public Agent(Type type, PositionAgent position) {
		this.type = type;
		this.position = position;
		this.nbTurnDeath = 0;
		this.etatVie= new EtatVie(this);
		this.etatMort= new EtatMort(this);
		this.etatInverse= new EtatInverse(this);
		
		vivant();	
		}
	
	public void setPosition(PositionAgent position) {
		this.position=position;
	}
	
	public PositionAgent newPosition() {
		PositionAgent newPosition = new PositionAgent(position.getX(), position.getY(), position.getDir());
		switch(newPosition.getDir()) {
		case EAST:
			newPosition.setX(newPosition.getX() + 1);
			break;
		case NORTH:
			newPosition.setY(newPosition.getY() - 1);
			break;
		case SOUTH:
			newPosition.setY(newPosition.getY() + 1);
			break;
		case WEST:
			newPosition.setX(newPosition.getX() - 1);
			break;
		default:
			break;
		}
		return newPosition;
	}
	
	@Override
	public void move() {
		position = newPosition();
	}
	
	@Override
	public void goUp() {
		position.setDir(PositionAgent.Dir.NORTH);
	}

	@Override
	public void goLeft() {
		position.setDir(PositionAgent.Dir.WEST);
	}

	@Override
	public void goDown() {
		position.setDir(PositionAgent.Dir.SOUTH);
	}

	@Override
	public void goRight() {
		position.setDir(PositionAgent.Dir.EAST);
	}
	
	
	public void vivant() {
		// TODO Auto-generated method stub
		etatActuel=etatVie;
	}

	public void mort() {
		// TODO Auto-generated method stub
		etatActuel=etatMort;
	}
	
	public void inversion() {
		// TODO Auto-generated method stub
		etatActuel=etatInverse;
	}
	
	public Etat getEtatActuel() {
		return etatActuel;
	}

	public void deathTurn() {
		if(etatActuel==etatMort) {
			System.out.println("nbtour : "+nbTurnDeath);
			if(nbTurnDeath>=20)
				vivant();
			else 
				nbTurnDeath++;
		}
	}

}
