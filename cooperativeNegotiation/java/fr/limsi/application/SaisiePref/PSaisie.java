package fr.limsi.application.SaisiePref;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PSaisie extends JPanel {
	
	private JLabel welcome;
	private CriteriaSaisie criteria;
	private Confirm buttons ;
	
	
	public PSaisie() {
		//this.setBackground(Color.red);

		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		
		criteria = new CriteriaSaisie();
		this.add(criteria, BorderLayout.CENTER);
		
		welcome = new JLabel("Veuillez ordonner par order croissant les valeurs selon vos préférences");
		welcome.setHorizontalAlignment(JLabel.CENTER);
		add(welcome, BorderLayout.NORTH);
		
		buttons = new Confirm();
		add(buttons, BorderLayout.SOUTH);
	}

	
		//public Map
}
