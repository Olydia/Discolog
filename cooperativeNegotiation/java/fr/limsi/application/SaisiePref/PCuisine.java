package fr.limsi.application.SaisiePref;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class PCuisine extends JPanel {
//	JLabel lChinese;
//	JTextField tChinese;
//	
//	JLabel lFrench;
//	JTextField tFrench;
//	
//	JLabel lItalian;
//	JTextField tItalian;
//	
//	JLabel lJapanese;
//	JTextField tJapanese;
//	
//	JLabel lTurkish;
//	JTextField tTurkish;
//	
//	JLabel lMexican;
//	JTextField tMexican;
//	
//	JLabel lCorean;
//	JTextField tCorean;

	
	Element lChinese;
	Element lFrench;
	Element lItalian;
	Element lJapanese;
	Element lTurkish;
	Element lMexican;
	Element lCorean;

	/**
	 * Create the panel.
	 */
	public PCuisine() {

		lChinese = new Element("Chinese");
		lFrench = new Element("French");
		lItalian = new Element("Italian");
		lJapanese = new Element("Japanese");
		lTurkish = new Element("Turkish");
		lMexican = new Element("Mexican");
		lCorean = new Element("Korean");
		
//		tChinese = new JTextField();
//		tCorean =  new JTextField();
//		tFrench = new JTextField();
//		tItalian = new JTextField();
//		tJapanese = new JTextField();
//		tMexican = new JTextField();
//		tTurkish = new JTextField();

		//FlowLayout flow = new FlowLayout();
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(boxlayout);

		add(Box.createHorizontalGlue());
		this.add(lChinese);
		
		add(Box.createHorizontalGlue());
		this.add(lFrench);
		
		add(Box.createHorizontalGlue());

		this.add(lCorean);
		
		add(Box.createHorizontalGlue());

		this.add(lItalian);
		
		add(Box.createHorizontalGlue());

		this.add(lJapanese);
		
		add(Box.createHorizontalGlue());

		this.add(lMexican);
		
		add(Box.createHorizontalGlue());

		this.add(lTurkish);
		
		add(Box.createHorizontalGlue());






		
	}

}
