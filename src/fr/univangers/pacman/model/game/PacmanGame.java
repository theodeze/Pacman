package fr.univangers.pacman.model.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.FactoryAgent;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.gamestate.PacmanGameState;
import fr.univangers.pacman.model.gamestate.PacmanGameState.Mode;
import fr.univangers.pacman.model.gamestate.PacmanGameState.StrategyGhost;
import fr.univangers.pacman.model.gamestate.PacmanGameState.StrategyPacman;
import fr.univangers.pacman.model.gamestate.PacmanGameState.Winner;

/**
 * PacmanGame est la classe principale du jeu 
 * Elle permet de créer la carte, de compter le score,
 * compter le  nombre de vie des pacmans de nourritures, 
 * les différents scores et agents ainsi que leur position
 */

public class PacmanGame extends Game {
	
	private static final long serialVersionUID = 998416452804755455L;
	private static final int NB_VIE_MAX = 3;
	
	private Maze maze;
	private List<Agent> pacmans = new ArrayList<>();
	private List<Agent> ghosts = new ArrayList<>();

	protected PacmanGameState state = new PacmanGameState();
	
	private int nbFood = 0;
	private int scorePerGhosts = 200;
	private Mode mode;
	private StrategyPacman strategyPacman;
	private StrategyGhost strategyGhost;

	@Override
	public PacmanGameState getState() {
		return state;
	}
	
	public PacmanGame(int maxTurn, Maze maze, StrategyPacman strategyPacman, StrategyGhost strategyGhost, Mode mode) {
		super(maxTurn);
		this.maze = maze;
		this.strategyPacman = strategyPacman;
		this.strategyGhost = strategyGhost;
		this.mode = mode;
		state.setWinner(Winner.NOWINNER);
		state.setNbLifePacmans(NB_VIE_MAX);
		init();
	}
	
	private void updatePacmans() {
		state.clearPositionPacmans();
		for(Agent pacman : pacmans) {
			if(!pacman.isDeath())
				state.addPositionPacmans(pacman.position());
		}
	}
	
