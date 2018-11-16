package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public boolean isNotOccupiedByGhost(List<PositionAgent> positionGhosts, PositionAgent p) {
    	for(PositionAgent positionGhost : positionGhosts)
    		if(PositionAgent.equal(positionGhost, p))
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

    public List<PositionAgent> neighbors(PositionAgent p, List<PositionAgent> positionGhosts, boolean[][] walls) {
        List<PositionAgent> neighbors = new ArrayList<>();
        int xSize = walls.length;
        int ySize = walls[0].length;
        if(p.getX() > 0 && !walls[p.getX() - 1][p.getY()] && isNotOccupiedByGhost(positionGhosts, new PositionAgent(p.getX() - 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() - 1, p.getY()));
        if(p.getY() > 0 && !walls[p.getX()][p.getY() - 1] && isNotOccupiedByGhost(positionGhosts, new PositionAgent(p.getX(), p.getY() - 1)))
            neighbors.add(new PositionAgent(p.getX(), p.getY() - 1));
        if(p.getX() < xSize  && !walls[p.getX() + 1][p.getY()] && isNotOccupiedByGhost(positionGhosts, new PositionAgent(p.getX() + 1, p.getY())))
            neighbors.add(new PositionAgent(p.getX() + 1, p.getY()));
        if(p.getY() < ySize  && !walls[p.getX()][p.getY() + 1] && isNotOccupiedByGhost(positionGhosts, new PositionAgent(p.getX(), p.getY() + 1)))
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

    public PositionAgent findPath(PositionAgent start, PositionAgent goal, List<PositionAgent> positionGhosts, boolean[][] walls) {
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
            if(PositionAgent.equal(current, goal))
                return nextPosition(cameFrom, current);

            closedList.add(current);
            openList.remove(current);

            for(PositionAgent neighbor : neighbors(current, positionGhosts, walls)) {

                double newGScore = getGScore(current) + 1;
                
                // voisin a déja été évalué ou ce n'est pas le mellieur chemin
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
    	PositionAgent currentTarget = targets.get(0);
    	int nearestDistance = heuristic(me, currentTarget);
    	for(PositionAgent target : targets) {
    		int currentDistance = heuristic(me, target);
    		if(nearestDistance > currentDistance)
    			
    	}
    	
    }	

	@Override
	public void move(Agent agent, List<PositionAgent> positionPacmans, List<PositionAgent> positionGhosts, boolean[][] walls) {
		if(positionPacmans.isEmpty())
			return;
			System.out.println("Pas besoin");
		}
        agent.setPosition(findPath(agent.position(), positionPacmans.get(0), positionGhosts, walls));
	}
}
