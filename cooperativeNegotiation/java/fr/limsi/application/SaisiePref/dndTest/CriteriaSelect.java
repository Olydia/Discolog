package fr.limsi.application.SaisiePref.dndTest;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.restaurant.Cuisine;

public class CriteriaSelect extends JFrame {

	private JLabel newTextField = new JLabel("Entrez vos préférences");
	private JList<String> sourceList = new JList<>(new DefaultListModel<>());
	private JList<String> destList = new JList<>(new DefaultListModel<>());
	protected JButton confim;
	private Class<? extends Criterion> type;

	public JList<String> getSourceList() {
		return sourceList;
	}

	public JList<String> getDestList() {
		return destList;
	}

	public CriteriaSelect(Class<? extends Criterion> type) {
		this.confim = new JButton("Confirm");
		this.type = type;  
		Criterion[] elements = type.getEnumConstants();
		// Remplir la liste des valeurs
		for (int i = 0; i < elements.length; i++) {
			((DefaultListModel<String>) sourceList.getModel()).add(i, elements[i].toString());
			// ((DefaultListModel<String>) destList.getModel()).add(i, "B " + i);
		}

		//confim.addActionListener(new confirmAction(this));
	
		
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(newTextField);

		Box sourceBox = Box.createVerticalBox();
		sourceBox.add(new JLabel("Source"));
		sourceBox.add(new JScrollPane(sourceList));

		Box destBox = Box.createVerticalBox();
		destBox.add(new JLabel("Destination"));
		destBox.add(new JScrollPane(destList));

		Box listBox = Box.createHorizontalBox();
		listBox.add(sourceBox);
		listBox.add(destBox);

		Box allBox = Box.createVerticalBox();
		allBox.add(nameBox);
		allBox.add(listBox);

		this.getContentPane().add(allBox, BorderLayout.CENTER);
		this.getContentPane().add(confim, BorderLayout.SOUTH);

		sourceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		destList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		sourceList.setDragEnabled(true);
		destList.setDragEnabled(true);

		sourceList.setDropMode(DropMode.INSERT);
		destList.setDropMode(DropMode.INSERT);

		sourceList.setTransferHandler(new ListTransferHandler());
		destList.setTransferHandler(new ListTransferHandler());
		this.pack();

	}
	
	public ListModel<String> getValues(){
		if(this.getSourceList().getModel().getSize()!=0)
			System.out.println("Remplir tout les critères");
		else{
			System.out.println(this.getDestList().getModel());
			return this.getDestList().getModel();
			// Ecrire les criteres dans l'ensemble static
		}
		return null;
	}

	

}




