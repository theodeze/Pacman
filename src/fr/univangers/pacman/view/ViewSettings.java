package fr.univangers.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import fr.univangers.pacman.controller.PacmanClientController;
import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGame.StrategyPacman;
import fr.univangers.pacman.model.PacmanGame.StrategyGhost;
import fr.univangers.pacman.model.PacmanGame.Mode;

/**
 * Interface qui permet de sélectionner le labyrinthe ainsi que les paramètres de la partie
 */
public class ViewSettings extends JFrame {
	private static final long serialVersionUID = 6791950037033830292L;

	private PanelPacmanGame panelPreview;
	
	private Maze maze;
	
	private JPanel panel;
	private JTextField pseudo  ;
	private JPasswordField mdp;
	private JComboBox<File> listMaze;
	private JComboBox<String> listMode;
	private JSpinner nbTurn;
	private JComboBox<String> listStrategyPacman;
	private JComboBox<String> listStrategyGhost;
	
	public ViewSettings() {
		super();

        setTitle("Configuration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
      
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Liste Maze
		File directory = new File("res/layouts");
		listMaze = new JComboBox<>(directory.listFiles());
		listMaze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateMaze((File)listMaze.getSelectedItem());
			}
		});
		
		panel.add(new JLabel("Votre pseudo :"));
		pseudo = new JTextField();
		pseudo.setColumns(10);
		panel.add(pseudo);	
		
		panel.add(new JLabel("Votre mot de passe :"));
		mdp = new JPasswordField();
		mdp.setColumns(10);
		panel.add(mdp);
		
		panel.add(new JLabel("Choix labyrinthe :"));
		panel.add(listMaze);

        try {
        	maze = new Maze(directory.listFiles()[0].toString());
			panelPreview = new PanelPacmanGame(maze);
			setSize(new Dimension(maze.getSizeX() * 20, maze.getSizeY() * 20 + 330));
			centerView();
		} catch (Exception e) {
		}
        
		// Liste Mode
		listMode = new JComboBox<>();
		updateMode();
		panel.add(new JLabel("Mode de jeu :"));
		panel.add(listMode);
		
		// Liste Nombre de tours
		nbTurn = new JSpinner(new SpinnerNumberModel(250, 1, 999, 1));
		panel.add(new JLabel("Nombre de tour :"));
		panel.add(nbTurn);
		
		// Liste Stratégie pacman
		listStrategyPacman = new JComboBox<>();
		updateStrategyPacman();
		panel.add(new JLabel("Strategie pacman :"));
		panel.add(listStrategyPacman);
		
		// Liste Stratégie fantôme
		listStrategyGhost = new JComboBox<>();
		updateStrategyGhost();
		panel.add(new JLabel("Stratégie fantôme :"));
		panel.add(listStrategyGhost);
        
		JButton buttonLaunch = new JButton("Lancer");
		buttonLaunch.addActionListener((arg0) -> {
			
			// à Supprimer
			
		});
		panel.add(buttonLaunch);
		
		JButton buttonClose = new JButton("Fermer");
		buttonClose.addActionListener((arg0) -> System.exit(0));
		panel.add(buttonClose);
		
		JLabel labelPreview = new JLabel("Menu");
		labelPreview.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
		labelPreview.setHorizontalAlignment((int)JLabel.CENTER_ALIGNMENT);;
		add(labelPreview, BorderLayout.PAGE_START);
		
        add(panelPreview, BorderLayout.CENTER);

        add(panel, BorderLayout.PAGE_END);
        
		setVisible(true);
	}
	
	public String getPseudo() {
		return pseudo.getText();
	}
	
	public String getMDP() {
		return mdp.getPassword().toString();
	}
	
	public Maze getMaze() {
		return maze;
	}
	
	public int getNbTurn() {
		return (int)nbTurn.getValue();
	}
	
	private void updateMaze(File file) {
		System.out.println(file.toString());
		try {
        	maze = new Maze(file.toString());
        	remove(panelPreview);
        	
			panelPreview = new PanelPacmanGame(maze);
			setSize(new Dimension(20*maze.getSizeX(), 20*maze.getSizeY() + 280));
			centerView();
			add(panelPreview, BorderLayout.CENTER);
        	revalidate();
        	repaint();
		} catch (Exception e) {
			
		}
		updateMode();
	}
	
	private void updateMode() {
	    Vector<String> modes = new Vector<>();
	    if(!maze.getPacman_start().isEmpty()) {
	    	modes.add("Auto");
	    	modes.add("Un joueur");
	    }
	    if(maze.getPacman_start().size() >= 2) {
	    	modes.add("Deux joueurs (Comperatif)");
	    }
	    if((!maze.getPacman_start().isEmpty()) && (!maze.getGhosts_start().isEmpty())) {
	    	modes.add("Deux joueurs (Opposition)");
	    }
	    listMode.removeAllItems();
	    for(String mode : modes)
	    	listMode.addItem(mode);
	}
	
	public Mode getMode() {
	    Mode mode = Mode.ONEPLAYER;
	    if(listMode.getSelectedItem().equals("Auto"))
	    	mode = Mode.AUTO;
	    else if(listMode.getSelectedItem().equals("Deux joueurs (Comperatif)"))
	    	mode = Mode.TWOPLAYERC;
		else if(listMode.getSelectedItem().equals("Deux joueurs (Opposition)"))
			mode = Mode.TWOPLAYERO;
	    return mode;
	}
	
	private void updateStrategyPacman() {
	    Vector<String> difficulties = new Vector<>();
	    difficulties.add("A*");
	    difficulties.add("Basique");
	    difficulties.add("Aléatoire");
	    difficulties.add("Rien");
	    listStrategyPacman.removeAllItems();
	    for(String difficulty : difficulties)
	    	listStrategyPacman.addItem(difficulty);
	}
	
	private void updateStrategyGhost() {
	    Vector<String> difficulties = new Vector<>();
	    difficulties.add("A* (Difficile)");
	    difficulties.add("Pister (Normale)");
	    difficulties.add("Basique (Facile)");
	    difficulties.add("Aléatoire (Facile)");
	    difficulties.add("Rien (Paisible)");
	    listStrategyGhost.removeAllItems();
	    for(String difficulty : difficulties)
	    	listStrategyGhost.addItem(difficulty);
	}
	
	public StrategyPacman getStrategyPacman() {
		StrategyPacman difficulty = StrategyPacman.ASTAR;
	    if(listStrategyPacman.getSelectedItem().equals("Basique"))
	    	difficulty = StrategyPacman.BASIC;
		else if(listStrategyPacman.getSelectedItem().equals("Aléatoire"))
			difficulty = StrategyPacman.RANDOM;
		else if(listStrategyPacman.getSelectedItem().equals("Rien"))
			difficulty = StrategyPacman.NONE;
	    return difficulty;
	}
	
	public StrategyGhost getStrategyGhost() {
		StrategyGhost difficulty = StrategyGhost.ASTAR;
	    if(listStrategyGhost.getSelectedItem().equals("Pister (Normale)"))
	    	difficulty = StrategyGhost.TRACKING;
	    else if(listStrategyGhost.getSelectedItem().equals("Basique (Facile)"))
	    	difficulty = StrategyGhost.BASIC;
		else if(listStrategyGhost.getSelectedItem().equals("Aléatoire (Facile)"))
			difficulty = StrategyGhost.RANDOM;
		else if(listStrategyGhost.getSelectedItem().equals("Rien (Paisible)"))
			difficulty = StrategyGhost.NONE;
	    return difficulty;
	}
	
	public void centerView() {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2 ;
        setLocation(dx, dy);   
	}
	
}
