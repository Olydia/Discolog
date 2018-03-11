package fr.limsi.application.FRversion;

import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.disco.*;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.CriterionNegotiation;
import fr.limsi.negotiate.CriterionProposal;
import fr.limsi.negotiate.OptionProposal;
import fr.limsi.negotiate.Proposal.Status;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import fr.limsi.negotiate.restaurant.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DownPrincipalScreen2 extends AnchorPane{

	static double relation=0.8;
	//The next two strings contain the form of the button: on when pressed and off when released
	static  String on=" -fx-background-color: linear-gradient(#2A5058, #61a2b1);"+"-fx-font-size: 16px;"+ "-fx-text-fill: yellow;";
	static  String off=" -fx-background-color: linear-gradient(#61a2b1, #2A5058)";

	//public ExampleAgent agent;

	public String action;
	/**
	 * details allows to create the instructions used for execute:
	 * details[0]: The action. Ex: Propose.
	 * details[1]: The situation of the negotiation
	 * details[2]: criterion or situation(when an option is chosen)
	 * details[3]: value of the criterion or option
	 * details[4]: criterion or situation(when an option is chosen) for the second part
	 * details[5]: value of the criterion or option for the second part
	 * details[6]: To specify if the user likes or not the preference he is expressing
	 * details[7]: contains General or Specific to differentiate between the 2 forms of AskPreference
	 */
	public String[] details = new String[8];

	public DownPrincipalScreen2(){

	}


	public void addCriteria(Criterion[] c,ArrayList<String> criteria){
		
		Arrays.sort(c);

		for (int i=0;i<c.length;i++){

    		criteria.add(c[i].toString().toUpperCase());

		}
	}
	/**
	 * @param ch: a string in a viewlist
	 * @param situation: restaurant or movie
	 * @return boolean: true if ch is a criterion
	 */
	public boolean isCriterion(String ch,String situation){

		ArrayList<String> criteria =new ArrayList<String>();

			addCriteria(Atmosphere.values(),criteria);
			addCriteria(Cost.values(),criteria);
			addCriteria(Cuisine.values(),criteria);
			addCriteria(Location.values(),criteria);
		
		

	   	for (int i=0; i<criteria.size();i++){
	   		//System.out.println(ch.toUpperCase()+"-*****-"+criteria.get(i));
	   		if (ch.toUpperCase().equals(criteria.get(i))) return true;
	   	}
	   	return false;

	}
/**
 * finds the criterion based on its value (in the form a string)
 * @param ch: a string in a list
 * @param situation: restaurant or movie
 * @return a string representing the criterion
 */
	public String getCriterion(String ch,String situation){

		ArrayList<String> tmp =new ArrayList<String>();
		String criterion="";

			addCriteria(Atmosphere.values(),tmp);
			for (int i=0;i<tmp.size();i++){
				if (ch.toUpperCase().equals(tmp.get(i))){return "Atmosphere";}
			}
			addCriteria(Cost.values(),tmp);
			for (int i=0;i<tmp.size();i++){
				if (ch.toUpperCase().equals(tmp.get(i))){return "Cost";}
			}
			addCriteria(Cuisine.values(),tmp);
			for (int i=0;i<tmp.size();i++){
				if (ch.toUpperCase().equals(tmp.get(i))){return "Cuisine";}
			}
			addCriteria(Location.values(),tmp);
			for (int i=0;i<tmp.size();i++){
				if (ch.toUpperCase().equals(tmp.get(i))){return "Location";}
			}
	
		return criterion;

	}

	/**
	 * colors the buttons as if they are released
	 * @param cButtons
	 */
	public void colorButtons(ArrayList<Button> cButtons){
		for (int i=0;i<cButtons.size();i++){
			cButtons.get(i).setStyle(off);
		}
	}
	/**
	 * removes the buttons from the screen
	 * @param c1HBoxs
	 * @param c2HBoxs
	 * @param c3HBoxs
	 * @param c4HBoxs
	 * @param list: the list of visible elements
	 * @param situation: restaurant or movie
	 */
	public void clearLastButtons(ArrayList<HBox> c1HBoxs,ArrayList<HBox> c2HBoxs,ArrayList<HBox> c3HBoxs,ArrayList<HBox> c4HBoxs,ObservableList list,String situation){

	
			 for (int i=0;i<Atmosphere.values().length;i++){
				 list.remove(c1HBoxs.get(i));

			 }

			 for (int i=0;i<Cost.values().length;i++){
				 list.remove(c2HBoxs.get(i));
			 }

			 for (int i=0;i<Cuisine.values().length;i++){
				 list.remove(c3HBoxs.get(i));
			 }

			 for (int i=0;i<Location.values().length;i++){
				 list.remove(c4HBoxs.get(i));
			 }
        
    

	}

	public void setSecondaryButtonsTexts(Criterion[] c, ArrayList<Button>c1Buttons, ArrayList<Button> c12Buttons,ArrayList<HBox> c1HBoxs, ArrayList<HBox> c12HBoxs){
		Arrays.sort(c); 
		
		for (int i=0;i<c.length;i++){
			 Button iButton = new Button( c[i].toString());
			 Button i2Button = new Button( c[i].toString());
			 c1Buttons.add(iButton);
			 c12Buttons.add(i2Button);
			 HBox iHBox = new HBox(15);
			 HBox i2HBox = new HBox(15);
			 iHBox.getChildren().addAll(iButton);
			 i2HBox.getChildren().addAll(i2Button);
			 c1HBoxs.add(iHBox);
			 c12HBoxs.add(i2HBox);
		 }
	}

	public void setSecondaryButtonsPositions(HBox c1HBox, HBox c2HBox, HBox c3HBox, HBox c4HBox, Button c1, Button c2, Button c3, Button c4, double pos, String situation, ObservableList<Node> list){
		setTopAnchor(c1HBox,pos);
    	setTopAnchor(c2HBox,pos);
    	setTopAnchor(c3HBox,pos);
    	setTopAnchor(c4HBox,pos);

    	c1.setStyle(off);
    	c2.setStyle(off);
    	c3.setStyle(off);
    	c4.setStyle(off);

    	if (situation=="restaurant")
	        {
        	setLeftAnchor(c1HBox,300.0);
        	setLeftAnchor(c2HBox,500.0);
        	setLeftAnchor(c3HBox,700.0);
        	setLeftAnchor(c4HBox,900.0);
        	list.addAll(c1HBox,c2HBox,c3HBox,c4HBox);
	        }
    	else{
    		setLeftAnchor(c1HBox,350.0);
    		setLeftAnchor(c2HBox,600.0);
    		setLeftAnchor(c3HBox,850.0);
    		list.addAll(c1HBox,c2HBox,c3HBox);
    		}
	}

	public void setCriterionButtonsTexts(Button b1,Button b2, Button b3, Button b4, String situation){
		if (situation=="restaurant")
        {
		b1.setText("Atmosphere");
		b2.setText("Cost");
		b3.setText("Cuisine");
		b4.setText("Location");
        }
		else{
			b1.setText("Category");
			b2.setText("Country");
			b3.setText("Year");
		}
	}
	public void addElements(String username, String situation,Stage chatStage/*,Interaction interaction*/){

		 totalOrderedModels model = new totalOrderedModels();
			 ToMNegotiatorProba agent= new ToMNegotiatorProba("Bob", model.model4(), ADAPT.NONADAPT);
			 User user= new User("User");
			 Interaction interaction = new Interaction(
						/*new ExampleAgent("agent", model.model1())*/agent,
						/*new User(username)*/user,/*args.length > 0 && args[0].length() > 0 ? args[0] : */null);
				interaction.load("models/NegotiateFR.xml");
				((ToMNegotiatorProba) interaction.getSystem()).setRelation(relation);


				interaction.setGuess(false);

				interaction.start(true); // give user first turn

				OptionChoice optionChoice=new OptionChoice();
        		Stage optionStage=new Stage();
            	Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            	int ButtonId;
        	     Scene scene = new Scene(optionChoice, visualBounds.getWidth(), visualBounds.getHeight());
        	     optionStage.setScene(scene);
        	     optionStage.setFullScreen(true);
        	     optionStage.setScene(scene);
        	     scene.getStylesheets().add
        	        (Home1.class.getResource("application2.css").toExternalForm());

	    for (int i=0;i<8;i++){
	    	details[i]="";
	    }
		details[1]=situation;

		/*The lists*/

		ListView<String> open = new ListView<>();
      	open.setPrefWidth(450.0);
      	open.setPrefHeight(100.0);
      	ArrayList<String> openList =new ArrayList<String>();

		/*Labels*/
		Label actionLabel = new Label("What do you want to say?");
		Label stopLabel = new Label("Are you sure that you want to stop the discussion?");
		Label acceptLabel = new Label("What do you want to accept?");
		Label proposeCriterionLabel = new Label("What criterion do you want to propose?");
		Label rejectLabel = new Label("What do you want to reject?");
		Label proposeLabel = new Label("What do you want to propose?");
		Label whyLabel = new Label("What did you dislike about this proposal?");
		Label errorLabel = new Label("You must precise if you like or don't like");
		errorLabel.setId("errorMessage");

		Label answer1=new Label("");
		Label answer2=new Label("");
		Label answer3=new Label("");
		Label answer4=new Label("");

		ObservableList<Node> list = getChildren();

		/*The buttons*/
		Button proposeButton = new Button("Propose");
		Button acceptButton = new Button("Accept");
		Button rejectButton = new Button("Reject");
		Button stateAskButton = new Button("State/Ask");
		Button stopButton = new Button("Stop");

		Button proposeOptionButton = new Button(situation);
		Button proposeValueButton = new Button("criterion");

		Button counterproposeOptionButton = new Button(situation);
		Button counterproposeValueButton = new Button("criterion");

		Button acceptProposeButton = new Button("Accept and Propose");

		Button rejectOnlyButton = new Button("Just Reject");
		Button rejectStateButton = new Button("Reject and explain why");
		Button rejectProposeButton = new Button("Reject and counterpropose");

		Button stateButton = new Button("I (don't) like ...");
		Button askGeneralButton = new Button("what kind of ... do you like?");
		Button askSpecificButton = new Button("Do you like ... "+situation+"s?");

		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");

		Button likeButton = new Button("I like ...");
		Button dontLikeButton = new Button("I don't like ...");

		Button optionButton = new Button(situation);
		Button criterionButton = new Button("criterion");

		Button cG1Button = new Button();
		Button cG2Button = new Button();
		Button cG3Button = new Button();
		Button cG4Button = new Button();

		Button c1Button = new Button();
		Button c2Button = new Button();
		Button c3Button = new Button();
		Button c4Button = new Button();

		Button c12Button = new Button();
		Button c22Button = new Button();
		Button c32Button = new Button();
		Button c42Button = new Button();

		setCriterionButtonsTexts(c1Button,c2Button,c3Button,c4Button,situation);
		setCriterionButtonsTexts(c12Button,c22Button,c32Button,c42Button,situation);
		setCriterionButtonsTexts(cG1Button,cG2Button,cG3Button,cG4Button,situation);

			Button sendButton = new Button("Send");


		/*All criteria buttons*/

		ArrayList<Button> c1Buttons=new ArrayList<>();
		ArrayList<Button> c2Buttons=new ArrayList<>();
		ArrayList<Button> c3Buttons=new ArrayList<>();
		ArrayList<Button> c4Buttons=new ArrayList<>();

		ArrayList<HBox> c1HBoxs=new ArrayList<>();
		ArrayList<HBox> c2HBoxs=new ArrayList<>();
		ArrayList<HBox> c3HBoxs=new ArrayList<>();
		ArrayList<HBox> c4HBoxs=new ArrayList<>();

		ArrayList<Button> c12Buttons=new ArrayList<>();
		ArrayList<Button> c22Buttons=new ArrayList<>();
		ArrayList<Button> c32Buttons=new ArrayList<>();
		ArrayList<Button> c42Buttons=new ArrayList<>();

		ArrayList<HBox> c12HBoxs=new ArrayList<>();
		ArrayList<HBox> c22HBoxs=new ArrayList<>();
		ArrayList<HBox> c32HBoxs=new ArrayList<>();
		ArrayList<HBox> c42HBoxs=new ArrayList<>();

			setSecondaryButtonsTexts(Atmosphere.values(),c1Buttons,c12Buttons,c1HBoxs,c12HBoxs);
			setSecondaryButtonsTexts(Cost.values(),c2Buttons,c22Buttons,c2HBoxs,c22HBoxs);
			setSecondaryButtonsTexts(Cuisine.values(),c3Buttons,c32Buttons,c3HBoxs,c32HBoxs);
			setSecondaryButtonsTexts(Location.values(),c4Buttons,c42Buttons,c4HBoxs,c42HBoxs);
	

		ArrayList<Button> co1Buttons=new ArrayList<>();
		ArrayList<Button> co2Buttons=new ArrayList<>();
		ArrayList<Button> co3Buttons=new ArrayList<>();
		ArrayList<Button> co4Buttons=new ArrayList<>();

		ArrayList<HBox> co1HBoxs=new ArrayList<>();
		ArrayList<HBox> co2HBoxs=new ArrayList<>();
		ArrayList<HBox> co3HBoxs=new ArrayList<>();
		ArrayList<HBox> co4HBoxs=new ArrayList<>();


		ArrayList<Button> co12Buttons=new ArrayList<>();
		ArrayList<Button> co22Buttons=new ArrayList<>();
		ArrayList<Button> co32Buttons=new ArrayList<>();
		ArrayList<Button> co42Buttons=new ArrayList<>();

		ArrayList<HBox> co12HBoxs=new ArrayList<>();
		ArrayList<HBox> co22HBoxs=new ArrayList<>();
		ArrayList<HBox> co32HBoxs=new ArrayList<>();
		ArrayList<HBox> co42HBoxs=new ArrayList<>();

			setSecondaryButtonsTexts(Atmosphere.values(),co1Buttons,co12Buttons,co1HBoxs,co12HBoxs);
			setSecondaryButtonsTexts(Cost.values(),co2Buttons,co22Buttons,co2HBoxs,co22HBoxs);
			setSecondaryButtonsTexts(Cuisine.values(),co3Buttons,co32Buttons,co3HBoxs,co32HBoxs);
			setSecondaryButtonsTexts(Location.values(),co4Buttons,co42Buttons,co4HBoxs,co42HBoxs);
	

		HBox proposeHBox = new HBox(15);
		HBox acceptHBox = new HBox(15);
		HBox rejectHBox = new HBox(15);
		HBox stateAskHBox = new HBox(15);
		HBox stopHBox = new HBox(15);

		HBox proposeOptionHBox = new HBox(15);
		HBox proposeValueHBox = new HBox(15);

		HBox counterproposeOptionHBox = new HBox(15);
		HBox counterproposeValueHBox = new HBox(15);

		HBox acceptProposeHBox = new HBox(15);

		HBox rejectOnlyHBox = new HBox(15);
		HBox rejectStateHBox = new HBox(15);
		HBox rejectProposeHBox = new HBox(15);


		HBox stateHBox = new HBox(15);
		HBox askGeneralHBox = new HBox(15);
		HBox askSpecificHBox = new HBox(15);

		HBox yesHBox = new HBox(15);
		HBox noHBox = new HBox(15);

		HBox likeHBox = new HBox(15);
		HBox dontLikeHBox = new HBox(15);

		HBox optionHBox= new HBox(15);
		HBox criterionHBox= new HBox(15);

		HBox cG1HBox = new HBox(15);
		HBox cG2HBox = new HBox(15);
		HBox cG3HBox = new HBox(15);
		HBox cG4HBox = new HBox(15);

		HBox c1HBox = new HBox(15);
		HBox c2HBox = new HBox(15);
		HBox c3HBox = new HBox(15);
		HBox c4HBox = new HBox(15);

		HBox c12HBox = new HBox(15);
		HBox c22HBox = new HBox(15);
		HBox c32HBox = new HBox(15);
		HBox c42HBox = new HBox(15);

		HBox sendHBox = new HBox(15);

		proposeHBox.getChildren().addAll(proposeButton);
		acceptHBox.getChildren().addAll(acceptButton);
		rejectHBox.getChildren().addAll(rejectButton);
		stateAskHBox.getChildren().addAll(stateAskButton);
		stopHBox.getChildren().addAll(stopButton);

		proposeOptionHBox.getChildren().addAll(proposeOptionButton);
		proposeValueHBox.getChildren().addAll(proposeValueButton);

		counterproposeOptionHBox.getChildren().addAll(counterproposeOptionButton);
		counterproposeValueHBox.getChildren().addAll(counterproposeValueButton);

		acceptProposeHBox.getChildren().addAll(acceptProposeButton);

		rejectOnlyHBox.getChildren().addAll(rejectOnlyButton);
		rejectStateHBox.getChildren().addAll(rejectStateButton);
		rejectProposeHBox.getChildren().addAll(rejectProposeButton);

		stateHBox.getChildren().addAll(stateButton);
		askGeneralHBox.getChildren().addAll(askGeneralButton);
		askSpecificHBox.getChildren().addAll(askSpecificButton);

		yesHBox.getChildren().addAll(yesButton);
		noHBox.getChildren().addAll(noButton);

		likeHBox.getChildren().addAll(likeButton);
		dontLikeHBox.getChildren().addAll(dontLikeButton);

		optionHBox.getChildren().addAll(optionButton);
		criterionHBox.getChildren().addAll(criterionButton);


		cG1HBox.getChildren().addAll(cG1Button);
		cG2HBox.getChildren().addAll(cG2Button);
		cG3HBox.getChildren().addAll(cG3Button);
		cG4HBox.getChildren().addAll(cG4Button);

		c1HBox.getChildren().addAll(c1Button);
		c2HBox.getChildren().addAll(c2Button);
		c3HBox.getChildren().addAll(c3Button);
		c4HBox.getChildren().addAll(c4Button);


		c12HBox.getChildren().addAll(c12Button);
		c22HBox.getChildren().addAll(c22Button);
		c32HBox.getChildren().addAll(c32Button);
		c42HBox.getChildren().addAll(c42Button);

		sendHBox.getChildren().addAll(sendButton);

		/*The positions*/
		   setTopAnchor(actionLabel,10.0);
		   setLeftAnchor(actionLabel,100.0);


		   setTopAnchor(acceptLabel,150.0);
		   setLeftAnchor(acceptLabel,100.0);

		   setTopAnchor(rejectLabel,150.0);
		   setLeftAnchor(rejectLabel,100.0);

		   setTopAnchor(proposeHBox,50.0);
		   setLeftAnchor(proposeHBox,350.0);

		   setTopAnchor(acceptHBox ,50.0);
		   setLeftAnchor(acceptHBox ,500.0);

		   setTopAnchor(rejectHBox,50.0);
		   setLeftAnchor(rejectHBox,650.0);

		   setTopAnchor(stateAskHBox,50.0);
		   setLeftAnchor(stateAskHBox,800.0);

		   setTopAnchor(stopHBox,50.0);
		   setLeftAnchor(stopHBox,950.0);

		   setTopAnchor(sendHBox,400.0);
		   setLeftAnchor(sendHBox,600.0);

		   list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox/*,sendHBox*/);
		   if (relation>0.5){
		   boolean guess1 = interaction.getProperty("interaction@guess", interaction.isGuess());
		   interaction.getSystem().respond(interaction, false, guess1);}

		   /*The actions*/

		   open.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
			   @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				   if ((open.getSelectionModel().getSelectedItem()!=null)&&(details[0].equals("Accept"))){
					   list.remove(acceptProposeHBox);
				   if (isCriterion(newValue, situation)){
					   setTopAnchor(acceptProposeHBox,260.0);
					   setLeftAnchor(acceptProposeHBox,650.0);
			        	list.add(acceptProposeHBox);
			        }
				   }
			        //System.out.println("Selected item: " + newValue);
			    }
		   });

		   proposeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.clear();
	     		   list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
	     			details[0]="propose";
	            	proposeButton.setStyle(on);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);

	            	answer1.setText("Let's go to ");
	            	answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	setTopAnchor(sendHBox,350.0);
	            	setLeftAnchor(sendHBox,600.0);

	            	setTopAnchor(proposeOptionHBox,100.0);
	            	setLeftAnchor(proposeOptionHBox,450.0);

	     		   	setTopAnchor(proposeValueHBox ,100.0);
	     		   	setLeftAnchor(proposeValueHBox ,750.0);

	     		   proposeOptionButton.setStyle(off);
	     		   proposeValueButton.setStyle(off);
	     		   list.addAll(proposeOptionHBox,proposeValueHBox);
	            }
	        });


		   acceptButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.clear();
		     		list.addAll(actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		details[0]="Accept";
	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(on);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);

	     		   	openList.clear();
	     		   for(CriterionNegotiation<Criterion> cr :agent.getNegotiation().getValuesNegotiation()){
    		   			for(CriterionProposal co:cr.getProposalsWithStatus(Status.OPEN,true))
		    				openList.add(co.toString());

    		   		}


	    		   	for (OptionProposal op: agent.getNegotiation().getOptionsProposals(Status.OPEN,true)){
	    		   		openList.add(op.toString());
	    		   		}
	    		    ObservableList<String> observableOpenList = FXCollections.observableArrayList(openList);

	            	open.setItems(observableOpenList);


		     		list.addAll(open,sendHBox);
		     		setTopAnchor(open,150.0);
		     		setLeftAnchor(open,400.0);

		     		setTopAnchor(sendHBox,260.0);
	            	setLeftAnchor(sendHBox,550.0);

	            	setTopAnchor(acceptLabel,100.0);
	            	setLeftAnchor(acceptLabel,100.0);
	            	list.add(acceptLabel);
	            }
	        });


		   rejectButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(on);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);

	            	counterproposeOptionButton.setStyle(off);
		     		counterproposeValueButton.setStyle(off);

	            	details[0]="Reject";

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);


	            	setTopAnchor(rejectOnlyHBox,100.0);
	            	setLeftAnchor(rejectOnlyHBox,250.0);

	     		   	setTopAnchor(rejectStateHBox ,100.0);
	     		   	setLeftAnchor(rejectStateHBox ,550.0);

	     		  	setTopAnchor(rejectProposeHBox ,100.0);
	     		   	setLeftAnchor(rejectProposeHBox ,850.0);

	     		   openList.clear();
	     			for(CriterionNegotiation<Criterion> cr :agent.getNegotiation().getValuesNegotiation()){
     		   			for(CriterionProposal co:cr.getProposalsWithStatus(Status.OPEN,true))
		    				openList.add(co.toString());

     		   		}

	    		   	for (OptionProposal op: agent.getNegotiation().getOptionsProposals(Status.OPEN,true)){
	    		   		openList.add(op.toString());
	    		   		}

	    		    ObservableList<String> observableOpenList = FXCollections.observableArrayList(openList);

	            	open.setItems(observableOpenList);
	            	rejectOnlyButton.setStyle(off);
	            	rejectStateButton.setStyle(off);
	            	rejectProposeButton.setStyle(off);
	     		   	list.addAll(rejectOnlyHBox,rejectStateHBox,rejectProposeHBox );
	            }

	        });


		   stateAskButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(on);
	            	stopButton.setStyle(off);
	            	details[0]="statepreference";
	            	details[6]="";
	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);

	            	setTopAnchor(stateHBox,100.0);
	            	setLeftAnchor(stateHBox,250.0);

	     		   	setTopAnchor(askGeneralHBox ,100.0);
	     		   	setLeftAnchor(askGeneralHBox ,500.0);

	     		  	setTopAnchor(askSpecificHBox ,100.0);
	     		   	setLeftAnchor(askSpecificHBox ,850.0);

	     		   setTopAnchor(sendHBox,300.0);
	            	setLeftAnchor(sendHBox,600.0);
	            	stateButton.setStyle(off);
	            	askGeneralButton.setStyle(off);
	            	askSpecificButton.setStyle(off);
	     		   	list.addAll(stateHBox,askGeneralHBox,askSpecificHBox);
	            }
	        });


		   stopButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(on);

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);


	            	setTopAnchor(stopLabel,100.0);
	            	setLeftAnchor(stopLabel,450.0);

	     		   	setTopAnchor(noHBox ,150.0);
	     		   	setLeftAnchor(noHBox ,530.0);

	     		  	setTopAnchor(yesHBox ,150.0);
	     		   	setLeftAnchor(yesHBox ,730.0);


	     		   	list.addAll(stopLabel,noHBox,yesHBox);

	            }
	        });


		   proposeOptionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	proposeOptionButton.setStyle(on);
	            	proposeValueButton.setStyle(off);

	            	details[0]="Propose";
	            	details[2]=situation;
	            	details[2]=details[2].substring(0, 1).toUpperCase() + details[2].substring(1).toLowerCase();


	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(proposeOptionHBox,proposeValueHBox);

		     		answer1.setText("Let's go to the ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	optionChoice.start(optionStage);

	            	optionChoice.addElements(situation,optionStage,chatStage,details,list);
	            	chatStage.hide();

	            	list.add(sendHBox);

	            }
	        });


		   proposeValueButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	proposeOptionButton.setStyle(off);
	            	proposeValueButton.setStyle(on);

	            	details[0]="Propose";

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(proposeOptionHBox,proposeValueHBox);
		     		list.addAll(proposeCriterionLabel);
		     		answer1.setText("Let's go to a ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");
		     		setTopAnchor(proposeCriterionLabel,150.0);
		     		setLeftAnchor(proposeCriterionLabel,100.0);
		     		setSecondaryButtonsPositions(c1HBox,c2HBox,c3HBox,c4HBox,c1Button,c2Button,c3Button,c4Button,200.0,situation,list);




	            }
	        });

		   counterproposeOptionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	counterproposeOptionButton.setStyle(on);
	            	counterproposeValueButton.setStyle(off);

	            	list.add(sendHBox);

	            	OptionChoice optionChoice=new OptionChoice();
	            	Stage optionStage=new Stage();
	            	Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

	        	     Scene scene = new Scene(optionChoice, visualBounds.getWidth(), visualBounds.getHeight());
	        	     optionStage.setScene(scene);
	        	     optionStage.setFullScreen(true);
	        	     optionStage.setScene(scene);
	        	     scene.getStylesheets().add
	        	        (Home1.class.getResource("application2.css").toExternalForm());
	            	optionChoice.start(optionStage);

	            	optionChoice.addElements(situation,optionStage,chatStage,details,list);
	            	chatStage.hide();

	            }
	        });


		   counterproposeValueButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	counterproposeOptionButton.setStyle(off);
	            	counterproposeValueButton.setStyle(on);

		     		setTopAnchor(proposeCriterionLabel,150.0);
		     		setLeftAnchor(proposeCriterionLabel,100.0);
		     		list.clear();
		     		list.addAll(actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);

		     		   list.addAll(counterproposeOptionHBox,counterproposeValueHBox,open);


		            	list.addAll(proposeLabel);
		            	if (details[0]=="AcceptPropose"){
		            		list.addAll(acceptLabel);
		            		}
		            	if (details[0]=="RejectPropose"){
		            		list.addAll(rejectOnlyHBox,rejectStateHBox,rejectProposeHBox,rejectLabel);
		            	}
	            	setSecondaryButtonsPositions(c12HBox,c22HBox,c32HBox,c42HBox,c12Button,c22Button,c32Button,c42Button,350.0,situation,list);

	            }
	        });

		   acceptProposeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="AcceptPropose";

		     		answer1.setText("Okay, let's go to  the ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);


		     		setTopAnchor(counterproposeOptionHBox,300.0);
	            	setLeftAnchor(counterproposeOptionHBox,450.0);

	     		   	setTopAnchor(counterproposeValueHBox ,300.0);
	     		   	setLeftAnchor(counterproposeValueHBox ,750.0);

	     		   setTopAnchor(proposeLabel,250.0);
	     		   setLeftAnchor(proposeLabel,100.0);
	     		   setTopAnchor(sendHBox,450.0);

	     		   list.addAll(counterproposeOptionHBox,counterproposeValueHBox,open);

	            	list.addAll(acceptLabel,proposeLabel);
	            }
	        });


		  rejectOnlyButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="Reject";
	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(rejectOnlyHBox,rejectStateHBox,rejectProposeHBox);

		     		answer1.setText("I'd rather choose something else");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	rejectOnlyButton.setStyle(on);
	            	rejectStateButton.setStyle(off);
	            	rejectProposeButton.setStyle(off);

	     		   setTopAnchor(open,200.0);
		     		setLeftAnchor(open,400.0);

		     		setTopAnchor(sendHBox,350.0);
	            	setLeftAnchor(sendHBox,600.0);

	     		 //  list.addAll(optionHBox,criterionHBox);
	     		  list.addAll(rejectLabel,open,sendHBox);

	            }
	        });


		   rejectStateButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="RejectState";
	            	details[6]="FALSE";
	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(rejectOnlyHBox,rejectStateHBox,rejectProposeHBox);

		     		answer1.setText("I don't like ");
		     		answer3.setText("let's choose something else");
		     		answer2.setText("");
	            	answer4.setText("");

	            	rejectOnlyButton.setStyle(off);
	            	rejectStateButton.setStyle(on);
	            	rejectProposeButton.setStyle(off);

	     		   setTopAnchor(open,150.0);
		     		setLeftAnchor(open,400.0);

		     		setTopAnchor(whyLabel,250.0);
	            	setLeftAnchor(whyLabel,100.0);

	            	likeButton.setStyle(off);
	            	dontLikeButton.setStyle(off);

	            	setTopAnchor(sendHBox,400.0);
	            	setLeftAnchor(sendHBox,600.0);

	            	setSecondaryButtonsPositions(c12HBox,c22HBox,c32HBox,c42HBox,c12Button,c22Button,c32Button,c42Button,300.0,situation,list);

	     		//   list.addAll(optionHBox,criterionHBox,option2HBox,criterion2HBox);
	     		  list.addAll(rejectLabel,open,whyLabel);
	            }
	        });

		   rejectProposeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="RejectPropose";

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(rejectOnlyHBox,rejectStateHBox,rejectProposeHBox);

		     		answer1.setText("I don't want to go to ");
		     		answer3.setText(". Let's rather go to ");
		     		answer2.setText("");
	            	answer4.setText("");

	            	rejectOnlyButton.setStyle(off);
	            	rejectStateButton.setStyle(off);
	            	rejectProposeButton.setStyle(on);

		     		   setTopAnchor(open,150.0);
			     		setLeftAnchor(open,400.0);

	            	setTopAnchor(proposeCriterionLabel,250.0);
		     		setLeftAnchor(proposeCriterionLabel,100.0);

	              	setTopAnchor(proposeLabel,250.0);
	            	setLeftAnchor(proposeLabel,100.0);

	            	setTopAnchor(counterproposeOptionHBox,300.0);
	            	setLeftAnchor(counterproposeOptionHBox,450.0);

	     		   	setTopAnchor(counterproposeValueHBox ,300.0);
	     		   	setLeftAnchor(counterproposeValueHBox ,750.0);

	     		   	setTopAnchor(sendHBox,450.0);
	            	setLeftAnchor(sendHBox,600.0);

	     		  list.addAll(rejectLabel,proposeLabel,open,counterproposeOptionHBox,counterproposeValueHBox);
	            }
	        });


		   stateButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="StatePreference";

	            	stateButton.setStyle(on);
	            	askGeneralButton.setStyle(off);
	            	askSpecificButton.setStyle(off);


	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(stateHBox,askGeneralHBox,askSpecificHBox);

		     		answer1.setText("I (don't) like");
		     		answer2.setText("");
	            	answer4.setText("");
	            	answer3.setText("");

	            	setTopAnchor(likeHBox,150.0);
	            	setTopAnchor(dontLikeHBox,150.0);
	            	setLeftAnchor(likeHBox,500.0);
	            	setLeftAnchor(dontLikeHBox,700.0);
	            	likeButton.setStyle(off);
	            	dontLikeButton.setStyle(off);
	            	setTopAnchor(sendHBox,300.0);
	            	setLeftAnchor(sendHBox,600.0);
	            	list.addAll(likeHBox,dontLikeHBox);

	            	setSecondaryButtonsPositions(c1HBox,c2HBox,c3HBox,c4HBox,c1Button,c2Button,c3Button,c4Button,200.0,situation,list);

	            }
	        });


		   askGeneralButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	details[0]="AskPreference";
	            	details[7]="General";
	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(stateHBox,askGeneralHBox,askSpecificHBox);

		     		answer1.setText("What kind of "+situation+"s do you like?");
		     		answer2.setText("");
	            	answer4.setText("");
	            	answer3.setText("");

	            	setSecondaryButtonsPositions(cG1HBox,cG2HBox,cG3HBox,cG4HBox,cG1Button,cG2Button,cG3Button,cG4Button,170.0,situation,list);

	            	  setTopAnchor(sendHBox,250.0);
		            	setLeftAnchor(sendHBox,600.0);
	            	stateButton.setStyle(off);
	            	askGeneralButton.setStyle(on);
	            	askSpecificButton.setStyle(off);


	            }
	        });

		   askSpecificButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="AskPreference";
	            	details[7]="Specefic";
	            	stateButton.setStyle(off);
	            	askGeneralButton.setStyle(off);
	            	askSpecificButton.setStyle(on);

	            	answer1.setText("Do you like ");
	            	answer2.setText("");
	            	answer4.setText("");
	            	answer3.setText("");

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(stateHBox,askGeneralHBox,askSpecificHBox);

	            	setSecondaryButtonsPositions(c1HBox,c2HBox,c3HBox,c4HBox,c1Button,c2Button,c3Button,c4Button,170.0,situation,list);

	            }
	        });

		  noButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);


	            	//list.removeAll(stateButton,askGeneralButton,askSpecificButton,proposeValueButton,proposeOptionButton,rejectOnlyHBox,rejectStateHBox,rejectProposeHBox ,stopLabel,noHBox,yesHBox,c1HBox,c2HBox,c3HBox,c4HBox);

	            	//list.removeAll(co1Button, co2Button, co3Button,co4Button,c1Button,c2Button,c3Button,c4Button,acceptCriterionLabel,acceptOptionLabel,proposeCriterionLabel,proposeOptionLabel,rejectOptionLabel,rejectCriterionLabel);

	            }
	        });

		  yesButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	yesButton.setStyle(on);
	            	noButton.setStyle(off);

	            	File fichier1 = new File(System.getProperty("java.io.tmpdir")+File.separator+"Console.test");
	            	File fichier2 = new File(System.getProperty("user.dir")+File.separator+"T1-Bob-dom"+relation+"-exp"+username+".txt");

	            	WriteHistory history=new WriteHistory();
	            	history.copyFile(fichier1,fichier2);
	            	chatStage.hide();
	            }
	        });

		  likeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[6]="TRUE";
	            	answer1.setText("I like ");
	            	answer2.setText("");
	            	answer4.setText("");
	            	answer3.setText("");

	            	likeButton.setStyle(on);
	            	dontLikeButton.setStyle(off);

	            }
	        });

		  dontLikeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[6]="FALSE";
	            	answer1.setText("I don't like ");
	            	answer2.setText("");
	            	answer4.setText("");
	            	answer3.setText("");

	            	dontLikeButton.setStyle(on);
	            	likeButton.setStyle(off);

	            }
	        });

		  optionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	optionButton.setStyle(on);
	            	criterionButton.setStyle(off);

	            	list.removeAll(c1HBox,c2HBox,c3HBox,c4HBox/*,co1HBox,co2HBox,co3HBox,co4HBox*/);
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);

	            //	setSecondaryButtonsPositions(co1HBox,co2HBox,co3HBox,co4HBox,co1Button,co2Button,co3Button,co4Button,200.0,situation,list);

	            }
	        });

		  criterionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	criterionButton.setStyle(on);
	            	optionButton.setStyle(off);

	            	list.removeAll(c1HBox,c2HBox,c3HBox,c4HBox/*,co1HBox,co2HBox,co3HBox,co4HBox*/);
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);

	            	setSecondaryButtonsPositions(c1HBox,c2HBox,c3HBox,c4HBox,c1Button,c2Button,c3Button,c4Button,200.0,situation,list);

	            }
	        });



		  c1Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	            	details[2]=c1Button.getText();
	            	 c3Button.setStyle(off);
	            	 c1Button.setStyle(on);
	            	 c2Button.setStyle(off);
	            	 c4Button.setStyle(off);
	            	 colorButtons(c1Buttons);
	            	 if (situation=="restaurant")
	     	        {

	     				 for (int i=0;i<Atmosphere.values().length;i++){

	     					setTopAnchor(c1HBoxs.get(i),getTopAnchor(c1HBox)+50.0);
	     					setLeftAnchor(c1HBoxs.get(i),300.0+200.0*i);
	     					list.add(c1HBoxs.get(i));
	     				 }

	     	        }
	            	 else{
	            		 for (int i=0;i<6;i++){

		     					setTopAnchor(c1HBoxs.get(i),getTopAnchor(c1HBox)+50.0);
		     					setLeftAnchor(c1HBoxs.get(i),200.0+150.0*i);
		     					list.add(c1HBoxs.get(i));
	            		 }
	            	 }
	            }
	        });

		  c2Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	              	details[2]=c2Button.getText();
	            	c3Button.setStyle(off);
	            	 c1Button.setStyle(off);
	            	 c2Button.setStyle(on);
	            	 c4Button.setStyle(off);
	            	 colorButtons(c2Buttons);
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<3;i++){

		     					setTopAnchor(c2HBoxs.get(i),getTopAnchor(c2HBox)+50.0);
		     					setLeftAnchor(c2HBoxs.get(i),300.0+250.0*i);
		     					list.add(c2HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){

			     					setTopAnchor(c2HBoxs.get(i),getTopAnchor(c2HBox)+50.0);
			     					setLeftAnchor(c2HBoxs.get(i),200.0+150.0*i);
			     					list.add(c2HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  c3Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	              	details[2]=c3Button.getText();
	            	c3Button.setStyle(on);
		           	 c1Button.setStyle(off);
		           	 c2Button.setStyle(off);
		           	 c4Button.setStyle(off);
		           	colorButtons(c3Buttons);
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<7;i++){
		     					setTopAnchor(c3HBoxs.get(i),getTopAnchor(c3HBox)+50.0);
		     					setLeftAnchor(c3HBoxs.get(i),150.0+150.0*i);
		     					list.add(c3HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(c3HBoxs.get(i),getTopAnchor(c3HBox)+50.0);
			     					setLeftAnchor(c3HBoxs.get(i),200.0+150.0*i);
			     					list.add(c3HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  c4Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	              	details[2]=c4Button.getText();
	            	 c3Button.setStyle(off);
	            	 c1Button.setStyle(off);
	            	 c2Button.setStyle(off);
	            	 c4Button.setStyle(on);
	            	 colorButtons(c4Buttons);
		     				 for (int i=0;i<5;i++){
		     					setTopAnchor(c4HBoxs.get(i),getTopAnchor(c4HBox)+50.0);
		     					setLeftAnchor(c4HBoxs.get(i),200.0+200.0*i);
		     					list.add(c4HBoxs.get(i));
		     				 }
		            	 }

	        });


		  cG1Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.remove(sendHBox);
	            	list.add(sendHBox);
	            	details[2]=cG1Button.getText();
	            	 cG3Button.setStyle(off);
	            	 cG1Button.setStyle(on);
	            	 cG2Button.setStyle(off);
	            	 cG4Button.setStyle(off);

	            }
	        });

		  cG2Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.remove(sendHBox);

	            	list.add(sendHBox);
	              	details[2]=cG2Button.getText();
	            	cG3Button.setStyle(off);
	            	 cG1Button.setStyle(off);
	            	 cG2Button.setStyle(on);
	            	 cG4Button.setStyle(off);


	            }
	        });

		  cG3Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.remove(sendHBox);

	            	list.add(sendHBox);
	              	details[2]=cG3Button.getText();
	            	cG3Button.setStyle(on);
		           	 cG1Button.setStyle(off);
		           	 cG2Button.setStyle(off);
		           	 cG4Button.setStyle(off);


	            }
	        });

		  cG4Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.remove(sendHBox);

	            	list.add(sendHBox);
	              	details[2]=cG4Button.getText();
	            	 cG3Button.setStyle(off);
	            	 cG1Button.setStyle(off);
	            	 cG2Button.setStyle(off);
	            	 cG4Button.setStyle(on);
	            }

	        });


		  c12Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);
	              	details[4]=c12Button.getText();
	            	 c32Button.setStyle(off);
	            	 c12Button.setStyle(on);
	            	 c22Button.setStyle(off);
	            	 c42Button.setStyle(off);
			         colorButtons(c12Buttons);

	            	 if (situation=="restaurant")
	     	        {

	     				 for (int i=0;i<4;i++){
	     					setTopAnchor(c12HBoxs.get(i),getTopAnchor(c12HBox)+50.0);
	     					setLeftAnchor(c12HBoxs.get(i),300.0+200.0*i);
	     					list.add(c12HBoxs.get(i));
	     				 }

	     	        }
	            	 else{
	            		 for (int i=0;i<6;i++){
		     					setTopAnchor(c12HBoxs.get(i),getTopAnchor(c12HBox)+50.0);
		     					setLeftAnchor(c12HBoxs.get(i),200.0+150.0*i);
		     					list.add(c12HBoxs.get(i));
	            		 }
	            	 }
	            }
	        });

		  c22Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);
	            	details[4]=c22Button.getText();
	            	c32Button.setStyle(off);
	            	 c12Button.setStyle(off);
	            	 c22Button.setStyle(on);
	            	 c42Button.setStyle(off);
	            	  colorButtons(c22Buttons);
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<3;i++){
		     					setTopAnchor(c22HBoxs.get(i),getTopAnchor(c22HBox)+50.0);
		     					setLeftAnchor(c22HBoxs.get(i),300.0+250.0*i);
		     					list.add(c22HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(c22HBoxs.get(i),getTopAnchor(c22HBox)+50.0);
			     					setLeftAnchor(c22HBoxs.get(i),200.0+150.0*i);
			     					list.add(c22HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  c32Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);
	            	details[4]=c32Button.getText();
	            	c32Button.setStyle(on);
		           	 c12Button.setStyle(off);
		           	 c22Button.setStyle(off);
		           	 c42Button.setStyle(off);
		             colorButtons(c32Buttons);
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<7;i++){
		     					setTopAnchor(c32HBoxs.get(i),getTopAnchor(c32HBox)+50.0);
		     					setLeftAnchor(c32HBoxs.get(i),150.0+150.0*i);
		     					list.add(c32HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(c32HBoxs.get(i),getTopAnchor(c32HBox)+50.0);
			     					setLeftAnchor(c32HBoxs.get(i),200.0+150.0*i);
			     					list.add(c32HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  c42Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);
	            	details[4]=c42Button.getText();
	            	 c32Button.setStyle(off);
	            	 c12Button.setStyle(off);
	            	 c22Button.setStyle(off);
	            	 c42Button.setStyle(on);
	            	 colorButtons(c42Buttons);
		     				 for (int i=0;i<5;i++){
		     					setTopAnchor(c42HBoxs.get(i),getTopAnchor(c42HBox)+50.0);
		     					setLeftAnchor(c42HBoxs.get(i),200.0+200.0*i);
		     					list.add(c42HBoxs.get(i));
		     				 }
		            	 }

	        });

		  /*Defining the actions of the button arrays*/


				 for (ButtonId=0;ButtonId<c1Buttons.size();ButtonId++){
					 String cr1=c1Buttons.get(ButtonId).getText();
					 String cr12=c12Buttons.get(ButtonId).getText();
					 final Button myButton=c1Buttons.get(ButtonId);
					 final Button myButton2=c12Buttons.get(ButtonId);
					 myButton.setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {
			            		colorButtons(c1Buttons);
			            		myButton.setStyle(on);
			            		list.remove(sendHBox);
			            		list.add(sendHBox);
			     				 details[3]= cr1;

				            	 	}

							 	});

					 myButton2.setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {
				            			colorButtons(c12Buttons);
				            			myButton2.setStyle(on);
				        				list.remove(sendHBox);
				            			list.add(sendHBox);
					     				details[5]=cr12;

					            	 	}

								 	});

				 }

				 for (int i=0;i<c2Buttons.size();i++){
					 String cr2=c2Buttons.get(i).getText();
					 String cr22=c22Buttons.get(i).getText();
					 final Button myButton=c2Buttons.get(i);
					 final Button myButton2=c22Buttons.get(i);
					 myButton.setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {
			            	colorButtons(c2Buttons);
			            		myButton.setStyle(on);
			        			list.remove(sendHBox);
			            		list.add(sendHBox);
				     				 details[3]=cr2;
				            	 	}

							 	});

					 myButton2.setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {
				            	colorButtons(c22Buttons);
				            	myButton2.setStyle(on);
				        			list.remove(sendHBox);
				            		list.add(sendHBox);
					     				 details[5]=cr22;
					            	 	}

								 	});

				 }


				 for (int i=0;i<c3Buttons.size();i++){
					 String cr3=c3Buttons.get(i).getText();
					 String cr32=c32Buttons.get(i).getText();
					 final Button myButton=c3Buttons.get(i);
					 final Button myButton2=c32Buttons.get(i);
					 myButton.setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {
			            			colorButtons(c3Buttons);
			            			myButton.setStyle(on);
			        				list.remove(sendHBox);
			            			list.add(sendHBox);
				     				 details[3]=cr3;
				            	 	}

							 	});

					 myButton2.setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {
				            	colorButtons(c32Buttons);
				            	myButton2.setStyle(on);
				            			list.remove(sendHBox);
				            			list.add(sendHBox);
					     				 details[5]=cr32;
					            	 	}

								 	});

				 }

				 for (int i=0;i<c4Buttons.size();i++){
					 String cr4=c4Buttons.get(i).getText();
					 String cr42=c42Buttons.get(i).getText();
					 final Button myButton=c4Buttons.get(i);
					 final Button myButton2=c42Buttons.get(i);
					 myButton.setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {
			            			colorButtons(c4Buttons);
			            			myButton.setStyle(on);
			            			list.remove(sendHBox);
			            			list.add(sendHBox);
				     				details[3]=cr4;
				            	 	}

							 	});

					 myButton2.setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {
				            			colorButtons(c42Buttons);
				            			myButton.setStyle(on);
				            			list.remove(sendHBox);
				            			list.add(sendHBox);
					     				details[5]=cr42;
					            	 	}

								 	});


				 }
				 /**
				  * This method implements what the send button does. It creates the commands of Disco
				  * in the form of strings by assembling the parts created by all the other buttons
				  */
				  sendButton.setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {

			            	String command;
			        		String command1="null";
			        		String command2="";
			        		details[3]=details[3].replace(" ","_");
			        		details[3]=details[3].toUpperCase();
			        		details[5]=details[5].replace(" ","_");
			        		details[5]=details[5].toUpperCase();
			        		if (details[0]=="AskPreference"){
			        			if (details[7]=="General"){
			        				command1="Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+".class";
				        			command2="null";

			        			}
			        			else{
			        				command1="Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+".class";
			        				command2="Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3];
			        			}
			        		}
			        		else if (details[0]=="StatePreference"){
			        			command1="Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3];
			        			command2="Packages.fr.limsi.negotiate.Statement.Satisfiable."+details[6];
			        		}
			        		else if (details[0]=="Propose"){
			        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
			        		}
			        		else if ((details[0]=="Accept")||(details[0]=="Reject")){
			        			if (isCriterion(open.getSelectionModel().getSelectedItem(),situation)){
				        			details[2]=getCriterion(open.getSelectionModel().getSelectedItem(),situation);
				        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
				        			details[3]=details[3].replace(" ","_");
			        			}
			        			else{
			        				details[2]=situation.substring(0, 1).toUpperCase()+situation.substring(1);
				        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
				        			details[3]=details[3].replace(" ","_");
			        			}
			        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
			        		}

			        		else if (details[0]=="RejectState"){
			        			if (isCriterion(open.getSelectionModel().getSelectedItem(),situation)){
				        			details[2]=getCriterion(open.getSelectionModel().getSelectedItem(),situation);
				        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
				        			details[3]=details[3].replace(" ","_");
			        			}
			        			else{
			        			details[2]=situation.substring(0, 1).toUpperCase()+situation.substring(1);
			        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
			        			details[3]=details[3].replace(" ","_");
			        			}
			        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
			        			command2="Packages.fr.limsi.negotiate."+details[1]+"."+details[4]+"."+details[5];
			        		}
			        		else {
			        			if (isCriterion(open.getSelectionModel().getSelectedItem(),situation)){
				        			details[2]=getCriterion(open.getSelectionModel().getSelectedItem(),situation);
				        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
				        			details[3]=details[3].replace(" ","_");
			        			}
			        			else{
			        			details[2]=situation.substring(0, 1).toUpperCase()+situation.substring(1);
			        			details[3]=open.getSelectionModel().getSelectedItem().toUpperCase();
			        			details[3]=details[3].replace(" ","_");
			        			}
			        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
			        			command2="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[4]+"."+details[5]+")";
			        		}
			        		command="fr.limsi.negotiate.lang."+details[0]+"/"+command1;
			        		if (command2!="") command=command+"/"+command2;

			        		//System.out.println(command);
			        		if ((details[0]=="StatePreference") && (details[6]=="")){
				        		setTopAnchor(errorLabel,500.0);
				        		setLeftAnchor(errorLabel,800.0);
				        		list.add(errorLabel);
				        		}
				        		else{
			        		interaction.getConsole().execute(command);
			        		boolean guess = interaction.getProperty("interaction@guess", interaction.isGuess());
			        		interaction.getSystem().respond(interaction, false, guess);
			        		if (agent.getNegotiation().negotiationSuccess()){System.out.println("We reached an agreement, the negotiation is over!");}
				        		}
			        		list.clear();
				     		   list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
				     		  proposeButton.setStyle(off);
				            	acceptButton.setStyle(off);
				            	rejectButton.setStyle(off);
				            	stateAskButton.setStyle(off);
				            	stopButton.setStyle(off);
			            }
			        });

	}
}
