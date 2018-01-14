package fr.limsi.application.SaisiePref.dndTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.*;


public class Acceuil extends JFrame{
	JLabel welcom  = new JLabel("text d'acceuil");
	JButton commencer = new JButton("Commencer");
	
	CriteriaSelect cuisine;
	CriteriaSelect cost;
	CriteriaSelect athmos;
	CriteriaSelect location;

	
	
	public Acceuil(){
		
		this.getContentPane().add(welcom, BorderLayout.NORTH);
		this.getContentPane().add(commencer, BorderLayout.CENTER);
		
		cost = new CriteriaSelect(Cost.class);
		cuisine = new CriteriaSelect(Cuisine.class);
		athmos = new CriteriaSelect(Atmosphere.class);
		location = new CriteriaSelect(Location.class);
		
		// Init the model of preferences
		PreferencesModel model = new PreferencesModel();
		
		
		// Affichage des fenetres
		commencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				cuisine.setVisible(true);
			}
				
			});
		
		cuisine.confim.addActionListener(
	            new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cuisine.getValues()!= null){
							//Get the selected rank of preferences
							model.d1_cuisine.createPreferences(cuisine.getValues());
							//next frame
							cuisine.setVisible(false);
							cost.setVisible(true);
						}
							
					}
	            }
	        );
		
		cost.confim.addActionListener(
	            new ActionListener() {
	         
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cost.getValues()!= null){
							//Get the selected rank of preferences
							model.d1_cost.createPreferences(cost.getValues());
							//next frame
							cost.setVisible(false);
							athmos.setVisible(true);
						}
							
					}
	            }
	        );
		
		athmos.confim.addActionListener(
	            new ActionListener() {
	         
					@Override
					public void actionPerformed(ActionEvent e) {
						if(athmos.getValues()!= null){
							//Get the selected rank of preferences
							model.d1_atmosphere.createPreferences(athmos.getValues());
							//next frame
							athmos.setVisible(false);
							location.setVisible(true);
						}
							
					}
	            }
	        );
		
		location.confim.addActionListener(
	            new ActionListener() {
	         
					@Override
					public void actionPerformed(ActionEvent e) {
						if(location.getValues()!= null){
							//Get the selected rank of preferences
							model.d1_location.createPreferences(location.getValues());
							//next frame
							location.setVisible(false);
							
							// Creer le modele de prefs
							Negotiation<Restaurant> nego = model.createModel();
						}
							
					}
	            }
	        );
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Acceuil frame = new Acceuil();
	        frame.setMinimumSize(new Dimension(200, 200));

			frame.pack();
			
			frame.setVisible(true);
		});
	}

}
