package fr.univangers.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.model.Game;

public class ViewGame extends JFrame implements View {

	private static final long serialVersionUID = -2187636929128362263L;
	private Game game;
	private JLabel labelCurrentTurn;
	
	public ViewGame(Game game) {
		super();
		this.game = game;
		this.game.addView(this);
		
        setTitle("Game");
        setSize(new Dimension(700, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        labelCurrentTurn = new JLabel("current turn " + game.nbTurn(), SwingConstants.CENTER);
		add(labelCurrentTurn, BorderLayout.NORTH);
        try {
			add(new PanelPacmanGame(new Maze("res/layouts/originalClassic.lay")), BorderLayout.CENTER);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
        
        setVisible(true);
	}
	
	@Override
	public void update() {
        labelCurrentTurn.setText("current turn " + game.nbTurn());
	}

}
