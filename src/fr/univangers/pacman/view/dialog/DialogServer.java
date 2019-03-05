package fr.univangers.pacman.view.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import fr.univangers.pacman.model.beans.ServerInformation;

public class DialogServer {
	private JSpinner port = new JSpinner(new SpinnerNumberModel(4400, 0, 65535, 1));
	private JCheckBox needAuthentication = new JCheckBox();
	private JPanel panel = new JPanel(new BorderLayout(5, 5));

	private DialogServer() {
	    JLabel title = new JLabel("Paramêtre du serveur", SwingConstants.CENTER);
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2)); 
	    
	    label.add(new JLabel("Port", SwingConstants.RIGHT));
	    controls.add(port);
	    label.add(new JLabel("Authentification", SwingConstants.RIGHT));
	    controls.add(needAuthentication);
	    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

	    panel.add(title, BorderLayout.NORTH);
	    panel.add(label, BorderLayout.WEST);
	    panel.add(controls, BorderLayout.CENTER);
	}
	
	private ServerInformation getServerInformation() {
		ServerInformation serverInformation = new ServerInformation();
		serverInformation.setPort((int) port.getValue());
		serverInformation.setNeedAuthentication(needAuthentication.isSelected());
		return serverInformation;
	}
	
	public static ServerInformation show() {
		DialogServer dialogServer = new DialogServer();
		JOptionPane.showOptionDialog(null, dialogServer.panel, "Serveur", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
				new String[] {"Valider"}, "Valider");
		return dialogServer.getServerInformation();
	}
	
}
