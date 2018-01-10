package fr.limsi.application.SaisiePref;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Element extends JPanel {
	private JLabel elem;
	private JTextField classement;
	
	public Element( String name) {
		//this.setBackground(Color.CYAN);

		elem = new JLabel(name);
		elem.setHorizontalAlignment(JLabel.CENTER);
		elem.setFont(new Font("Serif", Font.BOLD, 17));
		classement = new JTextField();
		GridLayout g  = new GridLayout(2, 1);
		this.setLayout(g);
		this.add(elem);
		this.add(classement);
	}

}
