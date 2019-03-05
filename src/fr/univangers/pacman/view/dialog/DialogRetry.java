package fr.univangers.pacman.view.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DialogRetry {
	private JPanel panel = new JPanel(new BorderLayout(5, 5));
	
	private DialogRetry(String message) {
	    JLabel title = new JLabel("Connexion", SwingConstants.CENTER);
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    
	    label.add(new JLabel(message, SwingConstants.CENTER));
	    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
	    
	    panel.add(title, BorderLayout.NORTH);
	    panel.add(label, BorderLayout.CENTER);
	}
	
	public static boolean show(String message) {
		DialogRetry dialogRetry = new DialogRetry(message);
		int result = JOptionPane.showConfirmDialog(null, dialogRetry.panel, "Pacman", 
				JOptionPane.YES_NO_OPTION,
				JOptionPane.ERROR_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}
}