	private void updateGhosts() {
		state.clearPositionGhosts();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				state.addPositionGhosts(ghost.position());
		}
	}
	
	private void updateFoodsCapsules() {
		state.clearPositionFoods();
		state.clearPositionCapsules();
		for(int x = 0; x < maze.getSizeX(); x++) {
			for(int y = 0; y < maze.getSizeY(); y++) {
				if(maze.isFoods(x, y))
					state.addPositionFoods(new PositionAgent(x, y));
				if(maze.isCapsule(x, y))
					state.addPositionCapsules(new PositionAgent(x, y));
			}
		}
	}
	
	private void updatePosition() {
		updatePacmans();
		updateGhosts();
		updateFoodsCapsules();
		state.clearGhostsScarred();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				state.addGhostsScarred(ghost.isVulnerable());
		}
		notifyViews();
	}
	
	public void movePacmanPlayer1(Dir dir) {
		Agent p1 = pacmans.get(0);
		if(mode == Mode.AUTO)
			return;
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
		if(mode == Mode.TWOPLAYERC)
			p2 = pacmans.get(1);
		else if(mode == Mode.TWOPLAYERO)
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
		agent.action(state.getPositionPacmans(), state.getPositionGhosts(), 
				Stream.concat(state.getPositionFoods().stream(), state.getPositionCapsules().stream())
                .collect(Collectors.toList()), maze.getWalls());
	}
	
	public void resetPosition() {
		for(Agent pacman : pacmans)
			pacman.resetPosition();
		for(Agent ghost : ghosts)
			ghost.resetPosition();
	}
	
	private void initializePacman() {
		pacmans.clear();
		int nbPacmanAdd = 0;
		for(PositionAgent position : maze.getPacmanStart()) {
			if((nbPacmanAdd < 1 && mode != Mode.AUTO) || (nbPacmanAdd < 2 && mode == Mode.TWOPLAYERC)) {
				pacmans.add(FactoryAgent.createPacmanPlayer(position));
				nbPacmanAdd++;
			}
			else {
				switch(strategyPacman) {
				case ASTAR:
					pacmans.add(FactoryAgent.createPacmanAstar(position));
					break;
				case BASIC:
					pacmans.add(FactoryAgent.createPacmanBasic(position));
					break;
				case NONE:
					pacmans.add(FactoryAgent.createPacmanNone(position));
					break;
				case RANDOM:
					pacmans.add(FactoryAgent.createPacmanRandom(position));
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void initializeGhost() {
		ghosts.clear();
		boolean isAddPlayer = false;
		for(PositionAgent position : maze.getGhostsStart()) {
			if(!isAddPlayer && mode == Mode.TWOPLAYERO) {
				ghosts.add(FactoryAgent.createGhostPlayer(position));
				isAddPlayer = true;
			} else {
				switch(strategyGhost) {
				case ASTAR:
					ghosts.add(FactoryAgent.createGhostAstar(position));
					break;
				case BASIC:
					ghosts.add(FactoryAgent.createGhostBasic(position));
					break;
				case TRACKING:
					ghosts.add(FactoryAgent.createGhostTracking(position));
					break;
				case NONE:
					ghosts.add(FactoryAgent.createGhostNone(position));
					break;
				case RANDOM:
					ghosts.add(FactoryAgent.createGhostRandom(position));
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void initializeFood() {
		nbFood = 0;
		maze.resetFoods();
		for(int x = 0; x < maze.getSizeX(); x++) {
			for(int y = 0; y < maze.getSizeY(); y++) {
				nbFood += maze.isFoods(x, y) ? 1 : 0;
				nbFood += maze.isCapsule(x, y) ? 1 : 0;
			}
		}
		maze.resetCapsules();
	}
	
	@Override
	public void initializeGame() {
		initializePacman();
		initializeGhost();
		initializeFood();
		state.setScore(0);
		state.setNbLifePacmans(NB_VIE_MAX);
		state.setWinner(Winner.NOWINNER);
		updatePosition();
		state.setCurrentSong("res/sounds/pacman_beginning.wav");
	}

	@Override
	/**
	 *  Fonction qui modifie le jeu à chaque tour
	 */
	
	public void takeTurn() {
		for(Agent pacman : pacmans) {
			moveAgent(pacman);
			deadAgents(pacman);
			if(maze.isFoods(pacman.position().getX(), pacman.position().getY())) {
				maze.setFoods(pacman.position().getX(), pacman.position().getY(), false);
				state.incScore(10);
				nbFood--;
				state.setCurrentSong("res/sounds/pacman_chomp.wav");
			}
			if(maze.isCapsule(pacman.position().getX(), pacman.position().getY())) {
				maze.setCapsule(pacman.position().getX(), pacman.position().getY(), false);
				for (Agent ghost : ghosts) {
					ghost.vulnerability();
				}
				state.incScore(50);
				nbFood--;
				state.setCurrentSong("res/sounds/pacman_extrapac.wav");
			}
		}
		
		updatePosition();
		
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
			state.setWinner(Winner.PACMANWINNER);
			state.setCurrentSong("res/sounds/pacman_intermission.wav");
			notifyViews();
		} else {
			state.setWinner(Winner.GHOSTWINNER);
			state.setCurrentSong("res/sounds/pacman_death.wav");
			notifyViews();
		}
	}
	
	/**
	 *  Fonction lifeAgents permet de réduire le nombre de 
	 *  vie des Agents Pacmans, jusqu'à la fin de la partie
	 */
	
	public void lifeAgents() {
		Iterator<Agent> iter = pacmans.iterator();
		while(iter.hasNext()) {
			Agent pacman = iter.next();
			if(pacman.isDeath() && state.getNbLifePacmans() > 0) {
				pacman.alive();
				state.incNbLifePacmans(1);
				resetPosition();
			}
		}
	}
	
	/**
	 *  Fonction deadAgents teste pour chaque agent concerné s'il est mort
	 *  et change son état en conséquence
	 */
	
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
	
	/**
	 *  Fonction changesForDeathState appelée dans deadAgents
	 */
	public void changesForDeathState(Agent pacman, Agent ghost) {
		if(ghost.position().equals(pacman.position())) {
			if (ghost.isVulnerable()) {
				ghost.dead();
				state.incScore(scorePerGhosts);
				scorePerGhosts *= 2;
				state.setCurrentSong("res/sounds/pacman_eatghost.wav");
			} else if (ghost.isLife()) {
				pacman.dead();
				lifeAgents();
				state.setCurrentSong("res/sounds/pacman_death.wav");
			}
		}
	}
	
	public void isOver() {
		if(state.getNbLifePacmans() == 0) {
			over();
		}
		if(nbFood == 0) {
			over();
		}
	}
	
}
