package fr.univangers.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.controller.GameController;
import fr.univangers.pacman.model.Game;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameGetter;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 *	Classe ViewGame permet d'afficher le jeu dans une interface graphique
 */

public class ViewGame extends JFrame implements View, KeyListener {

	private static final long serialVersionUID = -2187636929128362263L;
	private Game game;
	private GameController gameController;
	private JPanel panelInfo;
	private JLabel labelCurrentTurn;
	private JLabel labelScore;
	private JLabel labelLife;
	private PanelPacmanGame panelPacmanGame;
	private boolean reset;
	
	public ViewGame(Game game, GameController gameController, Maze maze) {
		super();
		this.game = game;
		this.game.addView(this);
		this.gameController = gameController;
		this.reset = false;
		
		addKeyListener(this);
		
        setTitle("Pacman");
        setSize(new Dimension(maze.getSizeX() * 20, maze.getSizeY() * 20 + 80));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 + 150;
        setLocation(dx, dy);   
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2,1));
        
        labelCurrentTurn = new JLabel("Tour " + game.nbTurn(), SwingConstants.CENTER);
        panelInfo.add(labelCurrentTurn);
		if(game instanceof PacmanGameGetter) {
			labelLife = new JLabel("Vie " + ((PacmanGameGetter)game).getNbLifePacmans(), SwingConstants.CENTER);
			panelInfo.add(labelLife);
			labelScore = new JLabel("Score " + ((PacmanGameGetter)game).getScore(), SwingConstants.CENTER);
			panelInfo.add(labelScore);
		}
		add(panelInfo, BorderLayout.NORTH);

		panelPacmanGame = new PanelPacmanGame(maze);
		add(panelPacmanGame, BorderLayout.CENTER);
			
        setVisible(true);
	}
	
	@Override
	public void update() {
        labelCurrentTurn.setText("Tour " + game.nbTurn());
		if(game instanceof PacmanGameGetter) {
			labelLife.setText("Vie " + ((PacmanGameGetter)game).getNbLifePacmans());
			labelScore.setText("Score " + ((PacmanGameGetter)game).getScore());
	        panelPacmanGame.setGhostsScarred(((PacmanGameGetter)game).getGhostsScarred());
	        panelPacmanGame.setPacmans_pos(((PacmanGameGetter)game).getPositionPacmans());
	        panelPacmanGame.setGhosts_pos(((PacmanGameGetter)game).getPositionGhosts());
	        
	        PacmanGame.Winner winner = ((PacmanGameGetter)game).getWinner();
	        if(!reset && winner != PacmanGame.Winner.NOWINNER) {
	        	String print = "";
		        if(winner == PacmanGame.Winner.PACMANWINNER) {
		        	print = "Les pacmans ont gagné";
		        }
		        else if(winner == PacmanGame.Winner.GHOSTWINNER) {
		        	print = "Les fantômes ont gagné";
		        }	  
	        	int answer = JOptionPane.showConfirmDialog(this,
	        			print + "\nRelancer ?", "Fin de partie", JOptionPane.YES_NO_OPTION); 
	        	if(answer == JOptionPane.YES_OPTION) {
	        		reset = true;
	        		gameController.restart();
	        	} else {
	        		System.exit(NORMAL);
	        	}	
	        }
	        else 
	        	reset = false;
		}
        panelPacmanGame.repaint();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        switch(code) {
        case KeyEvent.VK_LEFT:
            gameController.movePlayer1(Dir.WEST);
        	break;
        case KeyEvent.VK_UP:
            gameController.movePlayer1(Dir.NORTH);
        	break;
        case KeyEvent.VK_DOWN:
            gameController.movePlayer1(Dir.SOUTH);
        	break;
        case KeyEvent.VK_RIGHT:
            gameController.movePlayer1(Dir.EAST);
        	break;
        case KeyEvent.VK_Q:
            gameController.movePlayer2(Dir.WEST);
        	break;
        case KeyEvent.VK_Z:
            gameController.movePlayer2(Dir.NORTH);
        	break;
        case KeyEvent.VK_S:
            gameController.movePlayer2(Dir.SOUTH);
        	break;
        case KeyEvent.VK_D:
            gameController.movePlayer2(Dir.EAST);
        	break;
        case KeyEvent.VK_P:
        	gameController.pause();
        	break;
        default:
        	break;
        }
        
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
