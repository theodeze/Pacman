package fr.univangers.pacman.test;

import fr.univangers.pacman.client.Client;

public class TestPacmanClient {

	public static void main(String[] args) {
		Client client = Client.getInstance("localhost", 4400);
		if(client != null)
			new Thread(client).start();
		
	}
	
}
