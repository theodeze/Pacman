package fr.univangers.pacman.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanGame extends Game {

	private static final long serialVersionUID = 998416452804755455L;
	public static final int VIVANT=0;
	public static final int MORT=1;
	public static final int INVERSE=2;	
	public static final int nbVieMax=3;
	
	private Maze maze;
	private int score = 0;
	private List<Agent> pacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();
	private List<Integer> nbViePacmans = new ArrayList<>();
	private Random r = new Random();
	private int nbTurnVulnerables;
	private int nbFood = 0;
	private int scorePerGhosts = 200;
	
	public int score() {
		return score;
	}
	
	public int getNbViePacman(int vieDuPacman) {
		return nbViePacmans.get(vieDuPacman);
	}
	
	public void setNbViePacman(int vieDuPacman, int newValeur) {	
		nbViePacmans.set(vieDuPacman,newValeur);
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
			if(ghost.getEtatActuel().getEtat()!=MORT)
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
		agent.deathTurn();
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
	
	public void reinitPosition() {
		int index = 0;
		for(PositionAgent position : maze.getPacman_start()) {
			pacmans.get(index++).setPosition(position);
		}
		index = 0;
		for(PositionAgent position : maze.getGhosts_start()) {
			ghosts.get(index++).setPosition(position);
		}
	}
	
	@Override
	public void initializeGame() {
		pacmans.clear();
		for(PositionAgent position : maze.getPacman_start()) {
			pacmans.add(new Agent(Agent.Type.PACMAN, position));
			nbViePacmans.add(nbVieMax);
			
		}
		ghosts.clear();
		for(PositionAgent position : maze.getGhosts_start()) {
			ghosts.add(new Agent(Agent.Type.GHOST, position));
		}
		nbFood = 0;
		for(int x = 0; x < maze.getSizeX(); x++) {
			for(int y = 0; y < maze.getSizeY(); y++) {
				nbFood += maze.isFood(x, y) ? 1 : 0;
			}
		}
	}

	@Override
	public void takeTurn() {
		if(nbTurnVulnerables == 0) {
			setGhostsScarred(false);
			for(Agent pacman : pacmans) {
				if (pacman.getEtatActuel().getEtat()!=VIVANT)
					pacman.vivant();
			}
			for (Agent ghost : ghosts) {
				if(ghost.getEtatActuel().getEtat()==INVERSE)
					ghost.vivant();
			}
			nbTurnVulnerables--;
			scorePerGhosts = 200;
		} 
		if(nbTurnVulnerables > 0) {
			nbTurnVulnerables--;
		}
		for(Agent pacman : pacmans) {
			moveAgent(pacman);
			if(maze.isFood(pacman.position().getX(), pacman.position().getY())) {
				maze.setFood(pacman.position().getX(), pacman.position().getY(), false);
				score += 10;
				nbFood--;
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				setGhostsScarred(true);
				pacman.inversion();
				for (Agent ghost : ghosts) {
					if(ghost.getEtatActuel().getEtat()!=MORT)
						ghost.inversion();
				}
				nbTurnVulnerables = 20;
				score += 50;
			}
		}
		for(Agent ghost : ghosts) {
			moveAgent(ghost);
		}
		mortAgents();
		isOver();

		updatePosition();
	}

	@Override
	public void gameOver() {
		if(pacmans.isEmpty()) {
			System.out.println("Les fantomes ont gagnée");
		}
		if(nbFood == 0) {
			System.out.println("Les pacmans ont gagnée");
		}
	}
	
	public void vieAgents() {
		int count=0;
		Iterator<Agent> Iter = pacmans.iterator();
		while (Iter.hasNext()) {
			Agent pacman = Iter.next();
			if(pacman.getEtatActuel().getEtat()==MORT) {
				if (getNbViePacman(count)>0) {
					pacman.vivant();
					setNbViePacman(count,getNbViePacman(count)-1);
					reinitPosition();
				}
				else {
					pacmans.remove(pacman);
				}
			}
			count++;
		}
	}
	
	public void mortAgents() {	
		for (Agent ghost: ghosts) {
			for (Agent pacman: pacmans) {

				if ((ghost.position().getX()==pacman.position().getX())&&(ghost.position().getY()==pacman.position().getY())) {
					if (pacman.getEtatActuel().getEtat()==INVERSE) {
						ghost.mort();
						score += scorePerGhosts;
						scorePerGhosts *= 2;
					}
					
					else {
						if (ghost.getEtatActuel().getEtat()==VIVANT) {
							pacman.mort();
							vieAgents();
						}
					}
				}
			}
		}
	}
	
	public void isOver() {
		if(pacmans.isEmpty()) {
			over();
		}
		if(nbFood == 0) {
			over();
		}
	}
	
	
}
