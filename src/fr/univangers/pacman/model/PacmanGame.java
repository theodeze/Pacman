package fr.univangers.pacman.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanGame extends Game {
	public enum Mode {
		oneplayer,
		twoplayerC,
		twoplayerO
	}
	
	private static final long serialVersionUID = 998416452804755455L;
	private static final int nbVieMax=3;
	
	private Maze maze;
	private int score = 0;
	private List<Agent> pacmans = new ArrayList<>();
	private List<PositionAgent> positionPacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();
	private List<PositionAgent> positionGhosts = new ArrayList<>();
	private int nbLifePacmans;
	private int nbTurnVulnerables;
	private int nbFood = 0;
	private int scorePerGhosts = 200;
	private Mode mode;
	
	public int getNbLifePacmans() {
		return nbLifePacmans;
	}
	
	public int score() {
		return score;
	}
	
	public List<PositionAgent> positionPacmans() {
		return positionPacmans;
	}
	
	public List<PositionAgent> positionGhosts() {
		return positionGhosts;
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
	
	public List<Boolean> ghostsScarred() {
		List<Boolean> ghostsScarred = new ArrayList<>();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				ghostsScarred.add(ghost.isVulnerable());
		}
		return ghostsScarred;
	}
	
	public PacmanGame(int maxTurn, Maze maze, Mode mode) {
		super(maxTurn);
		this.maze = maze;
		this.mode = mode;
		this.nbLifePacmans = nbVieMax;
		init();
	}
	
	private void updatePosition() {
		positionPacmans.clear();
		for(Agent pacman : pacmans) {
			if(!pacman.isDeath())
				positionPacmans.add(pacman.position());
		}
		positionGhosts.clear();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				positionGhosts.add(ghost.position());
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
	
	public void movePacmanPlayer2(Dir dir) {
		Agent p2 = null;
		if(mode == Mode.twoplayerC)
			p2 = pacmans.get(1);
		else if(mode == Mode.twoplayerO)
			p2 = ghosts.get(0);
		if(p2 == null)
			return;
		switch(dir) {
		case EAST:
			p2.goRight();
			break;
		case NORTH:
			p2.goUp();
			break;
		case SOUTH:
			p2.goDown();
			break;
		case WEST:
			p2.goLeft();
			break;
		default:
			break;
		}	
	}
	
	public void moveAgent(Agent agent) {
		agent.action(positionPacmans(), positionGhosts(), maze.getWalls());
	}
	
	public void resetPosition() {
		for(Agent pacman : pacmans)
			pacman.resetPosition();
		for(Agent ghost : ghosts)
			ghost.resetPosition();
	}
	
	@Override
	public void initializeGame() {
		pacmans.clear();
		int p = 0;
		for(PositionAgent position : maze.getPacman_start()) {
			if((p < 1) || (p < 2 && mode == Mode.twoplayerC)) {
				pacmans.add(FactoryAgent.createPacmanPlayer(position));
				p++;
			}
			else
				pacmans.add(FactoryAgent.createPacmanRandom(position));
		}
		ghosts.clear();
		for(PositionAgent position : maze.getGhosts_start()) {
			if(p < 2 && mode == Mode.twoplayerO) {
				ghosts.add(FactoryAgent.createGhostPlayer(position));
				p++;
			}
			else
				ghosts.add(FactoryAgent.createGhostAstar(position));
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
			deadAgents(pacman);
			if(maze.isFood(pacman.position().getX(), pacman.position().getY())) {
				maze.setFood(pacman.position().getX(), pacman.position().getY(), false);
				score += 10;
				nbFood--;
				playSound("res/sounds/pacman_chomp.wav");
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				pacman.inversion();
				for (Agent ghost : ghosts) {
					ghost.vulnerability();
					
				}
				nbTurnVulnerables = 20;
				score += 50;
				playSound("res/sounds/pacman_extrapac.wav");
			}
		}
		for(Agent ghost : ghosts) {
			moveAgent(ghost);
			deadAgents(ghost);
		}
		isOver();

		updatePosition();
	}

	@Override
	public void gameOver() {
		if(nbFood == 0) {
			System.out.println("Les pacmans ont gagnée");
		} else {
			System.out.println("Les fantomes ont gagnée");
		}
	}
	
	public void lifeAgents() {
		Iterator<Agent> iter = pacmans.iterator();
		while(iter.hasNext()) {
			Agent pacman = iter.next();
			if(pacman.isDeath() && nbLifePacmans > 0) {
				pacman.vivant();
				nbLifePacmans--;
				resetPosition();
			}
		}
	}
	
	public void deadAgents(Agent agt) {	
		if(pacmans.contains(agt)) {		
			Agent pacman = agt;
			for(Agent ghost: ghosts) 
				changesForDeathState(pacman, ghost);				
		}
		
		else {		
			Agent ghost = agt;
			for(Agent pacman: pacmans)
				changesForDeathState(pacman, ghost);				
		}
	}
	
	public void changesForDeathState(Agent pacman, Agent ghost) {
		if(ghost.position().equals(pacman.position())) {
			if (ghost.isVulnerable()) {
				ghost.mort();
				score += scorePerGhosts;
				scorePerGhosts *= 2;
				playSound("res/sounds/pacman_eatghost.wav");
			} else if (ghost.isLife()) {
				pacman.mort();
				lifeAgents();
				playSound("res/sounds/pacman_death.wav");
			}
		}
	}
	
	
	public void isOver() {
		if(nbLifePacmans == 0) {
			over();
		}
		if(nbFood == 0) {
			over();
		}
	}
	
	
}
