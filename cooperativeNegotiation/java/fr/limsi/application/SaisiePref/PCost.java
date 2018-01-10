package fr.limsi.application.SaisiePref;


import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class PCost extends JPanel {
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

	
	Element lExpensive;
	Element lCheap;
	Element lAffordable;


	/**
	 * Create the panel.
	 */
	public PCost() {
		lExpensive = new Element("Expensive");
		lCheap = new Element("Cheap");
		lAffordable = new Element("Affordable");
		
		BoxLayout boxlayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(boxlayout);
		
//		GridLayout flow = new GridLayout(1,3);
//		this.setLayout(flow);
		add(Box.createHorizontalGlue());
		this.add(lExpensive);
		
		add(Box.createHorizontalGlue());
		this.add(lCheap);
		
		add(Box.createHorizontalGlue());
		this.add(lAffordable);
		
		add(Box.createHorizontalGlue());






		
	}

}
