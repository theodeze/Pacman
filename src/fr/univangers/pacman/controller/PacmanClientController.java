package fr.univangers.pacman.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.PositionAgent.Dir;

public class PacmanClientController implements GameController {

	private static final long serialVersionUID = -7348810349916899506L;
	private static final Logger LOGGER = LogManager.getLogger("CLIENT"); 
	private Socket so;
	
	public PacmanClientController(Socket so) {
		this.so = so;
	}
	
	@Override
	protected void finalize() throws Throwable {
		so.close();
		super.finalize();
	}
	
	public void sendCommande(Commande cmd) {
		try {
			PrintWriter output = new PrintWriter(so.getOutputStream(), true);
			output.println(cmd.toString());
		} catch (IOException e) {
			LOGGER.warn("ICI");
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
	public void launch() {
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
