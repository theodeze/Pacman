package fr.univangers.pacman.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacmanGame extends Game {

	private static final long serialVersionUID = 998416452804755455L;
	private Maze maze;
	private int score = 0;
	private List<Agent> pacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();
	private Random r = new Random();
	
	public int score() {
		return score;
	}
	
	public PacmanGame(int maxTurn, Maze maze) {
		super(maxTurn);
		this.maze = maze;
		init();
	}
	
	private void updatePosition() {
		positionPacman.clear();
		for(Agent pacman : pacmans) {
			positionPacman.add(pacman.position());
		}
		positionGhosts.clear();
		for(Agent ghost : ghosts) {
			positionGhosts.add(ghost.position());
		}
		notifyViews();
	}

	public boolean isLegalMove(Agent agent) {
		PositionAgent newPosition = agent.newPosition();
		return !maze.isWall(newPosition.getX(), newPosition.getY());
	}
	
	public void moveAgent(Agent agent) {
		if(isLegalMove(agent)) {
			agent.move();
		} else {
			switch(r.nextInt(4)) {
			case 0:
				agent.goUp();
				break;
			case 1:
				agent.goDown();
				break;
			case 2:
				agent.goLeft();
				break;
			case 3:
				agent.goRight();
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void initializeGame() {
		pacmans.clear();
		for(PositionAgent position : maze.getPacman_start()) {
			pacmans.add(new Agent(Agent.Type.PACMAN, position));
			
		}
		ghosts.clear();
		for(PositionAgent position : maze.getGhosts_start()) {
			ghosts.add(new Agent(Agent.Type.GHOST, position));
		}
	}

	@Override
	public void takeTurn() {
		for(Agent pacman : pacmans) {
			moveAgent(pacman);
			if(maze.isFood(pacman.position().getX(), pacman.position().getY())) {
				maze.setFood(pacman.position().getX(), pacman.position().getY(), false);
				score += 10;
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				score += 10;
			}
		}
		for(Agent ghost : ghosts) {
			moveAgent(ghost);
		}
		updatePosition();
	}

	@Override
	public void gameOver() {
		if(pacmans.isEmpty()) {
			System.out.println("Les fantomes ont gagnée");
		}
		if(ghosts.isEmpty()) {
			System.out.println("Les pacmans ont gagnée");
		}
	}

}
