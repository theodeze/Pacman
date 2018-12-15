package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

/**
 * Stratégie pour Exploration pour les fantomes
 * Elle utilise un tableau qui représente le labyrinthe et 
 * la valeur d'une case représente une "trace de la cible" 
 * (0 si récente et infinie si un mur) et à chaque exécution 
 * la "trace" ce dissipe et si un fantôme passe dessus la 
 * "trace" ce dissipe plus vite. Le fantôme se dirige suit 
 * la "trace".
 */
public class ExplorerStrategy implements Strategy {

	private static final long serialVersionUID = -3703352132989593376L;
	private static List<List<Double>> maze = new ArrayList<>();
	
	private void iniMaze(boolean[][] walls) {
		int xSize = walls.length;
		int ySize = walls[0].length;
        for(int x = 0; x < xSize; x++) {
        	maze.add(new ArrayList<>(Collections.nCopies(ySize, 0.0)));
        	for(int y = 0; y < ySize; y++)
        		if(walls[x][y])
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
	
	private void incMaze(List<PositionAgent> targets) {
		for(PositionAgent target : targets)
			incMaze(target.getX(), target.getY());
	}
	
	private void incAllMaze() {
        for(int x = 0; x < maze.size(); x++)
        	for(int y = 0; y < maze.get(0).size(); y++)
        		incMaze(x, y);
	}
	
	private void clearMaze(List<PositionAgent> targets) {
		for(PositionAgent target : targets)
			setMaze(target.getX(), target.getY(), 0);
	}
	
	private PositionAgent nextPosition(PositionAgent pAgent) {
		PositionAgent newPosition = new PositionAgent(pAgent);
		double min = Double.POSITIVE_INFINITY;
		List<PositionAgent> positions = new ArrayList<>();
		PositionAgent cp1 = new PositionAgent(pAgent);
		cp1.setX(cp1.getX() + 1);
		positions.add(cp1);
		PositionAgent cp2 = new PositionAgent(pAgent);
		cp2.setX(cp2.getX() - 1);
		positions.add(cp2);
		PositionAgent cp3 = new PositionAgent(pAgent);
		cp3.setY(cp3.getY() + 1);
		positions.add(cp3);
		PositionAgent cp4 = new PositionAgent(pAgent);
		cp4.setY(cp4.getY() - 1);
		positions.add(cp4);
		for(PositionAgent currentPosition : positions)	
			if(getMaze(currentPosition.getX(), currentPosition.getY()) < min) {
				min = getMaze(currentPosition.getX(), currentPosition.getY());
				newPosition = currentPosition;
			}
		return newPosition;
	}
	
	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies,
			boolean[][] walls) {
		if(maze.isEmpty())
			iniMaze(walls);
		PositionAgent pAgent = agent.position();
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
