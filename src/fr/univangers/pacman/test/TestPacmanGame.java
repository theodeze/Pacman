package fr.univangers.pacman.test;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class TestPacmanGame {

	public static void main(String[] args) {
		File directory = new File("res/layouts");
	    JComboBox<File> list = new JComboBox<>(directory.listFiles());
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JLabel("Choix map :"), BorderLayout.NORTH);
	    panel.add(list);
        JOptionPane.showMessageDialog(null, panel, "Choix map", JOptionPane.QUESTION_MESSAGE);
	    File choice = (File) list.getSelectedItem();
	    
		Maze maze;
		try {
			maze = new Maze(choice.toString());
		} catch (Exception e) {
			return;
		}
		PacmanGame pacmanGame = new PacmanGame(250,maze);
		PacmanGameController pacmanGameController = new PacmanGameController(pacmanGame);
		ViewCommande viewCommande = new ViewCommande(pacmanGame); 
		viewCommande.setGameController(pacmanGameController);
		new ViewGame(pacmanGame, pacmanGameController, maze);
	}

}
