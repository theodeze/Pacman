package fr.univangers.pacman.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univangers.pacman.controller.PacmanClientController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;
import fr.univangers.pacman.view.ViewSettings;

public class TestPacmanGame_Client {

	public static void main(String[] args) {	
		Socket so;
		String s; // le serveur
		int p; // le port de connexion
		PrintWriter sortie;
		DataInputStream entree;
		boolean connected=false;
		if (args.length == 0) { // on récupère les paramètres
			//s=args[0];
			//p=Integer.parseInt(args[1]);
			s="localhost";
			p=10001;
			
			ObjectMapper mapper = new ObjectMapper();	// Pour écriture en JSON
			
			try{// on connecte un socket
				so = new Socket(s, p);
				sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new DataInputStream(so.getInputStream());	
					
				while(!connected) {
					ViewSettings viewSettings = new ViewSettings();
					
					String msg = viewSettings.getPseudo()+ " ";
					msg +=viewSettings.getMDP()+" ";
					msg+=viewSettings.getNbTurn()+ " ";
					Maze maze = viewSettings.getMaze();
					
					//Object to JSON en String
					String jsonInString = mapper.writeValueAsString(maze);
					msg+=jsonInString+" ";
					msg+=viewSettings.getStrategyPacman()+ " ";
					msg+=viewSettings.getStrategyGhost()+" ";
					msg+= viewSettings.getMode();
					
					if( viewSettings.getPseudo()=="" || viewSettings.getMDP()=="") {
						sortie.println(msg);
					}
					if (true) {
						connected = true;
						viewSettings.setVisible(false);
					}
				}
								
				PacmanGame pacmanGame ;
				
				//PacmanClientController pacmanClientController = new PacmanClientController();
				
				//ViewCommande viewCommande = new ViewCommande(pacmanGame); 
				//viewCommande.setGameController(pacmanClientController);
				//new ViewGame(pacmanGame, pacmanClientController, viewSettings.getMaze());
				
				} catch(UnknownHostException e) {System.out.println(e);}
			catch (IOException e) {System.out.println("Aucun serveur n’est rattaché au port ");}
		} else {System.out.println("Syntaxe d’appel java cliTexte serveur port chaine_de_caractères\n");
		} 
	}

}
