package fr.limsi.application.SaisiePref.dndTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.ToM.preferencesGeneration.Models;
import fr.limsi.negotiate.restaurant.*;

// il faut envoyer les prefs de l'agent � l'agent
// 

public class Acceuil extends JFrame{
	
	JLabel textAcceuil = new JLabel("Bienvenu");
	String text = "Nous vous invitons à saisir vos préférences sur chaque critère \n"
			+ "en les classant par ordre croissant \n"
			+ " de la valeur que vous appréciez le moins \n"
			+ "a la valeur que vous appréciez le plus";
			
	JTextArea welcom  = new JTextArea(text);
	JButton commencer = new JButton("Commencer");
	
	CriteriaSelect cuisine;
	CriteriaSelect cost;
	CriteriaSelect athmos;
	CriteriaSelect location;

	
	
	public Acceuil(){
		Font font = new Font("Arial", Font.BOLD, 20);
		welcom.setFont(font);
		welcom.setEditable(false);
		
		this.getContentPane().add(textAcceuil, BorderLayout.NORTH);
		this.getContentPane().add(welcom, BorderLayout.CENTER);
		this.getContentPane().add(commencer, BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.white);
		
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
							Models<Restaurant> other = new Models<Restaurant>();
							Negotiation<Restaurant> agent = other.createOther(Restaurant.A_CITADELLA.getCriteria(),
									Restaurant.class, nego.getSelfs());
							
							for(Self_Ci<Criterion> c : agent.getSelfs())
								System.out.println(c.getSelfPreferences());
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
