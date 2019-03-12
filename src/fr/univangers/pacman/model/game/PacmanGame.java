package fr.univangers.pacman.model.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.univangers.pacman.model.Agent;
import fr.univangers.pacman.model.FactoryAgent;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.beans.PacmanGameState;
import fr.univangers.pacman.model.beans.PacmanGameSettings.Mode;
import fr.univangers.pacman.model.beans.PacmanGameState.Winner;

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
	protected PacmanGameSettings settings = new PacmanGameSettings();
	
	private int nbFood = 0;
	private int scorePerGhosts = 200;

	@Override
	public PacmanGameState getState() {
		return state;
	}
	
	public PacmanGame(PacmanGameSettings settings, Maze maze) {
		super(settings.getMaxTurn());
		this.maze = maze;
		this.settings = settings;
		state.setWinner(Winner.NOWINNER);
		state.setNbLifePacmans(NB_VIE_MAX);
		init();
	}
	
	private void updatePacmans() {
		state.getPositionPacmans().clear();
		for(Agent pacman : pacmans) {
			if(!pacman.isDeath())
				state.getPositionPacmans().add(pacman.position());
		}
	}
	
	private void updateGhosts() {
		state.getPositionGhosts().clear();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				state.getPositionGhosts().add(ghost.position());
		}
	}
	
	private void updatePosition() {
		updatePacmans();
		updateGhosts();
		state.getGhostsScarred().clear();
		for(Agent ghost : ghosts) {
			if(!ghost.isDeath())
				state.addGhostsScarred(ghost.isVulnerable());
		}
		notifyViews();
	}
	
	public void movePacmanPlayer1(Dir dir) {
		Agent p1 = pacmans.get(0);
		if(settings.getMode() == Mode.AUTO)
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
		if(settings.getMode() == Mode.TWOPLAYERC)
			p2 = pacmans.get(1);
		else if(settings.getMode() == Mode.TWOPLAYERO)
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
	
	public List<Position> getPositionPacmans() {
		List<Position> positions = new ArrayList<>();
		for(PositionAgent position : state.getPositionPacmans())
			positions.add(position.toPosition());
		return positions;
	}
	
	public void moveAgent(Agent agent) {
		agent.action(getPositionPacmans(), state.getPositionGhosts(), 
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
			if((nbPacmanAdd < 1 && settings.getMode() != Mode.AUTO) || (nbPacmanAdd < 2 && settings.getMode() == Mode.TWOPLAYERC)) {
				pacmans.add(FactoryAgent.createPacmanPlayer(position));
				nbPacmanAdd++;
			}
			else {
				switch(settings.getStrategyPacman()) {
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
		for(Position position : maze.getGhostsStart()) {
			if(!isAddPlayer && settings.getMode() == Mode.TWOPLAYERO) {
				ghosts.add(FactoryAgent.createGhostPlayer(position));
				isAddPlayer = true;
			} else {
				switch(settings.getStrategyGhost()) {
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
		state.setPositionFoods(new ArrayList<>(maze.getFoodsStart()));
		state.setPositionCapsules(new ArrayList<>(maze.getCapsulesStart()));
		nbFood = state.getPositionFoods().size() + state.getPositionCapsules().size();
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
			if(state.getPositionFoods().contains(pacman.position().toPosition())) {
				state.getPositionFoods().remove(pacman.position());
				state.incScore(10);
				nbFood--;
				state.setCurrentSong("res/sounds/pacman_chomp.wav");
			}
			if(state.getPositionCapsules().contains(pacman.position().toPosition())) {
				state.getPositionCapsules().remove(pacman.position().toPosition());
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
		if(state.getNbLifePacmans() == 0 || nbFood == 0)
			over();
	}
	
}
