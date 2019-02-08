package fr.univangers.pacman.test;

import fr.univangers.pacman.model.Server;

public class TestPacmanServer {

	public static void main(String[] args) {
		Server server = Server.getInstance(4400);
		new Thread(server).start();
	}
	
}
