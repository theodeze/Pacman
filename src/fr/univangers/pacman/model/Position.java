package fr.univangers.pacman.model;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable{
	
	private static final long serialVersionUID = 2427909326969919352L;
	int x;
	int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Position() {
		this.x = 0;
		this.y = 0;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x,y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position) {
			Position p = (Position) obj;
			return (p.x == x) && (p.y == y);
		}
		return false;
	}
	
	public double distance(Position p) {
		return Math.sqrt(Math.pow(p.x - (double)x, 2) + Math.pow(p.y - (double)y, 2));
	}
	
	public boolean near(Position p) {
		return distance(p) <= 1;
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
	
}
