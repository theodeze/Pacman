package fr.univangers.pacman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PacmanClient extends Game implements PacmanGameGetter {
	
	private static final long serialVersionUID = -5821764757690849185L;
	private static final Logger LOGGER = LogManager.getLogger("Client"); 
	private Socket so;
	
	private int nbLifePacmans = 3;
	private Winner winner = Winner.NOWINNER;
	private int score = 0;
	private List<PositionAgent> positionPacmans = new ArrayList<>();
	private List<PositionAgent> positionGhosts = new ArrayList<>();
	private List<PositionAgent> positionFoods = new ArrayList<>();
	private List<Boolean> ghostsScarred = new ArrayList<>();
	
	public PacmanClient(int maxTurn, Socket so) {
		super(maxTurn);
		this.so = so;
	}
	
	@Override
	public void run() {
		while(!so.isClosed()) {
			readData();
			notifyViews();
		}
	}
	
	private void readData() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));
			String[] datas = input.readLine().split("///");
			String typeData = datas[0];
			String data = datas[1];
			LOGGER.info("Réception données (client) " + typeData + "\n" + data);
		    Type listType = new TypeToken<List<PositionAgent>>(){}.getType();
		    
			switch(TypeData.valueOf(typeData)) {
			case GHOSTS_SCARRED:
				ghostsScarred = new Gson().fromJson(data, listType);
				break;
			case NB_LIFE_PACMANS:
				nbLifePacmans = new Gson().fromJson(data, int.class);
				break;
			case POSITION_FOODS:
				positionFoods = new Gson().fromJson(data, listType);
				break;
			case POSITION_GHOSTS:
				positionGhosts = new Gson().fromJson(data, listType);
				break;
			case POSITION_PACMANS:
				positionPacmans = new Gson().fromJson(data, listType);
				break;
			case SCORE:
				score = new Gson().fromJson(data, int.class);
				break;
			case WINNER:
				winner = new Gson().fromJson(data, winner.getClass());
				break;
			default:
				LOGGER.warn("Type de données incorrect");
				break;
			}
		} catch (IOException e) {
			LOGGER.warn("Données incorrectes");
		}
	}

	@Override
	public int getNbLifePacmans() {
		return nbLifePacmans;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Winner getWinner() {
		return winner;
	}

	@Override
	public List<PositionAgent> getPositionPacmans() {
		return positionPacmans;
	}

	@Override
	public List<PositionAgent> getPositionGhosts() {
		return positionGhosts;
	}

	@Override
	public List<PositionAgent> getPositionFoods() {
		return positionFoods;
	}

	@Override
	public List<Boolean> getGhostsScarred() {
		return ghostsScarred;
	}

	@Override
	public void initializeGame() {
	}

	@Override
	public void takeTurn() {
	}

	@Override
	public void gameOver() {
	}
	
	
}
