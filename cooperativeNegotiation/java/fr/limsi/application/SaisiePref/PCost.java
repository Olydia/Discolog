package fr.limsi.application.SaisiePref;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

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
		TitledBorder border = new TitledBorder(new LineBorder(Color.black),"Cost");
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.TOP);
		border.setTitleFont(new Font("Ariel", Font.BOLD, 20));
		this.setBorder(border);

		
		lExpensive = new Element("Expensive".toUpperCase());
		lCheap = new Element("Cheap".toUpperCase());
		lAffordable = new Element("Affordable".toUpperCase());

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
