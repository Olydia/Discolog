package fr.limsi.application.SaisiePref;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PAthmosphere extends JPanel {

	Element lCozy;
	Element lFamily;
	Element lLively;
	Element lModern;
	Element lQuiet;
	Element lRomantic;

	/**
	 * Create the panel.
	 */
	public PAthmosphere() {
		
	

		
		lCozy = new Element("Cozy");
		lFamily = new Element("Family");
		lLively = new Element("Lively");
		lModern = new Element("Modern");
		lQuiet = new Element("Quiet");
		lRomantic = new Element("Romantic");
		
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(boxlayout);
		 
//		GridLayout flow = new GridLayout(1,6);
//		this.setLayout(flow);
//				add(Box.createHorizontalGlue());
		add(Box.createHorizontalGlue());

		this.add(lCozy);
		add(Box.createHorizontalGlue());

		this.add(lFamily);
		add(Box.createHorizontalGlue());

		this.add(lLively);
		add(Box.createHorizontalGlue());

		this.add(lModern);
		add(Box.createHorizontalGlue());

		this.add(lRomantic);
		add(Box.createHorizontalGlue());

		this.add(lQuiet);
		add(Box.createHorizontalGlue());
		
		add(Box.createVerticalGlue());

		TitledBorder border = new TitledBorder(new LineBorder(Color.black),"Athmosphere");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(new Font("Ariel", Font.BOLD, 20));
		this.setBorder(border);




		
	}

}
