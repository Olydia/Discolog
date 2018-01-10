package fr.limsi.application.SaisiePref;


import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

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






		
	}

}
