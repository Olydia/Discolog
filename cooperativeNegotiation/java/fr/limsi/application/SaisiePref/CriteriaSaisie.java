package fr.limsi.application.SaisiePref;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

	


@SuppressWarnings("serial")
public class CriteriaSaisie extends JPanel {
	
	private PCuisine p;
	private PCost cost;
	private PLocation location;
	PAthmosphere athm;
	
	public CriteriaSaisie() {
		//this.setBackground(Color.black);
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		this.setSize(screenSize);
		//GridLayout g = new GridLayout(4, 1);
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxlayout);
		add(Box.createVerticalGlue());

		p = new PCuisine();
		this.add(p);
		add(Box.createVerticalGlue());
		
		cost = new PCost();
		this.add(cost);
		add(Box.createVerticalGlue());
		
		 location = new PLocation();
		this.add(location);
		add(Box.createVerticalGlue());
		
		 athm = new PAthmosphere();
		this.add(athm);
		add(Box.createVerticalGlue());
	}

}
