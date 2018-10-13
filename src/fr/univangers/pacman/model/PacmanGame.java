package fr.univangers.pacman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.strategy.EscapeStrategy;
import fr.univangers.pacman.model.strategy.NearestAttackStrategy;
import fr.univangers.pacman.model.strategy.PlayerStrategy;

public class PacmanGame extends Game {

	private static final long serialVersionUID = 998416452804755455L;
	public static final int nbVieMax=3;
	
	private Maze maze;
	private int score = 0;
	private List<Agent> pacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();
	private List<Integer> nbViePacmans = new ArrayList<>();
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
	
	private void playSound(String filename) {
		try {
	        AudioInputStream audioIn;
			audioIn = AudioSystem.getAudioInputStream(new File(filename));
	        Clip clip = AudioSystem.getClip();
	        clip.close();
	        clip.open(audioIn);
	        clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			if(!ghost.isDeath())
				addPositionGhosts(ghost.position());
		}
		notifyViews();
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
		agent.action(positionPacmans(), maze.getWalls());
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
			Agent pacman = new Agent(Agent.Type.PACMAN, position);
			pacman.setStrategy(new PlayerStrategy(), new PlayerStrategy());
			pacmans.add(pacman);
			nbViePacmans.add(nbVieMax);
			
		}
		ghosts.clear();
		for(PositionAgent position : maze.getGhosts_start()) {
			Agent ghost = new Agent(Agent.Type.GHOST, position);
			ghost.setStrategy(new NearestAttackStrategy(), new EscapeStrategy());
			ghosts.add(ghost);
		}
		nbFood = 0;
		for(int x = 0; x < maze.getSizeX(); x++) {
			for(int y = 0; y < maze.getSizeY(); y++) {
				nbFood += maze.isFood(x, y) ? 1 : 0;
			}
		}
		playSound("res/sounds/pacman_beginning.wav");
	}

	@Override
	public void takeTurn() {
		if(nbTurnVulnerables == 0) {
			setGhostsScarred(false);
			for (Agent ghost : ghosts) {
				ghost.stopVulnerability();
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
				playSound("res/sounds/pacman_chomp.wav");
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				setGhostsScarred(true);
				pacman.inversion();
				for (Agent ghost : ghosts) {
					ghost.vulnerability();
					
				}
				nbTurnVulnerables = 20;
				score += 50;
				playSound("res/sounds/pacman_eatghost.wav");
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
		Iterator<Agent> iter = pacmans.iterator();
		while (iter.hasNext()) {
			Agent pacman = iter.next();
			if(pacman.isDeath()) {
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
		for(Agent ghost: ghosts) {
			for(Agent pacman: pacmans) {
				if((ghost.position().getX()==pacman.position().getX())&&(ghost.position().getY()==pacman.position().getY())) {
					if (ghost.isVulnerable()) {
						ghost.mort();
						score += scorePerGhosts;
						scorePerGhosts *= 2;
					} else if (ghost.isLife()) {
						pacman.mort();
						vieAgents();
						playSound("res/sounds/pacman_death.wav");
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
