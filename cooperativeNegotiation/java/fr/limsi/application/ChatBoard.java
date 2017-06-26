package fr.limsi.application;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import edu.wpi.disco.*;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.ExampleAgent;
import fr.limsi.negotiate.movie.*;
import fr.limsi.negotiate.restaurant.*;
import fr.limsi.negotiate.restaurant.totalOrderedModels;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChatBoard extends AnchorPane{

	public String action;

	public ChatBoard(){

	}

	public Interaction interact(String username){
		 totalOrderedModels model = new totalOrderedModels();
		 //String[] args=null;
			ExampleAgent agent= new ExampleAgent("agent", model.model1());
			 Interaction interaction = new Interaction(
						new ExampleAgent("agent", model.model1()),
						new User(username),/*args.length > 0 && args[0].length() > 0 ? args[0] : */null);
				interaction.load("models/Negotiate.xml");
				((ExampleAgent) interaction.getSystem()).setRelation(0.9);

				// do not guess recipes, since using DecompositionPlugin below
				interaction.setGuess(false);
				// TODO: enable random choice among applicable utterances (disabled
				//       for now to make debugging easier
				// Agent.RANDOM = new Random(12345);
				interaction.start(true); // give user first turn


				return interaction;
	}

	public void clearLastButtons(ArrayList<HBox> c1HBoxs,ArrayList<HBox> c2HBoxs,ArrayList<HBox> c3HBoxs,ArrayList<HBox> c4HBoxs,ObservableList list,String situation){

		if (situation=="restaurant")
        {

			 for (int i=0;i<4;i++){
				 list.remove(c1HBoxs.get(i));


			 }

			 for (int i=0;i<3;i++){
				 list.remove(c2HBoxs.get(i));
			 }

			 for (int i=0;i<7;i++){
				 list.remove(c3HBoxs.get(i));
			 }

			 for (int i=0;i<5;i++){
				 list.remove(c4HBoxs.get(i));
			 }
        }
     else
     	{
	    	 for (int i=0;i<6;i++){
	    		 list.remove(c1HBoxs.get(i));
	    	 }
	    	 for (int i=0;i<5;i++){
	    		 list.remove(c2HBoxs.get(i));
			 }

			 for (int i=0;i<5;i++){
				 list.remove(c3HBoxs.get(i));

			 }


        }

	}


	public void addElements(String username, String situation,Stage chatStage,Interaction interaction){


		/*Connecting with Disco*/

		//String lien="C:\\Users\\dhouib\\AppData\\Local\\Temp\\Console.test";
		//Console cnsl=new Console(lien,interaction);
	  //  String cmd="fr.limsi.negotiate.lang.RejectState/createProposal(Packages.fr.limsi.negotiate.restaurant.Location.SOUTH_SIDE)/Packages.fr.limsi.negotiate.restaurant.Location.SOUTH_SIDE";
	    //cnsl.next(null);
	    //cnsl.execute("next");
	    //PrintStream discussion=new PrintStream();

	   // cnsl.execute(cmd);

	   //System.out.println(agent.getLastUtterance().format());
	 //   interaction.format(utterance)

	   // if ( !cnsl.getEngine().getStack().get(0).getChildren().isEmpty()) System.out.println();
	   //   if ( cnsl.getEngine().history(System.out) ) System.out.println();
	   //  if ( cnsl.getEngine().history(System.out) ) discussion=System.out;
	    // discussion.println();


	    String[] details = new String[8];
	    for (int i=0;i<8;i++){
	    	details[i]="";
	    }
		details[1]=situation;

		/*Labels*/
		Label actionLabel = new Label("What do you want to say "+username+"?");
		Label stopLabel = new Label("Are you sure that you want to stop the discussion?");
		Label acceptLabel = new Label("What do you want to accept?");
		Label proposeOptionLabel = new Label("What "+situation+" do you want to propose?  (Select the criteria to find the option you chose)");
		Label proposeCriterionLabel = new Label("What criterion do you want to propose?  (Select the criteria to find the option you chose)");
		Label stateLabel = new Label("What do you want to state?");
		Label rejectLabel = new Label("What do you want to reject?");
		Label proposeLabel = new Label("What do you want to propose?");

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

		Button proposeOptionButton = new Button("Option");
		Button proposeValueButton = new Button("Criterion");

		Button acceptOptionButton = new Button("Send");
		Button acceptValueButton = new Button("I want to propose");
		Button acceptProposeButton = new Button("Accept and counterpropose");

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

		Button optionButton = new Button("Option");
		Button criterionButton = new Button("Criterion");

		Button option2Button = new Button("Option");
		Button criterion2Button = new Button("Criterion");

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


		 if (situation=="restaurant")
	        {
			c1Button.setText("Atmosphere");
			c2Button.setText("Cost");
			c3Button.setText("Cuisine");
			c4Button.setText("Location");
			c12Button.setText("Atmosphere");
			c22Button.setText("Cost");
			c32Button.setText("Cuisine");
			c42Button.setText("Location");
			cG1Button.setText("Atmosphere");
			cG2Button.setText("Cost");
			cG3Button.setText("Cuisine");
			cG4Button.setText("Location");
	        }
		 else{
			 c1Button.setText("Category");
			c2Button.setText("Country");
			c3Button.setText("Year");
			c12Button.setText("Category");
			c22Button.setText("Country");
			c32Button.setText("Year");
			 cG1Button.setText("Category");
			cG2Button.setText("Country");
			cG3Button.setText("Year");

		 }


		 	Button co1Button = new Button();
			Button co2Button = new Button();
			Button co3Button = new Button();
			Button co4Button = new Button();
			Button co12Button = new Button();
			Button co22Button = new Button();
			Button co32Button = new Button();
			Button co42Button = new Button();


			 if (situation=="restaurant")
		        {
				co1Button.setText("Atmosphere");
				co2Button.setText("Cost");
				co3Button.setText("Cuisine");
				co4Button.setText("Location");
				co12Button.setText("Atmosphere");
				co22Button.setText("Cost");
				co32Button.setText("Cuisine");
				co42Button.setText("Location");
		        }
			 else{
				 co1Button.setText("Category");
				co2Button.setText("Country");
				co3Button.setText("Year");
				 co12Button.setText("Category");
				 co22Button.setText("Country");
				co32Button.setText("Year");
			 }




		Button sendButton = new Button("Send");

		//Button exitButton = new Button("Exit");

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


		 if (situation=="restaurant")
	        {

				 for (int i=0;i<4;i++){
					 Button iButton = new Button( Atmosphere.values()[i].toString());
					 Button i2Button = new Button( Atmosphere.values()[i].toString());
					 c1Buttons.add(iButton);
					 c12Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 HBox i2HBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 i2HBox.getChildren().addAll(i2Button);
					 c1HBoxs.add(iHBox);
					 c12HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<3;i++){
					 Button iButton = new Button( Cost.values()[i].toString());
					 Button i2Button = new Button( Cost.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c2Buttons.add(iButton);
					 c22Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c2HBoxs.add(iHBox);
					 c22HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<7;i++){
					 Button iButton = new Button( Cuisine.values()[i].toString());
					 Button i2Button = new Button( Cuisine.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c3Buttons.add(iButton);
					 c32Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c3HBoxs.add(iHBox);
					 c32HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<5;i++){
					 Button iButton = new Button( Location.values()[i].toString());
					 Button i2Button = new Button( Location.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c4Buttons.add(iButton);
					 c42Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c4HBoxs.add(iHBox);
					 c42HBoxs.add(i2HBox);
				 }
	        }
	     else
	     	{
		    	 for (int i=0;i<6;i++){
		    		 Button iButton = new Button( Category.values()[i].toString());
		    		 Button i2Button = new Button( Category.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c1Buttons.add(iButton);
					 c12Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c1HBoxs.add(iHBox);
					 c12HBoxs.add(i2HBox);
		    	 }
		    	 for (int i=0;i<5;i++){
					 Button iButton = new Button( Country.values()[i].toString());
					 Button i2Button = new Button( Country.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c2Buttons.add(iButton);
					 c22Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c2HBoxs.add(iHBox);
					 c22HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<5;i++){
					 Button iButton = new Button( Year.values()[i].toString());
					 Button i2Button = new Button( Year.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 c3Buttons.add(iButton);
					 c32Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 c3HBoxs.add(iHBox);
					 c32HBoxs.add(i2HBox);
				 }


	        }

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

		 if (situation=="restaurant")
	        {

				 for (int i=0;i<4;i++){
					 Button iButton = new Button( Atmosphere.values()[i].toString());
					 Button i2Button = new Button( Atmosphere.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co1Buttons.add(iButton);
					 co12Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co1HBoxs.add(iHBox);
					 co12HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<3;i++){
					 Button iButton = new Button( Cost.values()[i].toString());
					 Button i2Button = new Button( Cost.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co2Buttons.add(iButton);
					 co22Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co2HBoxs.add(iHBox);
					 co22HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<7;i++){
					 Button iButton = new Button( Cuisine.values()[i].toString());
					 Button i2Button = new Button( Cuisine.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co3Buttons.add(iButton);
					 co32Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co3HBoxs.add(iHBox);
					 co32HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<5;i++){
					 Button iButton = new Button( Location.values()[i].toString());
					 Button i2Button = new Button( Location.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co4Buttons.add(iButton);
					 co42Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co4HBoxs.add(iHBox);
					 co42HBoxs.add(i2HBox);
				 }
	        }
	     else
	     	{
		    	 for (int i=0;i<6;i++){
		    		 Button iButton = new Button( Category.values()[i].toString());
		    		 Button i2Button = new Button( Category.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co1Buttons.add(iButton);
					 co12Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co1HBoxs.add(iHBox);
					 co12HBoxs.add(i2HBox);
		    	 }
		    	 for (int i=0;i<5;i++){
					 Button iButton = new Button( Country.values()[i].toString());
					 Button i2Button = new Button(Country.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co2Buttons.add(iButton);
					 co22Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co2HBoxs.add(iHBox);
					 co22HBoxs.add(i2HBox);
				 }

				 for (int i=0;i<5;i++){
					 Button iButton = new Button( Year.values()[i].toString());
					 Button i2Button = new Button( Year.values()[i].toString());
					 HBox i2HBox = new HBox(15);
					 i2HBox.getChildren().addAll(i2Button);
					 co3Buttons.add(iButton);
					 co32Buttons.add(i2Button);
					 HBox iHBox = new HBox(15);
					 iHBox.getChildren().addAll(iButton);
					 co3HBoxs.add(iHBox);
					 co32HBoxs.add(i2HBox);
				 }


	        }

		HBox proposeHBox = new HBox(15);
		HBox acceptHBox = new HBox(15);
		HBox rejectHBox = new HBox(15);
		HBox stateAskHBox = new HBox(15);
		HBox stopHBox = new HBox(15);

		HBox proposeOptionHBox = new HBox(15);
		HBox proposeValueHBox = new HBox(15);

		HBox acceptOptionHBox = new HBox(15);
		HBox acceptValueHBox = new HBox(15);
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

		HBox option2HBox= new HBox(15);
		HBox criterion2HBox= new HBox(15);

		HBox cG1HBox = new HBox(15);
		HBox cG2HBox = new HBox(15);
		HBox cG3HBox = new HBox(15);
		HBox cG4HBox = new HBox(15);

		HBox c1HBox = new HBox(15);
		HBox c2HBox = new HBox(15);
		HBox c3HBox = new HBox(15);
		HBox c4HBox = new HBox(15);

		HBox co1HBox = new HBox(15);
		HBox co2HBox = new HBox(15);
		HBox co3HBox = new HBox(15);
		HBox co4HBox = new HBox(15);

		HBox c12HBox = new HBox(15);
		HBox c22HBox = new HBox(15);
		HBox c32HBox = new HBox(15);
		HBox c42HBox = new HBox(15);

		HBox co12HBox = new HBox(15);
		HBox co22HBox = new HBox(15);
		HBox co32HBox = new HBox(15);
		HBox co42HBox = new HBox(15);

		HBox sendHBox = new HBox(15);
		//HBox exitHBox = new HBox(15);

		proposeHBox.getChildren().addAll(proposeButton);
		acceptHBox.getChildren().addAll(acceptButton);
		rejectHBox.getChildren().addAll(rejectButton);
		stateAskHBox.getChildren().addAll(stateAskButton);
		stopHBox.getChildren().addAll(stopButton);

		proposeOptionHBox.getChildren().addAll(proposeOptionButton);
		proposeValueHBox.getChildren().addAll(proposeValueButton);

		acceptOptionHBox.getChildren().addAll(acceptOptionButton);
		acceptValueHBox.getChildren().addAll(acceptValueButton);
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

		option2HBox.getChildren().addAll(option2Button);
		criterion2HBox.getChildren().addAll(criterion2Button);

		cG1HBox.getChildren().addAll(cG1Button);
		cG2HBox.getChildren().addAll(cG2Button);
		cG3HBox.getChildren().addAll(cG3Button);
		cG4HBox.getChildren().addAll(cG4Button);

		c1HBox.getChildren().addAll(c1Button);
		c2HBox.getChildren().addAll(c2Button);
		c3HBox.getChildren().addAll(c3Button);
		c4HBox.getChildren().addAll(c4Button);

		co1HBox.getChildren().addAll(co1Button);
		co2HBox.getChildren().addAll(co2Button);
		co3HBox.getChildren().addAll(co3Button);
		co4HBox.getChildren().addAll(co4Button);

		c12HBox.getChildren().addAll(c12Button);
		c22HBox.getChildren().addAll(c22Button);
		c32HBox.getChildren().addAll(c32Button);
		c42HBox.getChildren().addAll(c42Button);

		co12HBox.getChildren().addAll(co12Button);
		co22HBox.getChildren().addAll(co22Button);
		co32HBox.getChildren().addAll(co32Button);
		co42HBox.getChildren().addAll(co42Button);

		sendHBox.getChildren().addAll(sendButton);
	//	exitHBox.getChildren().addAll(exitButton);

		/*The positions*/
		   setTopAnchor(actionLabel,10.0);
		   setLeftAnchor(actionLabel,100.0);


		   setTopAnchor(acceptLabel,100.0);
		   setLeftAnchor(acceptLabel,100.0);

		   setTopAnchor(rejectLabel,100.0);
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

		   setTopAnchor(sendHBox,10.0);
		   setLeftAnchor(sendHBox,1100.0);

		  // setTopAnchor(exitHBox,450.0);
		   //setLeftAnchor(exitHBox,1110.0);

		   list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);


		   /*The actions*/

		   String on=" -fx-background-color: linear-gradient(#2A5058, #61a2b1);";
		   String off=" -fx-background-color: linear-gradient(#61a2b1, #2A5058);";


		   proposeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.clear();
	     		   list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);

	            	proposeButton.setStyle(on);
	            	acceptButton.setStyle(off);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);

	            	answer1.setText("Let's go to ");
	            	answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	setTopAnchor(proposeOptionHBox,100.0);
	            	setLeftAnchor(proposeOptionHBox,450.0);

	     		   	setTopAnchor(proposeValueHBox ,100.0);
	     		   	setLeftAnchor(proposeValueHBox ,750.0);


	     		   list.addAll(proposeOptionHBox,proposeValueHBox);
	            }
	        });


		   acceptButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	list.clear();
		     		list.addAll( acceptLabel,actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);

	            	proposeButton.setStyle(off);
	            	acceptButton.setStyle(on);
	            	rejectButton.setStyle(off);
	            	stateAskButton.setStyle(off);
	            	stopButton.setStyle(off);

	            	answer1.setText("Okay, let's go to ");
	            	answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	setTopAnchor(acceptOptionHBox,250.0);
	            	setLeftAnchor(acceptOptionHBox,400.0);


	     		  	setTopAnchor(acceptProposeHBox ,250.0);
	     		   	setLeftAnchor(acceptProposeHBox ,750.0);


	     		   	list.addAll(acceptOptionHBox,acceptValueHBox,acceptProposeHBox );
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


	            	list.clear();
		     		list.addAll(rejectLabel, actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);


	            	setTopAnchor(rejectOnlyHBox,250.0);
	            	setLeftAnchor(rejectOnlyHBox,250.0);

	     		   	setTopAnchor(rejectStateHBox ,250.0);
	     		   	setLeftAnchor(rejectStateHBox ,550.0);

	     		  	setTopAnchor(rejectProposeHBox ,250.0);
	     		   	setLeftAnchor(rejectProposeHBox ,850.0);



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
	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);

	            	setTopAnchor(stateHBox,100.0);
	            	setLeftAnchor(stateHBox,250.0);

	     		   	setTopAnchor(askGeneralHBox ,100.0);
	     		   	setLeftAnchor(askGeneralHBox ,500.0);

	     		  	setTopAnchor(askSpecificHBox ,100.0);
	     		   	setLeftAnchor(askSpecificHBox ,850.0);



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
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);


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



	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox,sendHBox);
		     		list.addAll(proposeOptionHBox,proposeValueHBox);

		     		answer1.setText("Let's go to the ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	setTopAnchor(proposeOptionLabel,150.0);
	            	setLeftAnchor(proposeOptionLabel,100.0);

	            	setTopAnchor(co1HBox,200.0);
	            	setTopAnchor(co2HBox,200.0);
	            	setTopAnchor(co3HBox,200.0);
	            	setTopAnchor(co4HBox,200.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(co1HBox,300.0);
		            	setLeftAnchor(co2HBox,500.0);
		            	setLeftAnchor(co3HBox,700.0);
		            	setLeftAnchor(co4HBox,900.0);
		            	list.addAll(co1HBox,co2HBox,co3HBox,co4HBox);
		    	        }
	            	else{
	            		setLeftAnchor(co1HBox,350.0);
	            		setLeftAnchor(co2HBox,600.0);
	            		setLeftAnchor(co3HBox,850.0);
	            		list.addAll(co1HBox,co2HBox,co3HBox);
	            		}

	            	list.addAll(proposeOptionLabel);
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

		     		answer1.setText("Let's go to a ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");
		     		setTopAnchor(proposeCriterionLabel,150.0);
		     		setLeftAnchor(proposeCriterionLabel,100.0);

	            	setTopAnchor(c1HBox,200.0);
	            	setTopAnchor(c2HBox,200.0);
	            	setTopAnchor(c3HBox,200.0);
	            	setTopAnchor(c4HBox,200.0);

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
	            	list.addAll(proposeCriterionLabel);


	            }
	        });

		   acceptValueButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="Accept";
	            	acceptValueButton.setStyle(on);
	            	acceptOptionButton.setStyle(off);
	            	acceptProposeButton.setStyle(off);
	            	list.clear();
		     		list.addAll( acceptLabel,actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(acceptOptionHBox,acceptValueHBox,acceptProposeHBox);

		     		answer1.setText(answer1.getText()+"Okay, let's go to  a ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	setTopAnchor(c1HBox,200.0);
	            	setTopAnchor(c2HBox,200.0);
	            	setTopAnchor(c3HBox,200.0);
	            	setTopAnchor(c4HBox,200.0);

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
	        });


		   acceptOptionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="Accept";
	            	acceptValueButton.setStyle(off);
	            	acceptOptionButton.setStyle(on);
	            	acceptProposeButton.setStyle(off);

		     		answer1.setText(answer1.getText()+"Okay, let's go to  the ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(acceptOptionHBox,acceptValueHBox,acceptProposeHBox);

	            	setTopAnchor(co1HBox,200.0);
	            	setTopAnchor(co2HBox,200.0);
	            	setTopAnchor(co3HBox,200.0);
	            	setTopAnchor(co4HBox,200.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(co1HBox,300.0);
		            	setLeftAnchor(co2HBox,500.0);
		            	setLeftAnchor(co3HBox,700.0);
		            	setLeftAnchor(co4HBox,900.0);
		            	list.addAll(co1HBox,co2HBox,co3HBox,co4HBox);
		    	        }
	            	else{
	            		setLeftAnchor(co1HBox,350.0);
	            		setLeftAnchor(co2HBox,600.0);
	            		setLeftAnchor(co3HBox,850.0);
	            		list.addAll(co1HBox,co2HBox,co3HBox);
	            		}

	            	list.add(acceptLabel);

	            }
	        });

		   acceptProposeButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="AcceptPropose";

	            	acceptValueButton.setStyle(off);
	            	acceptOptionButton.setStyle(off);
	            	acceptProposeButton.setStyle(on);

		     		answer1.setText("Okay, let's go to  the ");
		     		answer2.setText("");
	            	answer3.setText("");
	            	answer4.setText("");

	            	list.clear();
		     		list.addAll( actionLabel,proposeHBox,acceptHBox,rejectHBox,stateAskHBox,stopHBox);
		     		list.addAll(acceptOptionHBox,acceptValueHBox,acceptProposeHBox);

	            	setTopAnchor(c1HBox,200.0);
	            	setTopAnchor(c2HBox,200.0);
	            	setTopAnchor(c3HBox,200.0);
	            	setTopAnchor(c4HBox,200.0);

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

	            	setTopAnchor(proposeOptionLabel,300.0);
	            	setLeftAnchor(proposeOptionLabel,100.0);

	            	setTopAnchor(co1HBox,350.0);
	            	setTopAnchor(co2HBox,350.0);
	            	setTopAnchor(co3HBox,350.0);
	            	setTopAnchor(co4HBox,350.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(co1HBox,300.0);
		            	setLeftAnchor(co2HBox,500.0);
		            	setLeftAnchor(co3HBox,700.0);
		            	setLeftAnchor(co4HBox,900.0);
		            	list.addAll(co1HBox,co2HBox,co3HBox,co4HBox);
		    	        }
	            	else{
	            		setLeftAnchor(co1HBox,350.0);
	            		setLeftAnchor(co2HBox,600.0);
	            		setLeftAnchor(co3HBox,850.0);
	            		list.addAll(co1HBox,co2HBox,co3HBox);
	            		}

	            	list.addAll(acceptLabel);
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



	     		   	setTopAnchor(criterionHBox ,150.0);
	     		   	setLeftAnchor(criterionHBox ,530.0);

	     		  	setTopAnchor(optionHBox ,150.0);
	     		   	setLeftAnchor(optionHBox ,730.0);

	     		   list.addAll(optionHBox,criterionHBox);
	     		  list.addAll(rejectLabel);

	            }
	        });


		   rejectStateButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	details[0]="RejectState";

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

	            	setTopAnchor(criterionHBox ,150.0);
	     		   	setLeftAnchor(criterionHBox ,530.0);

	     		  	setTopAnchor(optionHBox ,150.0);
	     		   	setLeftAnchor(optionHBox ,730.0);


	             	setTopAnchor(stateLabel,300.0);
	            	setLeftAnchor(stateLabel,100.0);

	            	setTopAnchor(criterion2HBox ,300.0);
	     		   	setLeftAnchor(criterion2HBox ,530.0);

	     		  	setTopAnchor(option2HBox ,300.0);
	     		   	setLeftAnchor(option2HBox ,730.0);

	     		   list.addAll(optionHBox,criterionHBox,option2HBox,criterion2HBox);
	     		  list.addAll(rejectLabel,stateLabel);
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

	            	setTopAnchor(criterionHBox ,150.0);
	     		   	setLeftAnchor(criterionHBox ,530.0);

	     		  	setTopAnchor(optionHBox ,150.0);
	     		   	setLeftAnchor(optionHBox ,730.0);


	              	setTopAnchor(proposeLabel,300.0);
	            	setLeftAnchor(proposeLabel,100.0);

	            	setTopAnchor(criterion2HBox ,300.0);
	     		   	setLeftAnchor(criterion2HBox ,530.0);

	     		  	setTopAnchor(option2HBox ,300.0);
	     		   	setLeftAnchor(option2HBox ,730.0);

	     		   list.addAll(optionHBox,criterionHBox,option2HBox,criterion2HBox);
	     		  list.addAll(rejectLabel,proposeLabel);
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

	            	setTopAnchor(likeHBox,200.0);
	            	setTopAnchor(dontLikeHBox,200.0);
	            	setLeftAnchor(likeHBox,500.0);
	            	setLeftAnchor(dontLikeHBox,700.0);

	            	list.addAll(likeHBox,dontLikeHBox);

	            	setTopAnchor(c1HBox,250.0);
	            	setTopAnchor(c2HBox,250.0);
	            	setTopAnchor(c3HBox,250.0);
	            	setTopAnchor(c4HBox,250.0);

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

	            	setTopAnchor(cG1HBox,200.0);
	            	setTopAnchor(cG2HBox,200.0);
	            	setTopAnchor(cG3HBox,200.0);
	            	setTopAnchor(cG4HBox,200.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(cG1HBox,300.0);
		            	setLeftAnchor(cG2HBox,500.0);
		            	setLeftAnchor(cG3HBox,700.0);
		            	setLeftAnchor(cG4HBox,900.0);
		            	list.addAll(cG1HBox,cG2HBox,cG3HBox,cG4HBox);
		    	        }
	            	else{
	            		setLeftAnchor(cG1HBox,350.0);
	            		setLeftAnchor(cG2HBox,600.0);
	            		setLeftAnchor(cG3HBox,850.0);
	            		list.addAll(cG1HBox,cG2HBox,cG3HBox);
	            		}


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
	            	setTopAnchor(c1HBox,200.0);
	            	setTopAnchor(c2HBox,200.0);
	            	setTopAnchor(c3HBox,200.0);
	            	setTopAnchor(c4HBox,200.0);

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
	        });

		  noButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	noButton.setStyle(on);
	            	yesButton.setStyle(off);
	            	list.clear();
	            	list.addAll(acceptButton,rejectButton,proposeButton,stateButton,stopButton,actionLabel);
	            	//list.removeAll(stateButton,askGeneralButton,askSpecificButton,proposeValueButton,proposeOptionButton,rejectOnlyHBox,rejectStateHBox,rejectProposeHBox ,stopLabel,noHBox,yesHBox,c1HBox,c2HBox,c3HBox,c4HBox);

	            	//list.removeAll(co1Button, co2Button, co3Button,co4Button,c1Button,c2Button,c3Button,c4Button,acceptCriterionLabel,acceptOptionLabel,proposeCriterionLabel,proposeOptionLabel,rejectOptionLabel,rejectCriterionLabel);

	            }
	        });

		  yesButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	//list.removeAll(stateButton,askGeneralButton,askSpecificButton,proposeValueButton,proposeOptionButton,rejectOnlyHBox,rejectStateHBox,rejectProposeHBox ,stopLabel,noHBox,yesHBox,c1HBox,c2HBox,c3HBox,c4HBox);

	            	//list.removeAll(co1Button, co2Button, co3Button,co4Button,c1Button,c2Button,c3Button,c4Button,acceptCriterionLabel,acceptOptionLabel,proposeCriterionLabel,proposeOptionLabel,rejectOptionLabel,rejectCriterionLabel);

	            	yesButton.setStyle(on);
	            	noButton.setStyle(off);

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

	            	list.removeAll(c1HBox,c2HBox,c3HBox,c4HBox,co1HBox,co2HBox,co3HBox,co4HBox);
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);

	            	setTopAnchor(co1HBox,200.0);
	            	setTopAnchor(co2HBox,200.0);
	            	setTopAnchor(co3HBox,200.0);
	            	setTopAnchor(co4HBox,200.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(co1HBox,300.0);
		            	setLeftAnchor(co2HBox,500.0);
		            	setLeftAnchor(co3HBox,700.0);
		            	setLeftAnchor(co4HBox,900.0);
		            	list.addAll(co1HBox,co2HBox,co3HBox,co4HBox);
		    	        }
	            	else{
	            		setLeftAnchor(co1HBox,350.0);
	            		setLeftAnchor(co2HBox,600.0);
	            		setLeftAnchor(co3HBox,850.0);
	            		list.addAll(co1HBox,co2HBox,co3HBox);
	            		}


	            }
	        });

		  criterionButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	criterionButton.setStyle(on);
	            	optionButton.setStyle(off);

	            	list.removeAll(c1HBox,c2HBox,c3HBox,c4HBox,co1HBox,co2HBox,co3HBox,co4HBox);
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);
	            	clearLastButtons(c1HBoxs,c2HBoxs,c3HBoxs,c4HBoxs,list,situation);

	            	setTopAnchor(c1HBox,200.0);
	            	setTopAnchor(c2HBox,200.0);
	            	setTopAnchor(c3HBox,200.0);
	            	setTopAnchor(c4HBox,200.0);

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
	        });

		  option2Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	option2Button.setStyle(on);
	            	criterion2Button.setStyle(off);

	            	list.removeAll(c12HBox,c22HBox,c32HBox,c42HBox,co12HBox,co22HBox,co32HBox,co42HBox);
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);

	            	setTopAnchor(co12HBox,350.0);
	            	setTopAnchor(co22HBox,350.0);
	            	setTopAnchor(co32HBox,350.0);
	            	setTopAnchor(co42HBox,350.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(co12HBox,300.0);
		            	setLeftAnchor(co22HBox,500.0);
		            	setLeftAnchor(co32HBox,700.0);
		            	setLeftAnchor(co42HBox,900.0);
		            	list.addAll(co12HBox,co22HBox,co32HBox,co42HBox);
		    	        }
	            	else{
	            		setLeftAnchor(co12HBox,350.0);
	            		setLeftAnchor(co22HBox,600.0);
	            		setLeftAnchor(co32HBox,850.0);
	            		list.addAll(co12HBox,co22HBox,co32HBox);
	            		}


	            }
	        });

		  criterion2Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {

	            	criterion2Button.setStyle(on);
	            	option2Button.setStyle(off);
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);
	            	list.removeAll(co12HBox,co22HBox,co32HBox,co42HBox,c12HBox,c22HBox,c32HBox,c42HBox);
	            	clearLastButtons(c12HBoxs,c22HBoxs,c32HBoxs,c42HBoxs,list,situation);


	            	setTopAnchor(c12HBox,350.0);
	            	setTopAnchor(c22HBox,350.0);
	            	setTopAnchor(c32HBox,350.0);
	            	setTopAnchor(c42HBox,350.0);

	            	if (situation=="restaurant")
		    	        {
		            	setLeftAnchor(c12HBox,300.0);
		            	setLeftAnchor(c22HBox,500.0);
		            	setLeftAnchor(c32HBox,700.0);
		            	setLeftAnchor(c42HBox,900.0);
		            	list.addAll(c12HBox,c22HBox,c32HBox,c42HBox);
		    	        }
	            	else{
	            		setLeftAnchor(c12HBox,350.0);
	            		setLeftAnchor(c22HBox,600.0);
	            		setLeftAnchor(c32HBox,850.0);
	            		list.addAll(c12HBox,c22HBox,c32HBox);
	            		}


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
	            	 if (situation=="restaurant")
	     	        {

	     				 for (int i=0;i<4;i++){
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
	              	details[2]=cG4Button.getText();
	            	 cG3Button.setStyle(off);
	            	 cG1Button.setStyle(off);
	            	 cG2Button.setStyle(off);
	            	 cG4Button.setStyle(on);
	            }

	        });







		  co1Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);
	            	if (co1Button.getStyle()==on){ co1Button.setStyle(off);}
	            	else { co1Button.setStyle(on);}

	            	 if (situation=="restaurant")
	     	        {

	     				 for (int i=0;i<4;i++){
	     					setTopAnchor(co1HBoxs.get(i),getTopAnchor(co1HBox)+50.0);
	     					setLeftAnchor(co1HBoxs.get(i),300.0+200.0*i);
	     					list.add(co1HBoxs.get(i));
	     				 }

	     	        }
	            	 else{
	            		 for (int i=0;i<6;i++){
		     					setTopAnchor(co1HBoxs.get(i),getTopAnchor(co1HBox)+50.0);
		     					setLeftAnchor(co1HBoxs.get(i),200.0+150.0*i);
		     					list.add(co1HBoxs.get(i));
	            		 }
	            	 }
	            }
	        });

		  co2Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);

	            	if (co2Button.getStyle()==on){ co2Button.setStyle(off);}
	            	else { co2Button.setStyle(on);}

	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<3;i++){
		     					setTopAnchor(co2HBoxs.get(i),getTopAnchor(co2HBox)+50.0);
		     					setLeftAnchor(co2HBoxs.get(i),300.0+250.0*i);
		     					list.add(co2HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(co2HBoxs.get(i),getTopAnchor(co2HBox)+50.0);
			     					setLeftAnchor(co2HBoxs.get(i),200.0+150.0*i);
			     					list.add(co2HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  co3Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);

	            	if (co3Button.getStyle()==on){ co3Button.setStyle(off);}
	            	else { co3Button.setStyle(on);}

	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<7;i++){
		     					setTopAnchor(co3HBoxs.get(i),getTopAnchor(co3HBox)+50.0);
		     					setLeftAnchor(co3HBoxs.get(i),150.0+150.0*i);
		     					list.add(co3HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(co3HBoxs.get(i),getTopAnchor(co3HBox)+50.0);
			     					setLeftAnchor(co3HBoxs.get(i),200.0+150.0*i);
			     					list.add(co3HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  co4Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co1HBoxs,co2HBoxs,co3HBoxs,co4HBoxs,list,situation);

	            	if (co4Button.getStyle()==on){ co4Button.setStyle(off);}
	            	else { co4Button.setStyle(on);}


		     				 for (int i=0;i<5;i++){
		     					setTopAnchor(co4HBoxs.get(i),getTopAnchor(co4HBox)+50.0);
		     					setLeftAnchor(co4HBoxs.get(i),200.0+200.0*i);
		     					list.add(co4HBoxs.get(i));
		     				 }
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
		     				 for (int i=0;i<5;i++){
		     					setTopAnchor(c42HBoxs.get(i),getTopAnchor(c42HBox)+50.0);
		     					setLeftAnchor(c42HBoxs.get(i),200.0+200.0*i);
		     					list.add(c42HBoxs.get(i));
		     				 }
		            	 }

	        });






		  co12Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);

	            	if (co12Button.getStyle()==on){ co12Button.setStyle(off);}
	            	else { co12Button.setStyle(on);}

	            	 if (situation=="restaurant")
	     	        {

	     				 for (int i=0;i<4;i++){
	     					setTopAnchor(co12HBoxs.get(i),getTopAnchor(co12HBox)+50.0);
	     					setLeftAnchor(co12HBoxs.get(i),300.0+200.0*i);
	     					list.add(co12HBoxs.get(i));
	     				 }

	     	        }
	            	 else{
	            		 for (int i=0;i<6;i++){
		     					setTopAnchor(co12HBoxs.get(i),getTopAnchor(co12HBox)+50.0);
		     					setLeftAnchor(co12HBoxs.get(i),200.0+150.0*i);
		     					list.add(co12HBoxs.get(i));
	            		 }
	            	 }
	            }
	        });

		  co22Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);

	            	if (co22Button.getStyle()==on){ co22Button.setStyle(off);}
	            	else { co22Button.setStyle(on);}
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<3;i++){
		     					setTopAnchor(co22HBoxs.get(i),getTopAnchor(co22HBox)+50.0);
		     					setLeftAnchor(co22HBoxs.get(i),300.0+250.0*i);
		     					list.add(co22HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(co22HBoxs.get(i),getTopAnchor(co22HBox)+50.0);
			     					setLeftAnchor(co22HBoxs.get(i),200.0+150.0*i);
			     					list.add(co22HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  co32Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);

	            	if (co32Button.getStyle()==on){ co32Button.setStyle(off);}
	            	else { co32Button.setStyle(on);}
	            	 if (situation=="restaurant")
		     	        {

		     				 for (int i=0;i<7;i++){
		     					setTopAnchor(co32HBoxs.get(i),getTopAnchor(co32HBox)+50.0);
		     					setLeftAnchor(co32HBoxs.get(i),150.0+150.0*i);
		     					list.add(co32HBoxs.get(i));
		     				 }

		     	        }
		            	 else{
		            		 for (int i=0;i<5;i++){
			     					setTopAnchor(co32HBoxs.get(i),getTopAnchor(co32HBox)+50.0);
			     					setLeftAnchor(co32HBoxs.get(i),200.0+150.0*i);
			     					list.add(co32HBoxs.get(i));
		            		 }
		            	 }

	            }
	        });

		  co42Button.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent prosposeEvent) {
	            	clearLastButtons(co12HBoxs,co22HBoxs,co32HBoxs,co42HBoxs,list,situation);

	            	if (co42Button.getStyle()==on){ co42Button.setStyle(off);}
	            	else { co42Button.setStyle(on);}


		     				 for (int i=0;i<5;i++){
		     					setTopAnchor(co42HBoxs.get(i),getTopAnchor(co42HBox)+50.0);
		     					setLeftAnchor(co42HBoxs.get(i),200.0+200.0*i);
		     					list.add(co42HBoxs.get(i));
		     				 }
		            	 }

	        });
		/*  exitButton.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent exitEvent) {

		     				 chatStage.hide();
		            	 }

	        });
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
	        		else if ((details[0]=="Propose")||(details[0]=="Accept")||(details[0]=="Reject")){
	        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
	        		}
	        		else if (details[0]=="RejectState"){
	        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
	        			command2="Packages.fr.limsi.negotiate."+details[1]+"."+details[4]+"."+details[5];
	        		}
	        		else {
	        			command1="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[2]+"."+details[3]+")";
	        			command2="createProposal(Packages.fr.limsi.negotiate."+details[1]+"."+details[4]+"."+details[5]+")";
	        		}
	        		command="fr.limsi.negotiate.lang."+details[0]+"/"+command1;
	        		if (command2!="") command=command+"/"+command2;
	        		//Reste à vérifier le cas de DOMINANT
	        		//String cmd="fr.limsi.negotiate.lang.Propose/createProposal(Packages.fr.limsi.negotiate.restaurant.Location.NORTH_SIDE)";
	        		//System.out.println(command);
	        		interaction.getConsole().execute(command);
	        		boolean guess = interaction.getProperty("interaction@guess", interaction.isGuess());
	        		interaction.getSystem().respond(interaction, false, guess);

		            	 }

	        });

		  /*Defining the actions of the button arrays*/


				 for (int i=0;i<c1Buttons.size();i++){
					 String cr1=c1Buttons.get(i).getText();
					 String cr12=c12Buttons.get(i).getText();
					 c1Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {

				     				 details[3]= cr1;
				            	 	}

							 	});

					 c12Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {

					     				details[5]=cr12;
					            	 	}

								 	});

				 }

				 for (int i=0;i<c2Buttons.size();i++){
					 String cr2=c2Buttons.get(i).getText();
					 String cr22=c22Buttons.get(i).getText();
					 c2Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {

				     				 details[3]=cr2;
				            	 	}

							 	});

					 c22Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {

					     				 details[5]=cr22;
					            	 	}

								 	});

				 }


				 for (int i=0;i<c3Buttons.size();i++){
					 String cr3=c3Buttons.get(i).getText();
					 String cr32=c32Buttons.get(i).getText();
					 c3Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {

				     				 details[3]=cr3;
				            	 	}

							 	});

					 c32Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {

					     				 details[5]=cr32;
					            	 	}

								 	});

				 }

				 for (int i=0;i<c4Buttons.size();i++){
					 String cr4=c4Buttons.get(i).getText();
					 String cr42=c42Buttons.get(i).getText();
					 c4Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

			            @Override
			            public void handle(ActionEvent exitEvent) {
			            			list.add(sendHBox);
				     				details[3]=cr4;
				            	 	}

							 	});

					 c42Buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {

				            @Override
				            public void handle(ActionEvent exitEvent) {
				            			list.add(sendHBox);
					     				details[5]=cr42;
					            	 	}

								 	});

				 }

	}
}
