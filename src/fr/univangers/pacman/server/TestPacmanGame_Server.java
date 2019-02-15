package fr.univangers.pacman.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;


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
			if (args.length == 0) {
				try {
					ObjectMapper mapper = new ObjectMapper();	// Pour lecture / écriture en JSON
					p=10001;
					//p=Integer.parseInt(args[0]); // on récupère le port
					ecoute = new ServerSocket(p); // on crée le serveur
					System.out.println("Serveur mis en place ");
					//le serveur va attendre qu’une connexion arrive
						so = ecoute.accept();
						
						//BufferedReader entree = new BufferedReader(new InputStreamReader(so.getInputStream()));
						//PrintWriter sortie = new PrintWriter(so.getOutputStream(), true);
						/*String ch=entree.readLine();
						String pseudo = ch.split(" ")[0];
						String MDP = ch.split(" ")[1];
						int nbTurn = Integer.parseInt(ch.split(" ")[2]);

						//JSON from String to Object
						Maze maze = mapper.readValue(ch.split(" ")[3], Maze.class);

						//Maze maze = new Maze("res/layouts/bigMaze.lay");
						
						StrategyPacman stratPacman = getStrategyPacman(ch.split(" ")[4]);
						StrategyGhost startGhost=getStrategyGhost(ch.split(" ")[5]);			
						Mode mode = getMode(ch.split(" ")[6]);
						if(pseudo.equals("Adann")) {
							sortie.println("OK");
						}*/
								
						/*PacmanGame pacmanGame = new PacmanGame(nbTurn, maze, stratPacman, startGhost, mode);
						PacmanServerController controller = new PacmanServerController(pacmanGame,so);
						new Thread(controller).start();	*/
						//Maze maze = new Maze("res/layouts/bigMaze.lay");
						Maze maze = new Maze("res/layouts/bigMaze.lay");
						PacmanGame pg = new PacmanGame(250, maze, StrategyPacman.ASTAR, StrategyGhost.TRACKING, Mode.ONEPLAYER);
						PacmanServerController psc = new PacmanServerController(pg, so);
						new ViewGame(pg, psc, maze);
						new Thread(psc).start();
					
				} catch (IOException e) { System.out.println("Problème : "+e); }
			} else { System.out.println("Syntaxe d’appel java servTexte port\n"); }
		} 
}
