package fr.univangers.pacman.model;

import java.io.Serializable;

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

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
}