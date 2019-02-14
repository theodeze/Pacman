package fr.univangers.pacman.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.univangers.pacman.model.Maze;

public class TestJack {

	public static void main(String[] args) {
		try {
			ObjectMapper mapper = new ObjectMapper();	// Pour lecture / écriture en JSON

			Maze maze = new Maze("res/layouts/bigMaze.lay");
			String json = mapper.writeValueAsString(maze);
			System.out.println(json);
			Maze maze2 = mapper.readValue(json, Maze.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
