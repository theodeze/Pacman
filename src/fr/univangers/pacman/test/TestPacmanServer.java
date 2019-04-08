package fr.univangers.pacman.test;

import fr.univangers.pacman.server.Server;

public class TestPacmanServer {

	public static void main(String[] args) {
		Server server = Server.getInstance();
		if(server != null)
			new Thread(server).start();
	}
	
}
