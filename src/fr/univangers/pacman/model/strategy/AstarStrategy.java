package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

public class AstarStrategy implements Strategy {

	private static final long serialVersionUID = 6882118649697899327L;
	private List<List<Double>> gScore = new ArrayList<>();
    private List<List<Double>> fScore = new ArrayList<>();

    public double getGScore(PositionAgent p) {
        return gScore.get(p.getX()).get(p.getY());
    }

    public void setGScore(PositionAgent p, double score) {
        gScore.get(p.getX()).set(p.getY(), score);
    }

    public double getFScore(PositionAgent p) {
        return fScore.get(p.getX()).get(p.getY());
    }

    public void setFScore(PositionAgent p, double score) {
        fScore.get(p.getX()).set(p.getY(), score);
    }

    public void initGScore(int xSize, int ySize) {
        gScore.clear();
        for(int x = 0; x < xSize; x++) {
            gScore.add(new ArrayList<>(Collections.nCopies(ySize, Double.POSITIVE_INFINITY)));
        }
    }

    public void initFScore(int xSize, int ySize) {
        fScore.clear();
        for(int x = 0; x < xSize; x++) {
            fScore.add(new ArrayList<>(Collections.nCopies(ySize, Double.POSITIVE_INFINITY)));
        }
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un ennemie 
     */
	public boolean isNotOccupiedByEnemies(PositionAgent me, List<PositionAgent> enemies, PositionAgent p) {
    	for(PositionAgent enemie : enemies)
    		// Verifie que l'agent n'est pas l'enemie ou si un enemie est proche
    		if(!enemie.equals(me) && enemie.near(p))
    			return false;
    	return true;
    }
    
    /**
     * Vérifie que la case n'est pas occupé par un amie
     */
	public boolean isNotOccupiedByFriends(PositionAgent me, List<PositionAgent> enemies, PositionAgent p) {
    	for(PositionAgent enemie : enemies)
    		// Verifie que l'agent n'est pas l'amie ou si un amie est proche
    		if(!enemie.equals(me) && enemie.equals(p))
    			return false;
    	return true;
    }

    public PositionAgent minFScore(List<PositionAgent> list) {
        if(list.isEmpty())
            return null;
        PositionAgent minPoint = list.get(0);
        Double minValue = getFScore(list.get(0));
        for(PositionAgent currentPoint : list) {
            Double currentValue = getFScore(currentPoint);
            if(currentValue < minValue) {
                minPoint = currentPoint;
                minValue = currentValue;
            }
        }
        return minPoint;
    }

    public List<PositionAgent> neighbors(PositionAgent me, PositionAgent p, List<PositionAgent> enemies, List<PositionAgent> friends, boolean[][] walls) {
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

    public int heuristic(PositionAgent p1, PositionAgent p2) {
        // distance de Manhattan
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    public PositionAgent nextPosition(Map<PositionAgent, PositionAgent> cameFrom, PositionAgent current) {
        PositionAgent nextPosition = current;
        PositionAgent currentPosition = current;
        while(cameFrom.get(current) != null) {
            current = cameFrom.get(current);
            nextPosition = currentPosition;
            currentPosition = current;
        }
        return nextPosition;
    }

    public PositionAgent findPath(PositionAgent start, PositionAgent goal, List<PositionAgent> enemies, List<PositionAgent> friends, boolean[][] walls) {
        List<PositionAgent> closedList = new ArrayList<>();
        List<PositionAgent> openList = new ArrayList<>();
        Map<PositionAgent, PositionAgent> cameFrom = new HashMap<>();

        openList.add(start);

        initGScore(walls.length, walls[0].length);
        initFScore(walls.length, walls[0].length);
        setGScore(start, 0);
        setFScore(start, heuristic(start, goal));

        while(!openList.isEmpty()) {
        	PositionAgent current = minFScore(openList);

            // arrivé
            if(current.equals(goal))
                return nextPosition(cameFrom, current);

            closedList.add(current);
            openList.remove(current);

            for(PositionAgent neighbor : neighbors(start, current, enemies, friends, walls)) {

                double newGScore = getGScore(current) + 1;
                
                // voisin a déja été évalué ou ce n'est pas le meilleur chemin
                if((closedList.contains(neighbor)) || (getGScore(neighbor) <= newGScore))
                    continue;

                // découverte d'un nouveau voisin
                if(!openList.contains(neighbor))
                    openList.add(neighbor);

                cameFrom.put(neighbor,current);
                setGScore(neighbor, newGScore);
                setFScore(neighbor, newGScore + heuristic(neighbor,goal));
            }
        }
       
        // il n'existe pas de chemin
        return start;
    }
    
    public PositionAgent nearestTarget(PositionAgent me, List<PositionAgent> targets) {
    	if(targets.isEmpty())
    		return null;
    	PositionAgent nearestTarget = targets.get(0);
    	int nearestDistance = heuristic(me, nearestTarget);
    	for(PositionAgent currentTarget : targets) {
    		int currentDistance = heuristic(me, currentTarget);
    		if(nearestDistance > currentDistance) {
    			nearestTarget = currentTarget;
    			nearestDistance = currentDistance;
    		}			
    	}
    	return nearestTarget;
    }	

	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies, boolean[][] walls) {
		PositionAgent target = nearestTarget(agent.position(), targets);
		if(target == null) {
			return;
		}
		PositionAgent newPosition = findPath(agent.position(), target, enemies, friends, walls);
		if(newPosition.equals(agent.position())) {
			newPosition  = findPath(agent.position(), target, enemies, Collections.emptyList(), walls);
		}
        agent.setPosition(newPosition);
	}
}
