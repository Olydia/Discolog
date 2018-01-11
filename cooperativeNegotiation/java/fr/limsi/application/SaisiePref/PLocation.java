package fr.limsi.application.SaisiePref;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

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
		TitledBorder border = new TitledBorder(new LineBorder(Color.black),"Location");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(new Font("Ariel", Font.BOLD, 20));
		this.setBorder(border);

		
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
