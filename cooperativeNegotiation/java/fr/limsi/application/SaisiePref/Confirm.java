package fr.limsi.application.SaisiePref;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Confirm extends JPanel {

	private JButton confirm;
	private JButton reset;
	
	
	public Confirm() {
		this.setBackground(Color.blue);

		confirm = new JButton("Confirm");
		reset = new JButton("Reset");
		this.setLayout(new FlowLayout());
		this.add(confirm);
		this.add(reset)
;	}

}
