package fr.univangers.pacman.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *	La classe Maze permet de créer la carte qui sera utilisée 
 *	aussi bien pour la vue des utilisateurs que pour le contrôle du jeu
 *	Sur la carte, on définit les murs, le placement des capsules et gommes, 
 *	ainsi que les positions des différents agents du jeu au début
 */
public class Maze implements Serializable{
	
	private static final long serialVersionUID = 1076456911984437464L;
	
	private int sizeX;
	private int sizeY;
	
	/** 
	 * Les éléments du labyrinthe
	 */
	private boolean walls[][];
	private boolean foodsStart[][];
	private boolean foods[][];
	private boolean capsulesStart[][];
	private boolean capsules[][];

	/** 
	 * Les positions initiales des agents
	 */
	private List<PositionAgent> pacmanStart;
	private List<PositionAgent> ghostsStart;

	
	public Maze(String filename) throws Exception
	{
		try{
			System.out.println("Layout file is "+filename);
			//Lecture du fichier pour determiner la taille du labyrinthe
			InputStream ips=new FileInputStream(filename); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			int nbX=0;
			int nbY=0;
			while ((ligne=br.readLine())!=null)
			{
				ligne=ligne.trim();
				if (nbX==0) {nbX=ligne.length();}
				else if (nbX!=ligne.length()) throw new Exception("Wrong Input Format: all lines must have the same size");
				nbY++;
			}			
			br.close(); 
			System.out.println("### Size of maze is "+nbX+";"+nbY);
			
			//Initialisation du labyrinthe
			sizeX=nbX;
			sizeY=nbY;
			walls=new boolean[sizeX][sizeY];
			foodsStart=new boolean[sizeX][sizeY];
			capsulesStart=new boolean[sizeX][sizeY];
			
			pacmanStart =new ArrayList<>();
			ghostsStart =new ArrayList<>();
			
			//Lecture du fichier pour mettre a jour le labyrinthe
			ips=new FileInputStream(filename); 
			ipsr=new InputStreamReader(ips);
			br=new BufferedReader(ipsr);
			int y=0;
			while ((ligne=br.readLine())!=null)
			{
				ligne=ligne.trim();

				for(int x=0;x<ligne.length();x++)
				{
					if (ligne.charAt(x)=='%') walls[x][y]=true; else walls[x][y]=false;
					if (ligne.charAt(x)=='.') foodsStart[x][y]=true; else foodsStart[x][y]=false;
					if (ligne.charAt(x)=='o') capsulesStart[x][y]=true; else capsulesStart[x][y]=false;
					if (ligne.charAt(x)=='P') {pacmanStart.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));}
					if (ligne.charAt(x)=='G') {ghostsStart.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));}
				}
				y++;
			}			
			br.close(); 
			
			resetFoods();
			resetCapsules();
			
			if (pacmanStart.isEmpty())throw new Exception("Wrong input format: must specify a Pacman start");
			
			//On verifie que le labyrinthe est clos			
			for(int x=0;x<sizeX;x++) if (!walls[x][0]) throw new Exception("Wrong input format: the maze must be closed");
			for(int x=0;x<sizeX;x++) if (!walls[x][sizeY-1]) throw new Exception("Wrong input format: the maze must be closed");
			for(y=0;y<sizeY;y++) if (!walls[0][y]) throw new Exception("Wrong input format: the maze must be closed");
			for(y=0;y<sizeY;y++) if (!walls[sizeX-1][y]) throw new Exception("Wrong input format: the maze must be closed");
			System.out.println("### Maze loaded.");
			
		}		
		catch (Exception e){
			e.printStackTrace();
			throw new Exception("Probleme a la lecture du fichier: "+e.getMessage());
		}
	}
	
	/**
	 * Renvoie la taille X du labyrtinhe
	 */
	public int getSizeX() {return(sizeX);}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {return(sizeY);}
	
	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x,int y) 
	{
		assert((x>=0) && (x<sizeX));
		assert((y>=0) && (y<sizeY));
		return(walls[x][y]);
	}
	
	/**
	 * Permet de savoir s'il y a de la nourriture
	 */
	public boolean isFoods(int x,int y) 
	{
		assert((x>=0) && (x<sizeX));
		assert((y>=0) && (y<sizeY));
		return(foods[x][y]);
	}

	public void setFoods(int x,int y,boolean b) {
		foods[x][y]=b;
	}
	
	public void resetFoods() {
		foods = new boolean[foodsStart.length][]; 
		for(int i = 0; i < foodsStart.length; i++)
			foods[i] = foodsStart[i].clone();
	}
	
	/**
	 * Permet de savoir s'il y a une capsule
	 */
	public boolean isCapsule(int x,int y) {
		assert((x>=0) && (x<sizeX));
		assert((y>=0) && (y<sizeY));
		return(capsules[x][y]);
	}
	
	public void setCapsule(int x,int y,boolean b) {
		capsules[x][y]=b;
	}
	
	public void resetCapsules() {
		capsules = new boolean[capsulesStart.length][]; 
		for(int i = 0; i < capsulesStart.length; i++)
			capsules[i] = capsulesStart[i].clone();
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
	public int getInitNumberOfGhosts() 
	{
		return(ghostsStart.size());
	}
	
	public boolean[][] getWalls() {
		return walls;
	}
	
	public List<PositionAgent> getPacmanStart() {
		return pacmanStart;
	}

	public void setPacmanStart(List<PositionAgent> pacmanStart) {
		this.pacmanStart = pacmanStart;
	}

	public List<PositionAgent> getGhostsStart() {
		return ghostsStart;
	}

	public void setGhostsStart(List<PositionAgent> ghostsStart) {
		this.ghostsStart = ghostsStart;
	}
	
	
}


