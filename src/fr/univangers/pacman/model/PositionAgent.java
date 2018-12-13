package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * La classe PositionAgent permet d'afficher et localiser dans
 * le jeu chaque agent à la case qui correspond, en fonction d'un
 * moment donné
 */

public class PositionAgent implements Serializable {

	private static final long serialVersionUID = -2240234555555290641L;

	public enum Dir { NORTH, SOUTH, EAST, WEST }
	
	private int x;
	private int y;
	private Dir dir;
	
	public PositionAgent(int x, int y, Dir dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public PositionAgent(int x, int y) {
		this.x = x;
		this.y = y;
		this.dir = Dir.NORTH;
	}
	
	public PositionAgent(PositionAgent p) {
		this.x = p.x;
		this.y = p.y;
		this.dir = p.dir;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x,y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PositionAgent) {
			PositionAgent p = (PositionAgent) obj;
			return (p.x == x) && (p.y == y);
		}
		return false;
	}
	
	public boolean near(PositionAgent p) {
		return ((p.x == x) || ((p.y == y) && (p.x - 1 == x) || (p.x + 1 == x))) 
				&& ((p.y == y) || ((p.x == x) && (p.y - 1 == y) || (p.y + 1 == y)));
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void updateDir(PositionAgent p) {
		if(p.x - 1 == x)
			dir = Dir.WEST;
		else if(p.x + 1 == x)
			dir = Dir.EAST;
		else if(p.y - 1 == y)
			dir = Dir.NORTH;
		else if(p.y + 1 == y)
			dir = Dir.SOUTH;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
}