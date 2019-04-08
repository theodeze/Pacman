package fr.univangers.pacman.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.beans.PacmanGameSettings;
import fr.univangers.pacman.model.beans.PacmanGameSettings.*;
import fr.univangers.pacman.view.PanelPacmanGame;

public class DialogSettings {
	private static final int MAX_X = 100;
	private static final int MAX_Y = 100;
	private static final Logger LOGGER = LogManager.getLogger("Dialog"); 
	private PanelPacmanGame panelPreview;
	private JPanel blankNorth = new JPanel();
	private JPanel blankEast = new JPanel();
	private JPanel blankWest = new JPanel();
	private JPanel blankSouth = new JPanel();
	private JPanel panel;
	private JComboBox<File> listNameMaze;
	private JComboBox<Mode> listMode;
	private JSpinner maxTurn;
	private JComboBox<StrategyPacman> listStrategyPacman;
	private JComboBox<StrategyGhost> listStrategyGhost;
	
	private DialogSettings() {
		panel = new JPanel(new BorderLayout(5, 5));
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2)); 
	    JPanel preview = new JPanel(new BorderLayout()); 
	    
	    listNameMaze = new JComboBox<>(new File("res/layouts").listFiles());
	    listNameMaze.setSelectedIndex(6);
	    listNameMaze.addActionListener(
	    		arg -> {
					try {
						Maze maze = new Maze(((File) listNameMaze.getSelectedItem()).toString());
						preview.remove(panelPreview);
						updatePreview(maze, preview);
		    			preview.add(panelPreview, BorderLayout.CENTER);
		    		    panel.revalidate();
		    			panel.repaint();
					} catch (Exception e) {
						LOGGER.warn(e.getMessage());
					}
	    		});
	    label.add(new JLabel("Nom maze", SwingConstants.RIGHT));
	    controls.add(listNameMaze);
	    
	    listMode = new JComboBox<>(Mode.values());
	    label.add(new JLabel("Mode de jeu", SwingConstants.RIGHT));
	    controls.add(listMode);
	    
	    maxTurn = new JSpinner(new SpinnerNumberModel(500, 0, Integer.MAX_VALUE, 1));
	    label.add(new JLabel("Nombre de tours", SwingConstants.RIGHT));
	    controls.add(maxTurn);
	    
	    listStrategyPacman = new JComboBox<>(StrategyPacman.values());
	    label.add(new JLabel("Strategies des Pacmans", SwingConstants.RIGHT));
	    controls.add(listStrategyPacman);
	    
	    listStrategyGhost = new JComboBox<>(StrategyGhost.values());
	    label.add(new JLabel("Strategies des Fantomes", SwingConstants.RIGHT));
	    controls.add(listStrategyGhost);

	    JLabel title = new JLabel("Paramêtre de la partie", SwingConstants.CENTER);
	    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
	    panel.add(title, BorderLayout.NORTH);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(controls, BorderLayout.CENTER);
		try {
			Maze maze = new Maze(((File) listNameMaze.getSelectedItem()).toString());
			updatePreview(maze, preview);
		} catch (Exception e) {
			panelPreview = null;
		}
		preview.add(panelPreview, BorderLayout.CENTER);
	    panel.add(preview, BorderLayout.EAST);
	}
	
	private void updatePreview(Maze maze, JPanel preview) {
		panelPreview = new PanelPacmanGame(maze);
		double scale = (double)maze.getSizeX() / (double)maze.getSizeY();
		int y = MAX_Y;
		int x = MAX_X;
		if(scale > 1) {
			y /= scale;
		} else {
			x *= scale;
		}
		panelPreview.setPreferredSize(new Dimension(x, y));
		blankNorth.setPreferredSize(
				new Dimension(MAX_Y, (MAX_X - y) / 2));
		preview.remove(blankNorth);
		preview.add(blankNorth, BorderLayout.NORTH);
		blankEast.setPreferredSize(
				new Dimension((MAX_Y - x) / 2, MAX_X));
		preview.remove(blankEast);
		preview.add(blankEast, BorderLayout.EAST);
		blankWest.setPreferredSize(
				new Dimension((MAX_Y - x) / 2, MAX_X));
		preview.remove(blankWest);
		preview.add(blankWest, BorderLayout.WEST);
		blankSouth.setPreferredSize(
				new Dimension(MAX_Y, (MAX_X - y) / 2));
		preview.remove(blankSouth);
		preview.add(blankSouth, BorderLayout.SOUTH);
	}
	
	private PacmanGameSettings getSettings() {
		PacmanGameSettings settings = new PacmanGameSettings();
		settings.setMaxTurn((int) maxTurn.getValue());
		settings.setMode((Mode) listMode.getSelectedItem());
		settings.setNameMaze(((File) listNameMaze.getSelectedItem()).toString());
		settings.setStrategyGhost((StrategyGhost) listStrategyGhost.getSelectedItem());
		settings.setStrategyPacman((StrategyPacman) listStrategyPacman.getSelectedItem());
		return settings;
	}
	
	public static PacmanGameSettings show() {
		DialogSettings dialogSettings = new DialogSettings();
		JOptionPane.showOptionDialog(null, dialogSettings.panel, "Pacman", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
				new String[] {"Valider"}, "Valider");
		 return dialogSettings.getSettings();
	}

}
