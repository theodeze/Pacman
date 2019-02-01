package fr.univangers.pacman.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanClientController implements GameController {

	private static final long serialVersionUID = -7348810349916899506L;
	private Socket so;
	private String host = "localhost";
	private int port = 5000;
	
	public PacmanClientController() {
		try {
			so = new Socket(host, port);
		} catch (IOException e) {
			System.err.println("Pas de connexion");
			so = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		so.close();
		super.finalize();
	}
	
	public void sendCommande(Commande cmd) {
		try {
			PrintWriter output = new PrintWriter(so.getOutputStream(), true);
			output.println(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendCommande(Commande cmd, String value) {
		try {
			PrintWriter output = new PrintWriter(so.getOutputStream(), true);
			output.println(cmd);
			output.println(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setTime(int time) {
		sendCommande(Commande.TIME, String.valueOf(time));
		
	}

	@Override
	public void pause() {
		sendCommande(Commande.PAUSE);
	}

	@Override
	public void restart() {
		sendCommande(Commande.RESTART);
	}

	@Override
	public void run() {
		sendCommande(Commande.RUN);
	}

	@Override
	public void step() {
		sendCommande(Commande.STEP);
	}

	@Override
	public void movePlayer1(Dir dir) {
		sendCommande(Commande.MOVE1, dir.toString());
	}

	@Override
	public void movePlayer2(Dir dir) {
		sendCommande(Commande.MOVE2, dir.toString());
	}

}
