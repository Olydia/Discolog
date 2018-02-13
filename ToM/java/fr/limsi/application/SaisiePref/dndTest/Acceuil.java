package fr.limsi.application.SaisiePref.dndTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.ToM.preferencesGeneration.Models;
import fr.limsi.negotiate.restaurant.*;

// il faut envoyer les prefs de l'agent � l'agent
// 

public class Acceuil extends JDialog{
	
	public static List<Negotiation<? extends Option>> negotiators;
	private Negotiation<? extends Option> userPref;

	//private JLabel textAcceuil = new JLabel("Bienvenu");
	private String text = "For the purposes of this study, we ask you to enter your preferences for each criterion"
			+ "\n that will be discussed during the negotiation. \n\n"+
			
			"Since the criteria are independent, each criterion will be presented independently of the others."
			+ "\n You have to focus your rank only on the criterion presented and ignore the other criteria. \n\n \n"
			
			+"We invite you to rank your preferences in an descending order, "
			+ "\n starting with the value you like the most,  to the value you like the least. \n\n"+
			
			"If you have any question, you can call the experimenter."
			+ "\n Otherwise, we can start the study.";
			
	private JTextArea welcom  = new JTextArea(text);
	private JButton commencer = new JButton("Commencer");
	
	private CriteriaSelect cuisine;
	private CriteriaSelect cost;
	private CriteriaSelect athmos;
	private CriteriaSelect location;

	private static boolean isDone = false;
	
	public Acceuil(){
		
		Lock l = new ReentrantLock();
		l.lock();
		negotiators = new ArrayList<Negotiation<? extends Option>> ();
		l.unlock();
		Font font = new Font("Arial", Font.BOLD, 20);
		welcom.setFont(font);
		welcom.setEditable(false);
		
		//this.getContentPane().add(textAcceuil, BorderLayout.NORTH);
		this.getContentPane().add(welcom, BorderLayout.CENTER);
		this.getContentPane().add(commencer, BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.white);
		setMinimumSize(new Dimension(400, 500));
		pack();
		
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
							
							// Creer le modele de prefs de l'utilisateur
							Negotiation<Restaurant> nego = model.createModel();
							setUserPref(nego);
							System.out.println(nego.printPreferences());
							Models<Restaurant> other = new Models<Restaurant>();
//							Negotiation<Restaurant> agent = other.createOther(Restaurant.A_CITADELLA.getCriteria(),
//									Restaurant.class, nego.getSelfs());
							List<List<Self_Ci<Criterion>>> agents = other.agentModels(other.preferencesCreation(Restaurant.A_CITADELLA.getCriteria(),
									Restaurant.class), userPref.getSelfs());
							
						
							for(List<Self_Ci<Criterion>> elem:other.getMax(agents)){
								System.out.println(elem);
							}
							
							setNegotiators(negotiatorAgents(agents, other, Restaurant.class));
							
							isDone = true;
							location.setVisible(false);
							setVisible(false);
							
						}
							
					}
	            }
	        );
	}
	
	
	/**
	 * 
	 * @param prefs
	 * @param model
	 * @param type
	 * @return Initates the negotiation models from the preferences sets
	 */
	List<Negotiation<? extends Option>> negotiatorAgents (List<List<Self_Ci<Criterion>>> prefs, Models<? extends Option> model,
													Class<? extends Option> type){
		List<Negotiation<? extends Option>>  negotiators = new ArrayList<Negotiation<? extends Option>> ();
		for(List<Self_Ci<Criterion>> pref : prefs){
			negotiators.add(model.createNegotiation(pref,type));
		}
		return negotiators;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Acceuil frame = new Acceuil();
	        frame.setMinimumSize(new Dimension(200, 200));

			frame.pack();
			
			frame.setVisible(true);
		});
	}


	public static List<Negotiation<? extends Option>> getNegotiators() {
		Lock l = new ReentrantLock();
		l.lock();
		List<Negotiation<? extends Option>> neg = Acceuil.negotiators;
		l.unlock();
		return neg;
	}


	public static void  setNegotiators(List<Negotiation<? extends Option>> negotiators) {
		Lock l = new ReentrantLock();
		l.lock();
		Acceuil.negotiators = negotiators;
		l.unlock();
	}


	public static boolean isDone() {
		Lock l = new ReentrantLock();
		boolean state;
		l.lock();
		state= isDone;
		l.unlock();
		return state;
	}


	public static void setDone(boolean isDone) {
		Lock l = new ReentrantLock();
		l.lock();
		Acceuil.isDone = isDone;
		l.unlock();
	}


	public Negotiation<? extends Option> getUserPref() {
		return userPref;
	}


	public void setUserPref(Negotiation<? extends Option> userPref) {
		this.userPref = userPref;
	}

}
