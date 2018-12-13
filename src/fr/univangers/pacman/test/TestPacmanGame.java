package fr.univangers.pacman.test;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGame.Mode;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class TestPacmanGame {

	public static void main(String[] args) {
		File directory = new File("res/layouts");
	    JComboBox<File> layouts = new JComboBox<>(directory.listFiles());
	    layouts.setSelectedIndex(6); //8 par défault
	    JPanel panelLayouts = new JPanel(new BorderLayout());
	    panelLayouts.add(new JLabel("Choix map :"), BorderLayout.NORTH);
	    panelLayouts.add(layouts);
        JOptionPane.showMessageDialog(null, panelLayouts, "Choix map", JOptionPane.QUESTION_MESSAGE);
	    File choiceLayout = (File) layouts.getSelectedItem();

		Maze maze;
		try {
			maze = new Maze(choiceLayout.toString());
		} catch (Exception e) {
			return;
		} 
	
	    Vector<String> listModes = new Vector<>();
	    if(!maze.getPacman_start().isEmpty()) {
	    	listModes.add("Auto");
	    	listModes.add("Un joueur");
	    }
	    if(maze.getPacman_start().size() >= 2) {
	    	listModes.add("Deux joueurs (Comperatif)");
	    }
	    if((!maze.getPacman_start().isEmpty()) && (!maze.getGhosts_start().isEmpty())) {
	    	listModes.add("Deux joueurs (Opposition)");
	    }
	    
	    JComboBox<String> modes = new JComboBox<>(listModes);
	    JPanel panelMode = new JPanel(new BorderLayout());
	    panelMode.add(new JLabel("Choix mode :"), BorderLayout.NORTH);
	    panelMode.add(modes);
        JOptionPane.showMessageDialog(null, panelMode, "Choix mode", JOptionPane.QUESTION_MESSAGE);


	    PacmanGame.Mode mode = Mode.ONEPLAYER;
	    if(modes.getSelectedItem() == "Auto")
	    	mode = Mode.AUTO;
	    if(modes.getSelectedItem() == "Deux joueurs (Comperatif)")
	    	mode = Mode.TWOPLAYERC;
		else if(modes.getSelectedItem() == "Deux joueurs (Opposition)")
			mode = Mode.TWOPLAYERO;
	    
		PacmanGame pacmanGame = new PacmanGame(250,maze,mode);
		PacmanGameController pacmanGameController = new PacmanGameController(pacmanGame);
		ViewCommande viewCommande = new ViewCommande(pacmanGame); 
		viewCommande.setGameController(pacmanGameController);
		new ViewGame(pacmanGame, pacmanGameController, maze);
	}

}
