package fr.univangers.pacman.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.univangers.pacman.view.ViewSettings;

public class TestPacmanGame_Client {

	public static void main(String[] args) {	
		Socket so;
		String s; // le serveur
		int p; // le port de connexion
		PrintWriter sortie;
		DataInputStream entree;
		if (args.length == 3) { // on récupère les paramètres
			s=args[0];
			p=Integer.parseInt(args[1]);
			try{// on connecte un socket
				so = new Socket(s, p);
				sortie = new PrintWriter(so.getOutputStream(), true);
				entree = new DataInputStream(so.getInputStream());	
											
				ViewSettings viewSettings = new ViewSettings();
				String msg = viewSettings.getPseudo()+ " ";
				msg +=viewSettings.getMDP()+" ";
				msg+=viewSettings.getNbTurn()+ " ";
				msg+=viewSettings.getMaze()+ " ";
				msg+=viewSettings.getStrategyPacman()+ " ";
				msg+=viewSettings.getStrategyGhost()+" ";
				msg+= viewSettings.getMode();
				//PacmanGame pacmanGame = new PacmanGame(getNbTurn(), getMaze(), getStrategyPacman(), getStrategyGhost(), getMode());
				sortie.println(msg);

				} catch(UnknownHostException e) {System.out.println(e);}
			catch (IOException e) {System.out.println("Aucun serveur n’est rattaché au port ");}
		} else {System.out.println("Syntaxe d’appel java cliTexte serveur port chaine_de_caractères\n");
		} 
	}

}
