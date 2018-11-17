package fr.univangers.pacman.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Maze implements Serializable{
	
	private static final long serialVersionUID = 1076456911984437464L;
	/** 
	 * Les differentes directions possibles pour les actions et les orientations des agents
	 */
	public static int NORTH=0;
	public static int SOUTH=1;
	public static int EAST=2;
	public static int WEST=3;
	public static int STOP=4;
	
	private int size_x;
	private int size_y;
	
	/** 
	 * Les elements du labyrinthe
	 */
	private boolean walls[][];
	private boolean foods_start[][];
	private boolean foods[][];
	private boolean capsules_start[][];
	private boolean capsules[][];

	/** 
	 * Les positions initiales des agents
	 */
	private List<PositionAgent> pacman_start;
	private List<PositionAgent> ghosts_start;

	
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
			size_x=nbX;
			size_y=nbY;
			walls=new boolean[size_x][size_y];
			foods_start=new boolean[size_x][size_y];
			capsules_start=new boolean[size_x][size_y];
			
			pacman_start =new ArrayList<>();
			ghosts_start =new ArrayList<>();
			
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
					if (ligne.charAt(x)=='.') foods_start[x][y]=true; else foods_start[x][y]=false;
					if (ligne.charAt(x)=='o') capsules_start[x][y]=true; else capsules_start[x][y]=false;
					if (ligne.charAt(x)=='P') {pacman_start.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));}
					if (ligne.charAt(x)=='G') {ghosts_start.add(new PositionAgent(x,y,PositionAgent.Dir.NORTH));}
				}
				y++;
			}			
			br.close(); 
			
			resetFoods();
			resetCapsules();
			
			if (pacman_start.size()==0)throw new Exception("Wrong input format: must specify a Pacman start");
			
			//On verifie que le labyrinthe est clos			
			for(int x=0;x<size_x;x++) if (!walls[x][0]) throw new Exception("Wrong input format: the maze must be closed");
			for(int x=0;x<size_x;x++) if (!walls[x][size_y-1]) throw new Exception("Wrong input format: the maze must be closed");
			for(y=0;y<size_y;y++) if (!walls[0][y]) throw new Exception("Wrong input format: the maze must be closed");
			for(y=0;y<size_y;y++) if (!walls[size_x-1][y]) throw new Exception("Wrong input format: the maze must be closed");
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
	public int getSizeX() {return(size_x);}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {return(size_y);}
	
	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(walls[x][y]);
	}
	
	/**
	 * Permet de savoir si il y a de la nourriture
	 */
	public boolean isFoods(int x,int y) 
	{
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(foods[x][y]);
	}

	public void setFoods(int x,int y,boolean b) {
		foods[x][y]=b;
	}
	
	public void resetFoods() {
		foods = new boolean[foods_start.length][]; 
		for(int i = 0; i < foods_start.length; i++)
			foods[i] = foods_start[i].clone();
	}
	
	/**
	 * Permet de savoir si il y a une capsule
	 */
	public boolean isCapsule(int x,int y) {
		assert((x>=0) && (x<size_x));
		assert((y>=0) && (y<size_y));
		return(capsules[x][y]);
	}
	
	public void setCapsule(int x,int y,boolean b) {
		capsules[x][y]=b;
	}
	
	public void resetCapsules() {
		capsules = new boolean[capsules_start.length][]; 
		for(int i = 0; i < capsules_start.length; i++)
			capsules[i] = capsules_start[i].clone();
	}
	
	/**
	 * Renvoie le nombre de pacmans
	 * @return
	 */
	public int getInitNumberOfPacmans() {
		return(pacman_start.size());	
	}
	
	/**
	 * Renvoie le nombre de fantomes
	 * @return
	 */
	public int getInitNumberOfGhosts() 
	{
		return(ghosts_start.size());
	}
	
	public boolean[][] getWalls() {
		return walls;
	}
	
	public List<PositionAgent> getPacman_start() {
		return pacman_start;
	}

	public void setPacman_start(List<PositionAgent> pacman_start) {
		this.pacman_start = pacman_start;
	}

	public List<PositionAgent> getGhosts_start() {
		return ghosts_start;
	}

	public void setGhosts_start(List<PositionAgent> ghosts_start) {
		this.ghosts_start = ghosts_start;
	}
	
	
}


