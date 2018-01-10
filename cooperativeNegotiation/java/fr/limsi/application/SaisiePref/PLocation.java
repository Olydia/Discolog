package fr.limsi.application.SaisiePref;


import java.awt.Dimension;


import javax.swing.*;

public class PLocation extends JPanel {
	
	Element lDowntown;
	Element lSouth;
	Element lNorth;
	Element lEast;
	Element lWest;


	/**
	 * Create the panel.
	 */
	public PLocation() {
		
		lDowntown = new Element("Downtown");
		lSouth = new Element("South");
		lNorth = new Element("North");
		lEast = new Element("Est");
		lWest = new Element("West");
		
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(boxlayout);
		
//		GridLayout flow = new GridLayout(1,5);
//		this.setLayout(flow);
		add(Box.createHorizontalGlue());

		this.add(lDowntown);
		//add(Box.createRigidArea(new Dimension(20,0)));
		add(Box.createHorizontalGlue());
		this.add(lNorth);
		add(Box.createHorizontalGlue());
		this.add(lWest);
		add(Box.createHorizontalGlue());
		this.add(lEast);
		add(Box.createHorizontalGlue());
		this.add(lSouth);
		add(Box.createHorizontalGlue());







		
	}

}
