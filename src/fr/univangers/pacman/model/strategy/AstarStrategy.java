package fr.univangers.pacman.model.strategy;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;

/**
 * Stratégie de l'algorithme A*
 */
public abstract class AstarStrategy implements Strategy {

	private static final long serialVersionUID = 6882118649697899327L;
	private List<List<Double>> gScore = new ArrayList<>();
    private List<List<Double>> fScore = new ArrayList<>();
    private List<Position> closedList = new ArrayList<>();
    private PriorityQueue<Position> openList = new PriorityQueue<>((p1, p2) -> {
    	int compare = compareFScore(p1, p2);
    	if(compare == 0)
    		compare = compareGScore(p1, p2);
    	return compare;
    });
    
    private double getGScore(Position p) {
        return gScore.get(p.getY()).get(p.getX());
    }

    private void setGScore(Position p, double score) {
        gScore.get(p.getY()).set(p.getX(), score);
    }

    private double getFScore(Position p) {
        return fScore.get(p.getY()).get(p.getX());
    }

    private void setFScore(Position p, double score) {
        fScore.get(p.getY()).set(p.getX(), score);
    }

    private void initGScore(int xSize, int ySize) {
        gScore.clear();
        for(int y = 0; y < ySize; y++) {
            gScore.add(new ArrayList<>(Collections.nCopies(xSize, Double.POSITIVE_INFINITY)));
        }
    }

    private void initFScore(int xSize, int ySize) {
        fScore.clear();
        for(int y = 0; y < ySize; y++) {
            fScore.add(new ArrayList<>(Collections.nCopies(xSize, Double.POSITIVE_INFINITY)));
        }
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un ennemi 
     */
    private boolean isNotOccupiedByEnemies(Position me, List<Position> enemies, Position p) {
    	for(Position enemy : enemies)
    		// Verifie que l'agent n'est pas l'ennemi ou si un ennemi est proche
    		if(!enemy.equals(me) && enemy.near(p))
    			return false;
    	return true;
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un ami
     */
    private boolean isNotOccupiedByFriends(Position me, List<Position> enemies, Position p) {
    	for(Position enemy : enemies)
    		// Verifie que l'agent n'est pas l'ami ou si un ami est proche
    		if(!enemy.equals(me) && enemy.equals(p))
    			return false;
    	return true;
    }

    private List<PositionAgent> neighbors(Position me, Position p, List<Position> enemies, List<Position> friends, List<List<Boolean>> walls) {
        List<PositionAgent> neighbors = new ArrayList<>();
        int xSize = walls.get(0).size();
        int ySize = walls.size();
        if(p.getX() > 0 && !walls.get(p.getY()).get(p.getX() - 1) 
        		&& isNotOccupiedByEnemies(me, enemies, new Position(p.getX() - 1, p.getY()))
        		&& isNotOccupiedByFriends(me, friends, new Position(p.getX() - 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() - 1, p.getY()));
        if(p.getY() > 0 && !walls.get(p.getY() - 1).get(p.getX()) 
        		&& isNotOccupiedByEnemies(me, enemies, new Position(p.getX(), p.getY() - 1))
    			&& isNotOccupiedByFriends(me, friends, new Position(p.getX(), p.getY() - 1)))
            neighbors.add(new PositionAgent(p.getX(), p.getY() - 1));
        if(p.getX() < xSize  && !walls.get(p.getY()).get(p.getX() + 1)  
        		&& isNotOccupiedByEnemies(me, enemies, new Position(p.getX() + 1, p.getY()))
    			&& isNotOccupiedByFriends(me, friends, new Position(p.getX() + 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() + 1, p.getY()));
        if(p.getY() < ySize  && !walls.get(p.getY() + 1).get(p.getX()) 
        		&& isNotOccupiedByEnemies(me, enemies, new Position(p.getX(), p.getY() + 1))
    			&& isNotOccupiedByFriends(me, friends, new Position(p.getX(), p.getY() + 1)))
            neighbors.add(new PositionAgent(p.getX(), p.getY() + 1));
        return neighbors;
    }
    
    private int manhattan(Position p1, Position p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    private int heuristic(Position p1, List<Position> ps2) {
    	int min = Integer.MAX_VALUE;
    	for(Position p2 : ps2) {
    		int dist = manhattan(p1, p2);
    		if(dist < min) {
    			min = dist;
    		}
    	}
    	return min;
    }

    private Position nextPosition(Map<Position, Position> cameFrom, Position current) {
    	Position nextPosition = current;
    	Position currentPosition = current;
        while(cameFrom.get(current) != null) {
            current = cameFrom.get(current);
            nextPosition = currentPosition;
            currentPosition = current;
        }
        return nextPosition;
    }
    
    private int compareGScore(Position p1, Position p2) {
    	if(getGScore(p1) < getGScore(p2))
    		return 1;
    	else if(getGScore(p1) == getGScore(p2))
    		return 0;
    	else 
    		return -1;
    }
    
    private int compareFScore(Position p1, Position p2) {
    	if(getFScore(p1) < getFScore(p2))
    		return -1;
    	else if(getFScore(p1) == getFScore(p2))
    		return 0;
    	else 
    		return 1;
    }
    
    protected Map.Entry<Double, Position> findPath(Position start, List<Position> goals, List<Position> friends, 
    		List<Position> enemies, List<List<Boolean>> walls) {    	
    	openList.clear();
        closedList.clear();
        
        Map<Position, Position> cameFrom = new HashMap<>();

        openList.offer(start);

        initGScore(walls.get(0).size(), walls.size());
        initFScore(walls.get(0).size(), walls.size());
        setGScore(start, 0);
        setFScore(start, heuristic(start, goals));

        while(!openList.isEmpty()) {
        	Position current = openList.poll();

            // Arrivée
        	for(Position goal : goals)
	            if(current.equals(goal))
	                return new AbstractMap.SimpleImmutableEntry<>(
	                		getGScore(goal), nextPosition(cameFrom, current));

            closedList.add(current);

            for(Position neighbor : neighbors(start, current, enemies, friends, walls)) {

                double newGScore = getGScore(current) + 1;
                
                // voisin a déja été évalué ou ce n'est pas le meilleur chemin
                if((closedList.contains(neighbor)) || (getGScore(neighbor) <= newGScore))
                    continue;

                // découverte d'un nouveau voisin
                if(!openList.contains(neighbor))
                    openList.offer(neighbor);

                cameFrom.put(neighbor,current);
                setGScore(neighbor, newGScore);
                setFScore(neighbor, newGScore + heuristic(neighbor, goals));
            }
        }
       
        // il n'existe pas de chemin
        return new AbstractMap.SimpleImmutableEntry<>(0.0, start);
    }
}
