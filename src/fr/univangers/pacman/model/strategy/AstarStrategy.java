package fr.univangers.pacman.model.strategy;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import fr.univangers.pacman.model.PositionAgent;

/**
 * Stratégie de l'aglgorithme A*
 */
public abstract class AstarStrategy implements Strategy {

	private static final long serialVersionUID = 6882118649697899327L;
	private List<List<Double>> gScore = new ArrayList<>();
    private List<List<Double>> fScore = new ArrayList<>();
    private List<PositionAgent> closedList = new ArrayList<>();
    private PriorityQueue<PositionAgent> openList = new PriorityQueue<>((p1, p2) -> {
    	int compare = compareFScore(p1, p2);
    	if(compare == 0)
    		compare = compareGScore(p1, p2);
    	return compare;
    });
    
    private double getGScore(PositionAgent p) {
        return gScore.get(p.getX()).get(p.getY());
    }

    private void setGScore(PositionAgent p, double score) {
        gScore.get(p.getX()).set(p.getY(), score);
    }

    private double getFScore(PositionAgent p) {
        return fScore.get(p.getX()).get(p.getY());
    }

    private void setFScore(PositionAgent p, double score) {
        fScore.get(p.getX()).set(p.getY(), score);
    }

    private void initGScore(int xSize, int ySize) {
        gScore.clear();
        for(int x = 0; x < xSize; x++) {
            gScore.add(new ArrayList<>(Collections.nCopies(ySize, Double.POSITIVE_INFINITY)));
        }
    }

    private void initFScore(int xSize, int ySize) {
        fScore.clear();
        for(int x = 0; x < xSize; x++) {
            fScore.add(new ArrayList<>(Collections.nCopies(ySize, Double.POSITIVE_INFINITY)));
        }
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un ennemie 
     */
    private boolean isNotOccupiedByEnemies(PositionAgent me, List<PositionAgent> enemies, PositionAgent p) {
    	for(PositionAgent enemie : enemies)
    		// Verifie que l'agent n'est pas l'enemie ou si un enemie est proche
    		if(!enemie.equals(me) && enemie.near(p))
    			return false;
    	return true;
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un amie
     */
    private boolean isNotOccupiedByFriends(PositionAgent me, List<PositionAgent> enemies, PositionAgent p) {
    	for(PositionAgent enemie : enemies)
    		// Verifie que l'agent n'est pas l'amie ou si un amie est proche
    		if(!enemie.equals(me) && enemie.equals(p))
    			return false;
    	return true;
    }

    private List<PositionAgent> neighbors(PositionAgent me, PositionAgent p, List<PositionAgent> enemies, List<PositionAgent> friends, boolean[][] walls) {
        List<PositionAgent> neighbors = new ArrayList<>();
        int xSize = walls.length;
        int ySize = walls[0].length;
        if(p.getX() > 0 && !walls[p.getX() - 1][p.getY()] 
        		&& isNotOccupiedByEnemies(me, enemies, new PositionAgent(p.getX() - 1, p.getY()))
        		&& isNotOccupiedByFriends(me, friends, new PositionAgent(p.getX() - 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() - 1, p.getY()));
        if(p.getY() > 0 && !walls[p.getX()][p.getY() - 1] 
        		&& isNotOccupiedByEnemies(me, enemies, new PositionAgent(p.getX(), p.getY() - 1))
    			&& isNotOccupiedByFriends(me, friends, new PositionAgent(p.getX(), p.getY() - 1)))
            neighbors.add(new PositionAgent(p.getX(), p.getY() - 1));
        if(p.getX() < xSize  && !walls[p.getX() + 1][p.getY()] 
        		&& isNotOccupiedByEnemies(me, enemies, new PositionAgent(p.getX() + 1, p.getY()))
    			&& isNotOccupiedByFriends(me, friends, new PositionAgent(p.getX() + 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() + 1, p.getY()));
        if(p.getY() < ySize  && !walls[p.getX()][p.getY() + 1] 
        		&& isNotOccupiedByEnemies(me, enemies, new PositionAgent(p.getX(), p.getY() + 1))
    			&& isNotOccupiedByFriends(me, friends, new PositionAgent(p.getX(), p.getY() + 1)))
            neighbors.add(new PositionAgent(p.getX(), p.getY() + 1));
        return neighbors;
    }
    
    private int manhattan(PositionAgent p1, PositionAgent p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    private int heuristic(PositionAgent p1, List<PositionAgent> ps2) {
    	int min = Integer.MAX_VALUE;
    	for(PositionAgent p2 : ps2) {
    		int dist = manhattan(p1, p2);
    		if(dist < min) {
    			min = dist;
    		}
    	}
    	return min;
    }

    private PositionAgent nextPosition(Map<PositionAgent, PositionAgent> cameFrom, PositionAgent current) {
        PositionAgent nextPosition = current;
        PositionAgent currentPosition = current;
        while(cameFrom.get(current) != null) {
            current = cameFrom.get(current);
            nextPosition = currentPosition;
            currentPosition = current;
        }
        return nextPosition;
    }
    
    private int compareGScore(PositionAgent p1, PositionAgent p2) {
    	if(getGScore(p1) < getGScore(p2))
    		return 1;
    	else if(getGScore(p1) == getGScore(p2))
    		return 0;
    	else 
    		return -1;
    }
    
    private int compareFScore(PositionAgent p1, PositionAgent p2) {
    	if(getFScore(p1) < getFScore(p2))
    		return -1;
    	else if(getFScore(p1) == getFScore(p2))
    		return 0;
    	else 
    		return 1;
    }
    
    protected Map.Entry<Double, PositionAgent> findPath(PositionAgent start, List<PositionAgent> goals, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {    	
    	openList.clear();
        closedList.clear();
        
        Map<PositionAgent, PositionAgent> cameFrom = new HashMap<>();

        openList.offer(start);

        initGScore(walls.length, walls[0].length);
        initFScore(walls.length, walls[0].length);
        setGScore(start, 0);
        setFScore(start, heuristic(start, goals));

        while(!openList.isEmpty()) {
        	PositionAgent current = openList.poll();

            // arrivé
        	for(PositionAgent goal : goals)
	            if(current.equals(goal))
	                return new AbstractMap.SimpleImmutableEntry<>(
	                		getGScore(goal), nextPosition(cameFrom, current));

            closedList.add(current);

            for(PositionAgent neighbor : neighbors(start, current, enemies, friends, walls)) {

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
        return new AbstractMap.SimpleImmutableEntry <>(0.0, start);
    }
}
