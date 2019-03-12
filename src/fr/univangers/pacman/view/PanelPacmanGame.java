package fr.univangers.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.Position;
import fr.univangers.pacman.model.PositionAgent;

/**
 * Classe PanelPacmanGame, utilisé dans ViewGame, permet de colorier
 * la carte ainsi que les éléments du jeu
 */

public class PanelPacmanGame extends JPanel{
	
	private static final long serialVersionUID = 6308859631855223659L;
	
	private Color wallColor=Color.BLUE;
	private Color wallColor2=Color.CYAN;

	private double sizePacman=1.1;	
	private Color pacmansColor = Color.yellow;
	
	private Color ghostsColor = Color.white;
	private Color ghostScarredColor=Color.pink;

	private double sizeFood=0.3; 
	private Color colorFood=Color.white; 

	private double sizeCapsule=0.7;
	private Color colorCapsule=Color.red;

	Maze m;
	
	private List<PositionAgent> pacmansPos;
	private List<Position> ghostsPos;
	private List<Position> foodsPos;
	private List<Position> capsulesPos;
	
	private List<Boolean> ghostsScarred;
	

	public PanelPacmanGame(Maze maze) {
		this.m = maze;
		pacmansPos = this.m.getPacmanStart();
		ghostsPos = this.m.getGhostsStart();
		foodsPos =  this.m.getFoodsStart();
		capsulesPos =  this.m.getCapsulesStart();
		ghostsScarred = new ArrayList<>(Collections.nCopies(ghostsPos.size(), false));		
	}

	@Override
	public void paint(Graphics g){
		
		int dx=getSize().width;
		int dy=getSize().height;
		g.setColor(Color.black);
		g.fillRect(0, 0,dx,dy);

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;
		double posx=0;
		
		for(int x=0;x<sx;x++)
		{
			double posy=0;
			for(int y=0;y<sy;y++)
			{
				if (m.isWall(x, y))
				{
					g.setColor(wallColor2);
					g.fillRect((int)posx, (int)posy, (int)(stepx+1),(int)(stepy+1));
					g.setColor(wallColor);
					double nsx=stepx*0.5;
					double nsy=stepy*0.5;
					double npx=(stepx-nsx)/2.0;
					double npy=(stepy-nsy)/2.0;
					g.fillRect((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);						
				}
				posy+=stepy;				
			}
			posx+=stepx;
		}
		
		for(Position pos : capsulesPos) {
			drawCapsules(g, pos.getX(), pos.getY(), colorCapsule);	
		}
		
		for(Position pos : foodsPos) {
			drawFoods(g, pos.getX(), pos.getY(), colorFood);	
		}

		for(PositionAgent pos : pacmansPos) {
			drawPacmans(g, pos.getX(), pos.getY(), pos.getDir(), pacmansColor);	
		}

		for(int i = 0; i < ghostsPos.size(); i++) {
			Position pos = ghostsPos.get(i);
			if(ghostsScarred.get(i)) {
				drawGhosts(g, pos.getX(), pos.getY(), ghostScarredColor);	
			} else {
				drawGhosts(g, pos.getX(), pos.getY(), ghostsColor);	
			}
		}
		
	}
	
	void drawCapsules(Graphics g, int px, int py, Color color) {
		int dx = getSize().width;
		int dy = getSize().height;
		

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;
		
		g.setColor(color);
		double nsx=stepx*sizeCapsule;
		double nsy=stepy*sizeCapsule;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;
		
		g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
	}
	
	void drawFoods(Graphics g, int px, int py, Color color) {
		int dx = getSize().width;
		int dy = getSize().height;
		

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;
		
		g.setColor(color);
		double nsx=stepx*sizeFood;
		double nsy=stepy*sizeFood;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;
		
		g.fillOval((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy);
	}

	void drawPacmans(Graphics g, int px, int py, PositionAgent.Dir pacmanDirection, Color color) {
		int dx = getSize().width;
		int dy = getSize().height;

		int sx = m.getSizeX();
		int sy = m.getSizeY();
		double stepx = dx/(double)sx;
		double stepy = dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;

		g.setColor(color);
		double nsx=stepx*sizePacman;
		double nsy=stepy*sizePacman;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;
		int sa=0;
		int fa=0;
		
		if (pacmanDirection==PositionAgent.Dir.NORTH) {
			sa=70; fa=-320;
		}
		if (pacmanDirection==PositionAgent.Dir.SOUTH) {
			sa=250; fa=-320;
		}
		if (pacmanDirection==PositionAgent.Dir.EAST) {
			sa=340; fa=-320;				
		}
		if (pacmanDirection==PositionAgent.Dir.WEST) {
			sa=160; fa=-320;
		}

		g.fillArc((int)(npx+posx),(int)(npy+posy),(int)(nsx),(int)nsy,sa,fa);
	}

	
	void drawGhosts(Graphics g, int px, int py, Color color) {
		int dx=getSize().width;
		int dy=getSize().height;

		int sx=m.getSizeX();
		int sy=m.getSizeY();
		double stepx=dx/(double)sx;
		double stepy=dy/(double)sy;

		double posx=px*stepx;
		double posy=py*stepy;

		g.setColor(color);

		double nsx=stepx*sizePacman;
		double nsy=stepy*sizePacman;
		double npx=(stepx-nsx)/2.0;
		double npy=(stepy-nsy)/2.0;

		g.fillArc((int)(posx+npx),(int)(npy+posy),(int)(nsx),(int)(nsy),0,180);
		g.fillRect((int)(posx+npx),(int)(npy+posy+nsy/2.0-1),(int)(nsx),(int)(nsy*0.666));
		g.setColor(Color.BLACK);
		g.fillOval((int)(posx+npx+nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);
		g.fillOval((int)(posx+npx+3*nsx/5.0),(int)(npy+posy+nsy/3.0),4,4);

		g.setColor(Color.black);
	}



	public void setGhostsScarred(List<Boolean> ghostsScarred) {
		this.ghostsScarred = ghostsScarred;
	}
	

	public List<PositionAgent> getPacmansPos() {
		return pacmansPos;
	}

	public void setPacmansPos(List<PositionAgent> pacmansPos) {
		this.pacmansPos = pacmansPos;
	}

	public List<Position> getGhostsPos() {
		return ghostsPos;
	}

	public void setGhostsPos(List<Position> ghostsPos) {
		this.ghostsPos = ghostsPos;
	}

	public List<Position> getFoodsPos() {
		return foodsPos;
	}

	public void setFoodsPos(List<Position> foodsPos) {
		this.foodsPos = foodsPos;
	}

	public List<Position> getCapsulesPos() {
		return capsulesPos;
	}

	public void setCapsulesPos(List<Position> capsulesPos) {
		this.capsulesPos = capsulesPos;
	}

}
