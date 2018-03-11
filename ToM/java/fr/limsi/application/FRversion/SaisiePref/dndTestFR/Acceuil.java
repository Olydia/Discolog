package fr.limsi.application.FRversion.SaisiePref.dndTestFR;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JDialog;

import fr.limsi.application.UpPrincipalScreen1;
import fr.limsi.application.WriteHistory;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.Self_Ci;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import fr.limsi.negotiate.ToM.preferencesGeneration.Models;
import fr.limsi.negotiate.restaurant.Atmosphere;
import fr.limsi.negotiate.restaurant.Cost;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.Location;
import fr.limsi.negotiate.restaurant.Restaurant;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// il faut envoyer les prefs de l'agent � l'agent
// 

public class Acceuil extends Application{

	public static List<Negotiation<? extends Option>> negotiators;
	private Negotiation<? extends Option> userPref;
	WriteHistory writer;
	
	public static HashMap<String, List<String>> preferencesUser= 
				new HashMap<String, List<String>>();

	//private JLabel textAcceuil = new JLabel("Bienvenu");
	private String text = "For the purposes of this study, we ask you to enter your preferences for each criterion"
			+ "\n that will be discussed during the negotiation. \n\n"+
			
			"Note that the agents do not know your preferences \n \n"+

			"We will present you each criterion (Athmosphere, Cost, Cuisine, Location) one after the other. \n \n"

			+"For each criterion, you will have to rank your preferences in a descending order: "
			+ "\n put on the top of the list the value you like the most for this criterion. \n\n"+

			"If you have any question, at any time during the process, feel free to call the experimenter.";



	private CriteriaSelect cuisine;
	private CriteriaSelect cost;
	private CriteriaSelect athmos;
	private CriteriaSelect location;
	private String username;
	private File history;
	private static boolean isDone = false;

	public Acceuil(String username, File history) {
		this.username =username;
		this.history = history;
		writer = new WriteHistory();
	}
	@Override
	public void start(Stage stage){
		TextArea textWelcom  = new TextArea(text);
		Button btncommencer = new Button("Ok, I understand");
		// TODO Auto-generated method stub
		stage.setTitle("Preferences selection");
		Lock l = new ReentrantLock();
		l.lock();
		negotiators = new ArrayList<Negotiation<? extends Option>> ();
		l.unlock();
	    btncommencer.setStyle("-fx-font-weight: bold;");
		textWelcom.setEditable(false);


		BorderPane border = new BorderPane();
		border.setCenter(textWelcom);

		HBox btn = new HBox();
		btn.setAlignment(Pos.CENTER);
		btn.getChildren().add(btncommencer);
		border.setBottom(btn);
//		textA.
		//border.setPrefSize(700, 500);

		Platform.setImplicitExit(false);

		
		Scene scene = new Scene(border, 700, 500);
		stage.setScene(scene);
		stage.show();

		//this.getContentPane().setBackground(Color.white);



		// this.setLocation((int) Screen.getPrimary().getBounds().getMinX(), this.getY());
		//	setMinimumSize(new Dimension(400, 300));
		//showOnScreen(2, this);
		//center(this);
		cost = new CriteriaSelect(Cost.class);
		cuisine = new CriteriaSelect(Cuisine.class);
		athmos = new CriteriaSelect(Atmosphere.class);
		location = new CriteriaSelect(Location.class);

		// Init the model of preferences
		PreferencesModel model = new PreferencesModel();

		btncommencer.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(javafx.event.ActionEvent event) {
				stage.hide();
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
							//	showOnScreen(1, cuisine);
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

							// save the preferences
							preferencesUser.put(Cuisine.class.getSimpleName(),
									cuisine.asList());
							
							preferencesUser.put(Location.class.getSimpleName(),
											location.asList());
							
							preferencesUser.put(Cost.class.getSimpleName(),
									cost.asList());
							
							preferencesUser.put(Atmosphere.class.getSimpleName(),
									athmos.asList());
							
							location.setVisible(false);
							//*************** Mise a jour des informations et ecriture dans le fichier text
							String userPref = "User Preferences : \n \n" 
									+ getUserPref().printPreferences();
							WriteHistory writer = new WriteHistory();
							writer.write(userPref, history);

							List<Negotiation<? extends Option>> neg = Acceuil.getNegotiators();
							System.out.println(neg.toString());

							isDone = true;
							//hide current
							
							
						// lanch agents
							
							Platform.runLater(new Runnable() {
						        @Override
						        public void run() {
						        	System.out.println("Running");
									startAgents();

						          //javaFX operations should go here
						        }
						   });

						}

					}
				}
				);
		
		
		
	}


	public void startAgents(){
		//System.out.println("je lance les agents");
		List<Negotiation<? extends Option>> negotiations = getNegotiators();
		UpPrincipalScreen1 chat = new UpPrincipalScreen1("Bob", username, ADAPT.COMPLEMENT);
		chat.situation="restaurant";
		Stage chatStage=new Stage();
		chat.setPrefModel(negotiations.get(0));
		String bob = "Preferences of agent Bob \n \n" + negotiations.get(0).printPreferences();
		writer.write(bob, history);

		chat.start(chatStage);



	}
	public void center(JDialog frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 3;
		int y = (dim.height - h) / 3;
		frame.setLocation(x, y);

	}

	//    public static void showOnScreen( int screen, Application frame ) {
	//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	//        GraphicsDevice[] gd = ge.getScreenDevices();
	//       // gd[0].
	//        System.out.println(gd.length);
	//        if( screen > -1 && screen < gd.length ) {
	//            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.);
	//        } else if( gd.length > 0 ) {
	//            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, frame.getY());
	//        } else {
	//            throw new RuntimeException( "No Screens Found" );
	//        }
	//    }
	//	
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
