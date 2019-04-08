package fr.univangers.pacman.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.LoginInformation;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;
import fr.univangers.pacman.view.dialog.DialogLogin;
import fr.univangers.pacman.view.dialog.DialogRetry;
import fr.univangers.pacman.view.dialog.DialogSettings;

public class Client implements Runnable {
	private static final Logger LOGGER = LogManager.getLogger("Client"); 
	private static final int MAX_TRY = 3;
	
	private Socket so;
	private PacmanGameSettings settings;
	
	private Client(Socket so, PacmanGameSettings settings) {
		this.so = so;
		this.settings = settings;
	}
	
	public static Client getInstance() {
		Client client = null;
		int nTry = MAX_TRY;
		boolean connect = false;
		boolean noRetry = true;
		while(nTry-- > 0 && !connect && noRetry) {
			LoginInformation login = DialogLogin.show();
			try {
				Socket so = new Socket(login.getHost(), login.getPort());
				PrintWriter output = new PrintWriter(so.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(so.getInputStream()));

				output.println(login.toJson());
				connect = Boolean.valueOf(input.readLine());
		        if(!connect) {
		        	noRetry = DialogRetry.show("Mots de passe/Identifiant incorect");
		        } else {
		        	PacmanGameSettings settings = DialogSettings.show();
			        output.println(settings.toJson());
		        	client = new Client(so, settings);
		        }
			} catch (UnknownHostException e) {
				noRetry = DialogRetry.show("Hote n'existe pas");
				LOGGER.warn("Hote n'existe pas");
			} catch (IOException e) {
				noRetry = DialogRetry.show("Connexion refusé");
				LOGGER.warn("Connexion refusé");
			}
		}
		return client;
	}

	@Override
	public void run() {
		try {
			PacmanClientController pcc = new PacmanClientController(so);
			Maze maze = new Maze(settings.getNameMaze());
			PacmanClient game = new PacmanClient(settings.getMaxTurn(), so);
			ViewCommande vc = new ViewCommande(game);
			vc.setGameController(pcc);
			new ViewGame(game, pcc, maze);
			game.launch();
		} catch (Exception e) {
			LOGGER.warn("Problème lancement pacman");
		}
	}
	
}
