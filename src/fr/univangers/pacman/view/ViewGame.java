package fr.univangers.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.controller.GameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.model.game.Game;
import fr.univangers.pacman.model.gamestate.PacmanGameState;
import fr.univangers.pacman.model.gamestate.PacmanGameState.Winner;

/**
 *	Classe ViewGame permet d'afficher le jeu dans une interface graphique
 */

public class ViewGame implements View, KeyListener {

	private static final long serialVersionUID = -2187636929128362263L;
	private Game game;
	private GameController gameController;
	private JFrame frame;
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
		
		frame = new JFrame();
		frame.addKeyListener(this);
		
		frame.setTitle("Pacman");
        frame.setSize(new Dimension(maze.getSizeX() * 20, maze.getSizeY() * 20 + 80));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Dimension windowSize = frame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 + 150;
        frame.setLocation(dx, dy);   
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2,1));
        
        labelCurrentTurn = new JLabel("Tour " + game.getState().getNbTurn(), SwingConstants.CENTER);
        panelInfo.add(labelCurrentTurn);
		if(game.getState() instanceof PacmanGameState) {
			PacmanGameState state = (PacmanGameState) game.getState();
			labelLife = new JLabel("Vie " + state.getNbLifePacmans(), SwingConstants.CENTER);
			panelInfo.add(labelLife);
			labelScore = new JLabel("Score " + state.getScore(), SwingConstants.CENTER);
			panelInfo.add(labelScore);
		}
		frame.add(panelInfo, BorderLayout.NORTH);

		panelPacmanGame = new PanelPacmanGame(maze);
		frame.add(panelPacmanGame, BorderLayout.CENTER);
			
		frame.setVisible(true);
	}
	
	@Override
	public void update() {
        labelCurrentTurn.setText("Tour " + game.getState().getNbTurn());
        
		if(game.getState() instanceof PacmanGameState) {
			PacmanGameState state = (PacmanGameState) game.getState();
			labelLife.setText("Vie " + state.getNbLifePacmans());
			labelScore.setText("Score " + state.getScore());
	        panelPacmanGame.setGhostsScarred(state.getGhostsScarred());
	        panelPacmanGame.setPacmansPos(state.getPositionPacmans());
	        panelPacmanGame.setGhostsPos(state.getPositionGhosts());
	        panelPacmanGame.setFoodsPos(state.getPositionFoods());
	        panelPacmanGame.setCapsulesPos(state.getPositionCapsules());
	        
	        if(!state.getCurrentSong().isEmpty())
	        	playSound(state.getCurrentSong());
	       
	        Winner winner = state.getWinner();
	        if(!reset && winner != Winner.NOWINNER) {
	        	String print = "";
		        if(winner == Winner.PACMANWINNER) {
		        	print = "Les pacmans ont gagné";
		        }
		        else if(winner == Winner.GHOSTWINNER) {
		        	print = "Les fantômes ont gagné";
		        }	  
	        	int answer = JOptionPane.showConfirmDialog(frame,
	        			print + "\nRelancer ?", "Fin de partie", JOptionPane.YES_NO_OPTION); 
	        	if(answer == JOptionPane.YES_OPTION) {
	        		reset = true;
	        		gameController.restart();
	        	} else {
	        		System.exit(JFrame.NORMAL);
	        	}	
	        }
	        else 
	        	reset = false;
		}
        panelPacmanGame.repaint();
	}
	
	private void playSound(String filename) {
		try {
	        AudioInputStream audioIn;
			audioIn = AudioSystem.getAudioInputStream(new File(filename));
	        Clip clip = AudioSystem.getClip();
	        clip.stop();
	        clip.setFramePosition(0);
	        clip.open(audioIn);
	        clip.start();
		} catch (Exception e) {
			// Cas où il n'y a pas de sons
		}
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
		/* NOT USE */
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		/* NOT USE */
	}

}
