package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGame.Mode;
import fr.univangers.pacman.model.PacmanGame.StrategyGhost;
import fr.univangers.pacman.model.PacmanGame.StrategyPacman;


public class TestPacmanGame_Server {
	
	public static synchronized StrategyPacman getStrategyPacman(String stratPacman) {
		StrategyPacman difficulty;
		if(stratPacman.equals("StrategyPacman.BASIC"))
	    	difficulty = StrategyPacman.BASIC;
		else if(stratPacman.equals("StrategyPacman.RANDOM"))
				difficulty = StrategyPacman.RANDOM;
		else if(stratPacman.equals("StrategyPacman.NONE"))
				difficulty = StrategyPacman.NONE;
		else 
			difficulty= StrategyPacman.ASTAR;
		return difficulty;
		}
	
	public static synchronized StrategyGhost getStrategyGhost(String stratGhost) {
		StrategyGhost difficulty;
		if(stratGhost.equals("StrategyPacman.TRACKING"))
	    	difficulty = StrategyGhost.TRACKING;
		else if(stratGhost.equals("StrategyPacman.BASIC"))
	    	difficulty = StrategyGhost.BASIC;
		else if(stratGhost.equals("StrategyPacman.RANDOM"))
			difficulty = StrategyGhost.RANDOM;
		else if(stratGhost.equals("StrategyPacman.NONE"))
			difficulty = StrategyGhost.NONE;
		else 
			difficulty= StrategyGhost.ASTAR;
		return difficulty;
		}
	public static synchronized Mode getMode(String inMode) {
	Mode mode ;
    if(inMode.equals("Mode.AUTO"))
    	mode = Mode.AUTO;
    else if(inMode.equals("Mode.TWOPLAYERC"))
    	mode = Mode.TWOPLAYERC;
	else if(inMode.equals("Mode.TWOPLAYERO"))
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
			
		public static void main(String[] args) {		
			int p; // le port d’écoute
			ServerSocket ecoute;
			Socket so;
			if (args.length == 1) {
				try {
					p=Integer.parseInt(args[0]); // on récupère le port
					ecoute = new ServerSocket(p); // on crée le serveur
					System.out.println("Serveur mis en place ");
					while (true) {// le serveur va attendre qu’une connexion arrive
						so = ecoute.accept();
						
						BufferedReader entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
						String ch=entree.readLine();
						String pseudo = ch.split(" ")[0];
						String MDP = ch.split(" ")[1];
						int nbTurn = Integer.parseInt(ch.split(" ")[2]);
						Maze maze; 	// Transformer String en Maze
						StrategyPacman stratPacman = getStrategyPacman(ch.split(" ")[4]);
						StrategyGhost startGhost=getStrategyGhost(ch.split(" ")[5]);			
						Mode mode = getMode(ch.split(" ")[6]);
												
						//PacmanGame pacmanGame = new PacmanGame(nbTurn, maze, stratPacman, startGhost, mode);
				
					}
				} catch (IOException e) { System.out.println("Problème\n"+e); }
			} else { System.out.println("Syntaxe d’appel java servTexte port\n"); } 
			} 
		

}
