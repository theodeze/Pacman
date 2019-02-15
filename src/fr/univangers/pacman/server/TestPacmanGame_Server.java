package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univangers.pacman.controller.PacmanServerController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameGetter.Mode;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyGhost;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyPacman;


public class TestPacmanGame_Server {
	
	public static synchronized StrategyPacman getStrategyPacman(String stratPacman) {
		StrategyPacman difficulty;
		if(stratPacman.equals("BASIC"))
	    	difficulty = StrategyPacman.BASIC;
		else if(stratPacman.equals("RANDOM"))
				difficulty = StrategyPacman.RANDOM;
		else if(stratPacman.equals("NONE"))
				difficulty = StrategyPacman.NONE;
		else 
			difficulty= StrategyPacman.ASTAR;
		return difficulty;
		}
	
	public static synchronized StrategyGhost getStrategyGhost(String stratGhost) {
		StrategyGhost difficulty;
		if(stratGhost.equals("TRACKING"))
	    	difficulty = StrategyGhost.TRACKING;
		else if(stratGhost.equals("BASIC"))
	    	difficulty = StrategyGhost.BASIC;
		else if(stratGhost.equals("RANDOM"))
			difficulty = StrategyGhost.RANDOM;
		else if(stratGhost.equals("NONE"))
			difficulty = StrategyGhost.NONE;
		else 
			difficulty= StrategyGhost.ASTAR;
		return difficulty;
		}
	public static synchronized Mode getMode(String inMode) {
	Mode mode ;
    if(inMode.equals("AUTO"))
    	mode = Mode.AUTO;
    else if(inMode.equals("TWOPLAYERC"))
    	mode = Mode.TWOPLAYERC;
	else if(inMode.equals("TWOPLAYERO"))
		mode = Mode.TWOPLAYERO;
	else 
		mode = Mode.ONEPLAYER;
    return mode;
	}
					
		public static synchronized void broadcast(Socket socket, String msg) {
				try {
					DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
					
					
					}catch(SocketException e) {
						System.out.println("Déconnection d'un client "+ socket);}
												
					catch(IOException e) {
						System.out.println("Error Broadcast: "+e);
					}
		}
			
		public static void main(String[] args) throws Exception {		
			int p; // le port d’écoute
			ServerSocket ecoute;
			Socket so;
			ObjectMapper mapper = new ObjectMapper();
			if (args.length == 0) {
				try {
					p=10001;
					//p=Integer.parseInt(args[0]); // on récupère le port
					ecoute = new ServerSocket(p); // on crée le serveur
					System.out.println("Serveur mis en place ");
					//le serveur va attendre qu’une connexion arrive
						so = ecoute.accept();
						
						BufferedReader entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
						String ch=entree.readLine();
						String pseudo = ch.split(" ")[0];
						String MDP = ch.split(" ")[1];
						int nbTurn = Integer.parseInt(ch.split(" ")[2]);
						
						//JSON from file to Object
						//Maze user = mapper.readValue(new File("./json/maze_vers_serveur.json"), Maze.class);

						//JSON from String to Object
						Maze maze = mapper.readValue(ch.split(" ")[3], Maze.class);
						
						StrategyPacman stratPacman = getStrategyPacman(ch.split(" ")[4]);
						StrategyGhost startGhost=getStrategyGhost(ch.split(" ")[5]);			
						Mode mode = getMode(ch.split(" ")[6]);
												
						PacmanGame pacmanGame = new PacmanGame(nbTurn, maze, stratPacman, startGhost, mode);
						PacmanServerController controller = new PacmanServerController(pacmanGame,so);
						new Thread(controller).start();				
					
				} catch (IOException e) { System.out.println("Problème : "+e); }
			} else { System.out.println("Syntaxe d’appel java servTexte port\n"); }
		} 
}
