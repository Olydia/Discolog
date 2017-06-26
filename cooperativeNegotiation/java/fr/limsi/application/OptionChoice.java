package fr.limsi.application;

import fr.limsi.negotiate.movie.*;
import fr.limsi.negotiate.restaurant.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class OptionChoice extends AnchorPane{

	public OptionChoice(){

	}

	public void addElements(String situation){
		/*The labels*/
		Label actionLabel = new Label("Choose the criterions of the "+situation);
		Label c1Label = new Label();
		Label c2Label = new Label();
		Label c3Label = new Label();
		Label c4Label = new Label();
		if (situation=="restaurant"){
			c1Label.setText("Atmosphere");
			c2Label.setText("Cost");
			c3Label.setText("Cuisine");
			c4Label.setText("Location");
		}
		else {
			c1Label.setText("Category");
			c2Label.setText("Country");
			c3Label.setText("Year");
		}

		/*The lists*/
		 ChoiceBox<String> cb1 = new ChoiceBox();
	        if (situation=="restaurant")
	        {cb1.setItems(FXCollections.observableArrayList(
	        		Atmosphere.LIVELY.values().toString(),"*"));

	        }
	        else {cb1.setItems(FXCollections.observableArrayList(
	        		Category.COMEDY.getValues().toString(),"*"));}
	        ChoiceBox<String> cb2 = new ChoiceBox();
	        if (situation=="restaurant")
	        {cb2.setItems(FXCollections.observableArrayList(
	        		Cost.EXPENSIVE.values().toString(),"*"));

	        }
	        else {cb2.setItems(FXCollections.observableArrayList(
	        		Country.FRANCE.getValues().toString(),"*"));}
	        ChoiceBox<String> cb3 = new ChoiceBox();
	        if (situation=="restaurant")
	        {cb3.setItems(FXCollections.observableArrayList(
	        		Cuisine.FRENCH.values().toString(),"*"));

	        }
	        else {cb3.setItems(FXCollections.observableArrayList(
	        		Year.THE_SEXTIES.getValues().toString(),"*"));}
	        ChoiceBox<String> cb4 = new ChoiceBox();
	        cb4.setItems(FXCollections.observableArrayList(
	        		Location.DOWNTOWN.getValues().toString(),"*"));

	    /*The positions*/

	    ObservableList<Node> list = getChildren();
	    setTopAnchor(actionLabel,100.0);
    	setLeftAnchor(actionLabel,100.0);
	    setTopAnchor(c1Label,150.0);
    	setLeftAnchor(c1Label,100.0);
    	setTopAnchor(c2Label,150.0);
    	setLeftAnchor(c2Label,100.0);
    	setTopAnchor(c3Label,150.0);
    	setLeftAnchor(c3Label,100.0);
    	setTopAnchor(c4Label,150.0);
    	setLeftAnchor(c4Label,100.0);

    	setTopAnchor(cb1,200.0);
     	setLeftAnchor(cb1,100.0);
     	setTopAnchor(cb2,200.0);
     	setLeftAnchor(cb2,100.0);
     	setTopAnchor(cb3,200.0);
     	setLeftAnchor(cb3,100.0);
     	setTopAnchor(cb4,200.0);
     	setLeftAnchor(cb4,100.0);
    	if (situation=="restaurant"){
    		list.addAll(cb1,cb2,cb3,cb4,c1Label,c2Label,c3Label,c4Label);
    	}
    	else list.addAll(cb1,cb2,cb3,c1Label,c2Label,c3Label);

    	/*Lists*/
    	ListView<String> options = new ListView<>();
    	ObservableList<String> items =FXCollections.observableArrayList (
    	    "Single", "Double", "Suite", "Family App");
    	options.setItems(items);


		/*Buttons*/

		Button filterButton = new Button();
		Button okButton = new Button();
		HBox filterHBox = new HBox(15);
		HBox okHBox = new HBox(15);
		filterHBox.getChildren().addAll(filterButton);
		okHBox.getChildren().addAll(okButton);

		filterButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent prosposeEvent) {

            }
        });
		okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent prosposeEvent) {

            }
        });

	}
}
