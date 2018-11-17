package fr.univangers.pacman.view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.controller.GameController;
import fr.univangers.pacman.model.Game;

public class ViewEndGame extends JFrame implements View{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3788218648735610801L;
	private Game game;
	private GameController gameController;
	
	private JPanel panelBtn;
	private JButton btnRestart;
	private JButton btnStop;
	
	private JPanel panelInfo;
	

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
	public ViewEndGame(Game game, String StateOfEnd) {
		super();
		this.game = game;
		this.game.addView(this);
		
        setTitle("Fin de la partie");
        setSize(new Dimension(700, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,1));

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2 ;
        int dy = centerPoint.y - windowSize.height / 2 + 150;
        setLocation(dx, dy);   
        
        panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout(2,2));
        btnRestart = new JButton(new ImageIcon("res/icons/icon_restart.png"));
        btnRestart.addActionListener(evenement -> { 
        	gameController.restart();
        	btnRestart.setEnabled(true);
        });
        btnRestart.setEnabled(true);
        JLabel labelRestart = new JLabel("Relancer le jeu :", SwingConstants.CENTER);
        
        btnStop = new JButton(new ImageIcon("res/icons/icon_stop.png"));
        btnStop.addActionListener(evenement -> { 
        	gameController.restart();
        	btnStop.setEnabled(true);
        });
        btnStop.setEnabled(true);
        JLabel labelStop = new JLabel("Arrêter le jeu :", SwingConstants.CENTER);

       
        panelBtn.add(labelRestart);
        panelBtn.add(labelStop);
        panelBtn.add(btnRestart);
        panelBtn.add(btnStop);

     
        add(panelBtn);
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(1,1));
        JLabel labelTime = new JLabel(StateOfEnd, SwingConstants.CENTER);
        panelInfo.add(labelTime);
       
        add(panelInfo);
        
		setVisible(true);
	}
	
	
	@Override
	public void update() { //Ne sert à rien

	}
}
