package fr.univangers.pacman.view.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.model.beans.LoginInformation;

public class DialogLogin {
	private JTextField host = new JTextField("localhost");
	private JSpinner port = new JSpinner(new SpinnerNumberModel(4400, 0, 65535, 1));
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();
	private JPanel panel = new JPanel(new BorderLayout(5, 5));
	
	private DialogLogin() {
	    JLabel title = new JLabel("Connexion", SwingConstants.CENTER);
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2)); 
	    
	    label.add(new JLabel("Nom serveur", SwingConstants.RIGHT));
	    controls.add(host);
	    label.add(new JLabel("Port serveur", SwingConstants.RIGHT));
	    controls.add(port);
	    label.add(new JLabel("Nom d'utilisateur", SwingConstants.RIGHT));
	    controls.add(username);
	    label.add(new JLabel("Mots de passe", SwingConstants.RIGHT));
	    controls.add(password);
	    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
	    
	    panel.add(title, BorderLayout.NORTH);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(controls, BorderLayout.CENTER);
	}
	
	private LoginInformation getLoginInformation() {
		LoginInformation loginInformation = new LoginInformation();
		loginInformation.setHost(host.getText());
		loginInformation.setPort((int) port.getValue());
		loginInformation.setUsername(username.getText());
		loginInformation.setPassword(Arrays.toString(password.getPassword()));
		return loginInformation;
	}
	
	public static LoginInformation show() {
		DialogLogin dialogLogin = new DialogLogin();
		JOptionPane.showOptionDialog(null, dialogLogin.panel, "Pacman", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
				new String[] {"Valider"}, "Valider");
		return dialogLogin.getLoginInformation();
	}
	
}
