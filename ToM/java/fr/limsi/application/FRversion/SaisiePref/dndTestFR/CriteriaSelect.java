package fr.limsi.application.FRversion.SaisiePref.dndTestFR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.AbstractList;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import fr.limsi.negotiate.Criterion;

public class CriteriaSelect extends JDialog {

	private JLabel welcomText = new JLabel("All other things being equal,"
											+ "sort the following values in ascending "
											+ "order of your preferences");
	
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
	    EmptyBorder border = new EmptyBorder(10, 20, 10, 20);
	    
	    toBeRanked.setFont(new Font("Arial", Font.PLAIN, 16));
	    ranked.setFont(new Font("Arial", Font.PLAIN, 16));

	    
		Font font = new Font("Arial",Font.BOLD,14);
		welcomText.setFont(font);
		welcomText.setBorder(border);
		welcomText.setAlignmentX(Component.CENTER_ALIGNMENT);
		//welcomText.setBorder(new LineBorder(Color.BLACK));
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
		nameBox.add(welcomText);

		Box sourceBox = Box.createVerticalBox();
		JLabel toRank = new JLabel("Criteria still to be classified");
		toRank.setAlignmentX(Component.CENTER_ALIGNMENT);
		toRank.setBorder(border);
		sourceBox.add(toRank);
		
		sourceBox.add(new JScrollPane(toBeRanked));

		Box destBox = Box.createVerticalBox();
		JLabel rankedLabel = new JLabel("Your ranking");
		rankedLabel.setBorder(border);
		rankedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		destBox.add(rankedLabel);
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
			JOptionPane.showMessageDialog(this, "Please, rank all the values before confirm", "Message",
			        JOptionPane.WARNING_MESSAGE);
		}

			
		else{
			//System.out.println(this.getDestList().getModel());
			return this.getDestList().getModel();
			// Ecrire les criteres dans l'ensemble static
		}
		return null;
	}

	public  List<String> asList() {
		final ListModel<String> model = this.getValues();
	     return new AbstractList<String>() {
	          @Override public String get(int index) {
	              return        model.getElementAt(index);
	          }

			@Override
			public int size() {
				// TODO Auto-generated method stub
				return model.getSize();
			}
	     };
	 }

}




