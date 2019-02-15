package fr.univangers.pacman.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univangers.pacman.controller.PacmanClientController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameGetter.Mode;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyGhost;
import fr.univangers.pacman.model.PacmanGameGetter.StrategyPacman;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;
import fr.univangers.pacman.view.ViewSettings;

public class TestPacmanGame_Client {

	public static void main(String[] args) {	
		Socket so;
		String host; // le serveur
		int port; // le port de connexion
		PrintWriter sortie;
		DataInputStream entree;
		boolean connected=false;
		if (args.length == 0) { // on récupère les paramètres
			//s=args[0];
			//p=Integer.parseInt(args[1]);
			host="localhost";
			port=10001;
						
			try{// on connecte un socket
				ObjectMapper mapper = new ObjectMapper();	// Pour lecture / écriture en JSON
				so = new Socket(host, port);
				/*sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new DataInputStream(so.getInputStream());		
				ViewSettings viewSettings = new ViewSettings();
				String msg; 
				String jsonInString ; *
				
				/*while(!connected) {
					msg = viewSettings.getPseudo()+ " ";
					msg += viewSettings.getMDP()+" ";
					msg += viewSettings.getNbTurn()+ " ";
					Maze maze = viewSettings.getMaze();
					
					//Object to JSON en String
					jsonInString = mapper.writeValueAsString(maze);
					msg += jsonInString+" ";
					msg += viewSettings.getStrategyPacman()+ " ";
					msg += viewSettings.getStrategyGhost()+" ";
					msg += viewSettings.getMode();
					
					sortie.flush();
					sortie.println(msg);
					
					
					String ch=entree.readUTF();
					//if (Server.validateAccount(viewSettings.getPseudo(),viewSettings.getMDP()) {
					if (ch.split(" ")[0].equals("OK")) {
						connected = true;
					}
				}*/
					
					//viewSettings.setVisible(false);
					/*String ch=entree.readUTF();
					PacmanGame pacmanGame = mapper.readValue(ch.split(" ")[0], PacmanGame.class);
					Maze maze = viewSettings.getMaze();
					PacmanClientController pacmanClientController = new PacmanClientController(so);
					ViewCommande viewCommande = new ViewCommande(pacmanGame); 
					viewCommande.setGameController(pacmanClientController);
					new ViewGame(pacmanGame, pacmanClientController, maze);*/
					
					
					Maze maze;
					try {
						PacmanClientController pcc = new PacmanClientController(so);
						maze = new Maze("res/layouts/bigMaze.lay");
						PacmanGame game = new PacmanGame(999, maze, StrategyPacman.ASTAR, StrategyGhost.BASIC,  Mode.ONEPLAYER);
						ViewCommande vc = new ViewCommande(game);
						vc.setGameController(pcc);
						new ViewGame(game, pcc, maze);
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch(UnknownHostException e) {System.out.println(e);}
			catch (IOException e) {System.out.println("Aucun serveur n’est rattaché au port ");}
		} else {System.out.println("Syntaxe d’appel java cliTexte serveur port chaine_de_caractères\n");
		} 
	}

}
