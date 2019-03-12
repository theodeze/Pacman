package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.Position;

/**
 * Stratégie pister pour les fantomes
 * Elle utilise un tableau qui représente le labyrinthe et 
 * la valeur d'une case représente une "trace de la cible" 
 * (0 si récente et infinie si un mur) et à chaque exécution 
 * la "trace" ce dissipe et si un fantôme passe dessus la 
 * "trace" ce dissipe plus vite. Le fantôme suit 
 * la "trace".
 */
public class TrackingStrategy implements Strategy {

	private static final long serialVersionUID = -3703352132989593376L;
	private static List<List<Double>> maze = new ArrayList<>();
	
	private void iniMaze(List<List<Boolean>> walls) {
		int xSize = walls.size();
		int ySize = walls.get(0).size();
        for(int x = 0; x < xSize; x++) {
        	maze.add(new ArrayList<>(Collections.nCopies(ySize, 0.0)));
        	for(int y = 0; y < ySize; y++)
        		if(walls.get(y).get(x))
        			setMaze(x, y, Double.POSITIVE_INFINITY);
        }
	}
	
	private double getMaze(int x, int y) {
		return maze.get(x).get(y);
	}
	
	private void setMaze(int x, int y, double value) {
		maze.get(x).set(y, value);
	}
	
	private void incMaze(int x, int y) {
		maze.get(x).set(y, getMaze(x, y) + 1);
	}
	
	private void incMaze(List<Position> targets) {
		for(Position target : targets)
			incMaze(target.getX(), target.getY());
	}
	
	private void incAllMaze() {
        for(int x = 0; x < maze.size(); x++)
        	for(int y = 0; y < maze.get(0).size(); y++)
        		incMaze(x, y);
	}
	
	private void clearMaze(List<Position> targets) {
		for(Position target : targets)
			setMaze(target.getX(), target.getY(), 0);
	}
	
	private Position nextPosition(Position pAgent) {
		Position newPosition = new Position(pAgent);
		double min = Double.POSITIVE_INFINITY;
		List<Position> positions = new ArrayList<>();
		Position cp1 = new Position(pAgent);
		cp1.setX(cp1.getX() + 1);
		positions.add(cp1);
		Position cp2 = new Position(pAgent);
		cp2.setX(cp2.getX() - 1);
		positions.add(cp2);
		Position cp3 = new Position(pAgent);
		cp3.setY(cp3.getY() + 1);
		positions.add(cp3);
		Position cp4 = new Position(pAgent);
		cp4.setY(cp4.getY() - 1);
		positions.add(cp4);
		for(Position currentPosition : positions)	
			if(getMaze(currentPosition.getX(), currentPosition.getY()) < min) {
				min = getMaze(currentPosition.getX(), currentPosition.getY());
				newPosition = currentPosition;
			}
		return newPosition;
	}
	
	@Override
	public void move(Agent agent, List<Position> targets, List<Position> friends, 
			List<Position> enemies, List<List<Boolean>> walls) {
		if(maze.isEmpty())
			iniMaze(walls);
		Position pAgent = agent.position();
		// incremente la position actuelle de l'agent
		incMaze(pAgent.getX(), pAgent.getY());
		incMaze(friends);
		// incremente tous les positions
		incAllMaze();
		// met a zero les positons des cibles
		clearMaze(targets);
		agent.setPosition(nextPosition(pAgent));
	}

}
