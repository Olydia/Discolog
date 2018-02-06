package fr.limsi.application.SaisiePref.dndTest;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import fr.limsi.negotiate.Criterion;

public class CriteriaSelect extends JDialog {

	private JLabel newTextField = new JLabel("Entrez vos préférences");
	private JList<String> toBeRanked = new JList<>(new DefaultListModel<>());
	private JList<String> ranked = new JList<>(new DefaultListModel<>());
	protected JButton confim;
	private Class<? extends Criterion> type;

	public JList<String> getSourceList() {
		return toBeRanked;
	}

	public JList<String> getDestList() {
		return ranked;
	}

	public CriteriaSelect(Class<? extends Criterion> type) {
		this.confim = new JButton("Confirm");
		this.type = type;  
		Criterion[] elements = type.getEnumConstants();
		// Remplir la liste des valeurs
		for (int i = 0; i < elements.length; i++) {
			((DefaultListModel<String>) toBeRanked.getModel()).add(i, elements[i].toString());
			// ((DefaultListModel<String>) destList.getModel()).add(i, "B " + i);
		}

		//confim.addActionListener(new confirmAction(this));
	
		
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(newTextField);

		Box sourceBox = Box.createVerticalBox();
		sourceBox.add(new JLabel("Critères restant à classer"));
		sourceBox.add(new JScrollPane(toBeRanked));

		Box destBox = Box.createVerticalBox();
		destBox.add(new JLabel("Votre classement"));
		destBox.add(new JScrollPane(ranked));

		Box listBox = Box.createHorizontalBox();
		listBox.add(sourceBox);
		listBox.add(destBox);

		Box allBox = Box.createVerticalBox();
		allBox.add(nameBox);
		allBox.add(listBox);

		this.getContentPane().add(allBox, BorderLayout.CENTER);
		this.getContentPane().add(confim, BorderLayout.SOUTH);

		toBeRanked.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ranked.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		toBeRanked.setDragEnabled(true);
		ranked.setDragEnabled(true);

		toBeRanked.setDropMode(DropMode.INSERT);
		ranked.setDropMode(DropMode.INSERT);

		ListTransferHandler h = new ListTransferHandler(toBeRanked);
		toBeRanked.setTransferHandler(h);
		ranked.setTransferHandler(h);
        this.setMinimumSize(new Dimension(300, 350));

		this.pack();

	}
	
	public ListModel<String> getValues(){
		if(this.getSourceList().getModel().getSize()!=0){
			JOptionPane.showMessageDialog(this, "Merci de classer toutes les valeurs", "Message",
			        JOptionPane.WARNING_MESSAGE);
		}

			
		else{
			System.out.println(this.getDestList().getModel());
			return this.getDestList().getModel();
			// Ecrire les criteres dans l'ensemble static
		}
		return null;
	}

	

}




