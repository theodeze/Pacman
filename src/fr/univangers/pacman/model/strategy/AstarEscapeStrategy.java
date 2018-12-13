package fr.univangers.pacman.model.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.PositionAgent;

public class AstarEscapeStrategy extends AstarStrategy {

	private static final long serialVersionUID = -8042348962940629614L;
	
	@Override
	public void move(Agent agent, List<PositionAgent> targets, List<PositionAgent> friends, List<PositionAgent> enemies,
			boolean[][] walls) {
		double maxScore = 0;
		PositionAgent newPosition = agent.position();
		
		List<PositionAgent> positions = new ArrayList<>();
		PositionAgent cp1 = new PositionAgent(newPosition);
		cp1.setX(cp1.getX() + 1);
		positions.add(cp1);
		PositionAgent cp2 = new PositionAgent(newPosition);
		cp2.setX(cp2.getX() - 1);
		positions.add(cp2);
		PositionAgent cp3 = new PositionAgent(newPosition);
		cp3.setY(cp3.getY() + 1);
		positions.add(cp3);
		PositionAgent cp4 = new PositionAgent(newPosition);
		cp4.setY(cp4.getY() - 1);
		positions.add(cp4);
		for(PositionAgent currentPosition : positions)	
			if(!walls[currentPosition.getX()][currentPosition.getY()]) {
				Entry<Double, PositionAgent> entry = 
						findPath(currentPosition, enemies, Collections.emptyList(), Collections.emptyList(), walls);
				if(entry.getKey() > maxScore) {
					maxScore = entry.getKey();
					newPosition = currentPosition;
				}
			}
        agent.setPosition(newPosition);
	}
	
}
