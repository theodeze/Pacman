package fr.univangers.pacman.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *	La classe Maze permet de créer la carte qui sera utilisée 
 *	aussi bien pour la vue des utilisateurs que pour le contrôle du jeu
 *	Sur la carte, on définit les murs, le placement des capsules et gommes, 
 *	ainsi que les positions des différents agents du jeu au début
 */

public class Maze implements Serializable{
	
	private static final long serialVersionUID = 1076456911984437464L;
	private static final Logger LOGGER = LogManager.getLogger("Maze"); 
	
	private int sizeX;
	private int sizeY;
	
	/** 
	 * Les éléments du labyrinthe
	 */
	private List<List<Boolean>> walls = new ArrayList<>();
	private List<Position> foodsStart = new ArrayList<>();
	private List<Position> capsulesStart = new ArrayList<>();

	/** 
	 * Les positions initiales des agents
	 */
	private List<PositionAgent> pacmanStart = new ArrayList<>();
	private List<Position> ghostsStart = new ArrayList<>();

	
	public Maze(String filename) throws MazeException {
		try{
			updateMaze(filename);
			validateMaze();
			LOGGER.info("### Maze loaded.");
		} catch (MazeException e){
			throw new MazeException("Probleme a la lecture du fichier: "+e.getMessage());
		}
	}
	
	private void validateMaze() throws MazeException {
		if (pacmanStart.isEmpty())
			throw new MazeException("Wrong input format: must specify a Pacman start");
		
		//On verifie que le labyrinthe est clos			
		for(int x = 0; x < sizeX; x++) 
			if(!isWall(x, 0) || !isWall(x, sizeY - 1)) 
				throw new MazeException("Wrong input format: the maze must be closed");
		for(int y = 0; y < sizeY; y++) 
			if (!isWall(0, y) || !isWall(sizeX - 1, y)) 
				throw new MazeException("Wrong input format: the maze must be closed");
	}
	
	private void updateMaze(String fileName) throws MazeException {
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			LOGGER.info("Layout file is "+ fileName);
			//Lecture du fichier pour determiner la taille du labyrinthe
			//et pour mettre a jour le labyrinthe
			int y = 0;
			int nbX = 0;
			int nbY = 0;
			String ligne = null;
			while((ligne = br.readLine())!=null) {
				ligne = ligne.trim();
				walls.add(new ArrayList<>());
				for(int x=0; x < ligne.length(); x++) {
					walls.get(y).add(ligne.charAt(x)=='%');
					if(ligne.charAt(x)=='.') 
						foodsStart.add(new Position(x, y));
					else if(ligne.charAt(x)=='o') 
						capsulesStart.add(new Position(x, y));
					else if(ligne.charAt(x)=='P') 
						pacmanStart.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));
					else if(ligne.charAt(x)=='G') 
						ghostsStart.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));
				}
				if(nbX==0)
					nbX=ligne.length();
				else if(nbX!=ligne.length()) 
					throw new MazeException("Wrong Input Format: all lines must have the same size");
				nbY++;
				y++;
			}
			sizeX = nbX;
			sizeY = nbY;
			LOGGER.info("### Size of maze is " + nbX + ";" + nbY);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * Renvoie la taille X du labyrtinhe
	 */
	public int getSizeX() {
		return(sizeX);
	}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {
		return(sizeY);
	}
	
	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x, int y) {
		return walls.get(y).get(x);
	}
	
	/**
	 * Renvoie le nombre de pacmans
	 */
	public int getInitNumberOfPacmans() {
		return(pacmanStart.size());	
	}
	
	/**
	 * Renvoie le nombre de fantomes
	 */
	public int getInitNumberOfGhosts() {
		return(ghostsStart.size());
	}
	
	public List<Position> getFoodsStart() {
		return foodsStart;
	}

	public void setFoodsStart(List<Position> foodsStart) {
		this.foodsStart = foodsStart;
	}

	public List<Position> getCapsulesStart() {
		return capsulesStart;
	}

	public void setCapsulesStart(List<Position> capsulesStart) {
		this.capsulesStart = capsulesStart;
	}
	
	public List<List<Boolean>> getWalls() {
		return walls;
	}
	
	public List<PositionAgent> getPacmanStart() {
		return pacmanStart;
	}

	public void setPacmanStart(List<PositionAgent> pacmanStart) {
		this.pacmanStart = pacmanStart;
	}

	public List<Position> getGhostsStart() {
		return ghostsStart;
	}

	public void setGhostsStart(List<Position> ghostsStart) {
		this.ghostsStart = ghostsStart;
	}
	
	
}


