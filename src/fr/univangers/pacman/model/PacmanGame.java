package fr.univangers.pacman.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanGame extends Game {

	private static final long serialVersionUID = 998416452804755455L;
	private Maze maze;
	private int score = 0;
	private List<Agent> pacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();
	private Random r = new Random();
	private int nbTurnVulnerables;
	
	public int score() {
		return score;
	}
	
	public PacmanGame(int maxTurn, Maze maze) {
		super(maxTurn);
		this.maze = maze;
		init();
	}
	
	private void updatePosition() {
		clearPositionPacman();
		for(Agent pacman : pacmans) {
			addPositionPacman(pacman.position());
		}
		clearPositionGhosts();
		for(Agent ghost : ghosts) {
			addPositionGhosts(ghost.position());
		}
		notifyViews();
	}

	public boolean isLegalMove(Agent agent) {
		PositionAgent newPosition = agent.newPosition();
		return !maze.isWall(newPosition.getX(), newPosition.getY());
	}
	
	public void movePacmanPlayer1(Dir dir) {
		Agent p1 = pacmans.get(0);
		switch(dir) {
		case EAST:
			p1.goRight();
			break;
		case NORTH:
			p1.goUp();
			break;
		case SOUTH:
			p1.goDown();
			break;
		case WEST:
			p1.goLeft();
			break;
		default:
			break;
		}
	}
	
	public void moveAgent(Agent agent) {
		if(isLegalMove(agent)) {
			agent.move();
		} /*else {
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
		}*/
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
		if(nbTurnVulnerables == 0) {
			setGhostsScarred(false);
		} else {
			nbTurnVulnerables--;
		}
		for(Agent pacman : pacmans) {
			moveAgent(pacman);
			if(maze.isFood(pacman.position().getX(), pacman.position().getY())) {
				maze.setFood(pacman.position().getX(), pacman.position().getY(), false);
				score += 10;
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				setGhostsScarred(true);
				nbTurnVulnerables = 20;
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
			System.out.println("Les fantomes ont gagn�e");
		}
		if(ghosts.isEmpty()) {
			System.out.println("Les pacmans ont gagn�e");
		}
	}

}
