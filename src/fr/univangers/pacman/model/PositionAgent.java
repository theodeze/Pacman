package fr.univangers.pacman.model;

/**
 * La classe PositionAgent represente la position d'un agent et sa direction
 */
public class PositionAgent extends Position {

	public enum Dir { NORTH, SOUTH, EAST, WEST }
	
	private Dir dir;
	
	public PositionAgent(int x, int y, Dir dir) {
		super(x, y);
		this.dir = dir;
	}
	
	public PositionAgent(int x, int y) {
		super(x, y);
		this.dir = Dir.NORTH;
	}
	
	public PositionAgent(PositionAgent p) {
		super(p.x, p.y);
		this.dir = p.dir;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public PositionAgent(Position p) {
		super(p.x, p.y);
		this.dir = Dir.NORTH;
	}
	
	public Position toPosition() {
		return new Position(this);
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