package fr.limsi.application.FRversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.restaurant.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class OptionChoice extends AnchorPane{
	String choice;

	public OptionChoice(){

	}
	/**
	 * Le but de cette methode est de g�n�rer la liste
	 *  des chaines de caracteres a partir des criteres
	 * @param c
	 * @return
	 */
	public ObservableList<String> getCriteriaList(Criterion[] c){
		ArrayList<String> l =new ArrayList<String>();
		for (int i=0;i<c.length;i++){

			l.add(c[i].toString().toUpperCase());
		}
		l.add("WHATEVER");
		ObservableList<String> la = FXCollections.observableArrayList(l);
		return la;
	}

	public int getIndex (ChoiceBox<String> cb){
		int d=0;
		for (int i=0;i<cb.getItems().size();i++){
			if (cb.getValue()==cb.getItems().get(i)){
				d=i;
			}
		}
		return d;
	}

	public void start(Stage optionStage) {
		optionStage.setTitle("Choose an option");
		optionStage.show();


		String image = OptionChoice.class.getResource("a.jpg").toExternalForm();

		setStyle("-fx-background-image: url('" + image + "'); "
				+
				"-fx-background-position: center center; " +
				"-fx-background-repeat: stretch;");
	}

	private int getNamePosition(String choice){

		int i=1;
		while (!((choice.substring(i-1, i)).equals(":"))&&(i<choice.length()))
		{
			//ch=ch+choice.substring(i-1, i);
			i=i+1;
			//System.out.println(choice.substring(i-1, i));

		}

		return i;
	}

	public void addElements(String situation, Stage optionStage,Stage chatStage,String[] details,ObservableList ChatList){
		/*The labels*/
		final Text errorMessage = new Text();

		errorMessage.setId("errorMessage");

		Label actionLabel = new Label("Choose the criteria of the "+situation);
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
		ChoiceBox<String> cb1 = new ChoiceBox<String>();
		
		cb1.setItems(getCriteriaList(Atmosphere.values()));
		
		ChoiceBox<String> cb2 = new ChoiceBox<String>();
	
		//Restaurant.values()[0].getCriteria().get(1)
		cb2.setItems(getCriteriaList(Cost.values()));

	
		ChoiceBox<String> cb3 = new ChoiceBox<String>();

		Cuisine[] c = Cuisine.values();
		for(int i =0; i< c.length; i++)
			//  	System.out.println(c[i]);
			cb3.setItems(getCriteriaList(c));



		ChoiceBox<String> cb4 = new ChoiceBox<String>();
		cb4.setItems(getCriteriaList(Location.values()));
		cb1.getSelectionModel().selectLast();
		cb2.getSelectionModel().selectLast();
		cb3.getSelectionModel().selectLast();
		cb4.getSelectionModel().selectLast();

		ListView<String> options = new ListView<>();
		options.setPrefWidth(getWidth()/3);
		options.setPrefHeight(getHeight()/3);
		ArrayList<String> optionsList =new ArrayList<String>();


		/*The positions*/

		ObservableList<Node> list = getChildren();
		setTopAnchor(actionLabel,100.0);
		setLeftAnchor(actionLabel,100.0);
		setTopAnchor(c1Label,150.0);
		setLeftAnchor(c1Label,100.0);
		setTopAnchor(c2Label,150.0);
		setLeftAnchor(c2Label,400.0);
		setTopAnchor(c3Label,150.0);
		setLeftAnchor(c3Label,700.0);
		setTopAnchor(c4Label,150.0);
		setLeftAnchor(c4Label,1000.0);

		setTopAnchor(cb1,200.0);
		setLeftAnchor(cb1,100.0);
		setTopAnchor(cb2,200.0);
		setLeftAnchor(cb2,400.0);
		setTopAnchor(cb3,200.0);
		setLeftAnchor(cb3,700.0);
		setTopAnchor(cb4,200.0);
		setLeftAnchor(cb4,1000.0);

		if (situation=="movie"){
			setLeftAnchor(cb1,250.0);
			setLeftAnchor(cb2,550.0);
			setLeftAnchor(cb3,850.0);
			setLeftAnchor(c1Label,250.0);
			setLeftAnchor(c2Label,550.0);
			setLeftAnchor(c3Label,850.0);
		}

		setTopAnchor(options,350.0);
		setLeftAnchor(options,410.0);

		if (situation=="restaurant"){
			list.addAll(actionLabel,cb1,cb2,cb3,cb4,c1Label,c2Label,c3Label,c4Label);
		}
		else list.addAll(actionLabel,cb1,cb2,cb3,c1Label,c2Label,c3Label);
		/*Buttons*/

		Button filterButton = new Button("filter");
		Button okButton = new Button("ok");
		Button retourButton = new Button("return");
		HBox filterHBox = new HBox(15);
		HBox okHBox = new HBox(15);
		HBox retourHBox = new HBox(15);
		filterHBox.getChildren().addAll(filterButton);
		//setTopAnchor(filterHBox,300.0);
		setTopAnchor(filterHBox,getHeight()* 0.275);
		setLeftAnchor(filterHBox,getWidth() * 0.4);
		okHBox.getChildren().addAll(okButton);
		setTopAnchor(okHBox,getHeight() -100);
		setLeftAnchor(okHBox,getWidth() * 0.4);
		retourHBox.getChildren().addAll(retourButton);
		setTopAnchor(retourHBox,getHeight() - 100);
		setLeftAnchor(retourHBox,getWidth() * 0.5);
		list.addAll(retourHBox,filterHBox);
		/* The options list */

		List<Criterion> values1=new ArrayList<Criterion>();
		List<Option> results1=new ArrayList<Option>();


		results1=getOptionWithValues(values1, Restaurant.values());

		for (Option o: results1){
			optionsList.add(o.toString() + ": " + o.print());
		}

		ObservableList<String> observableOptionsList1 = FXCollections.observableArrayList(optionsList);
		options.setItems(observableOptionsList1);
		list.addAll(options,okHBox);


		/* Defining the actions */

		filterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent filterEvent) {


				//list.remove(options);
				optionsList.clear();
				list.clear();
				List<Criterion> values=new ArrayList<Criterion>();


				if (cb1.getValue()!="WHATEVER"){
					values.add(Atmosphere.values()[getIndex(cb1)]);

				}

				if (cb2.getValue()!="WHATEVER"){
					values.add(Cost.values()[getIndex(cb2)]);

				}

				if (cb3.getValue()!="WHATEVER"){
					values.add(Cuisine.values()[getIndex(cb3)]);

				}

				if (cb4.getValue()!="WHATEVER") values.add(Location.values()[getIndex(cb4)]);

				List<Option> results=new ArrayList<Option>();


				results=getOptionWithValues(values, Restaurant.values());


				//values.add(Atmosphere.LIVELY);

				for (Option o: results){
					optionsList.add(o.toString() + ": " + o.print());
				}

				ObservableList<String> observableOptionsList = FXCollections.observableArrayList(optionsList);
				options.setItems(observableOptionsList);
				list.addAll(options,okHBox,filterHBox,retourHBox);
				if (situation=="restaurant"){
					list.addAll(actionLabel,cb1,cb2,cb3,cb4,c1Label,c2Label,c3Label,c4Label);
				}
				else list.addAll(actionLabel,cb1,cb2,cb3,c1Label,c2Label,c3Label);

			}
		});
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent prosposeEvent) {

				choice=options.getSelectionModel().getSelectedItem();
				if (choice==null){
					errorMessage.setText("You must choose an option or click on return");
					list.add(errorMessage);
					setTopAnchor(errorMessage,900.0);
					setLeftAnchor(errorMessage,650.0);
				}
				else{
					Label choiceLabel = new Label(choice);
					setTopAnchor(choiceLabel,270.0);
					setLeftAnchor(choiceLabel,350.0);
					ChatList.add(choiceLabel);
					if ((details[0].equals("AcceptPropose"))||(details[0].equals("RejectPropose"))){
						details[4]=situation.substring(0, 1).toUpperCase()+situation.substring(1);
						details[5]=choice.substring(0, getNamePosition(choice)-1);
					}
					details[3]=choice.substring(0, getNamePosition(choice)-1);
					//System.out.println(details[3]);
					chatStage.setFullScreen(true);
					optionStage.hide();
					chatStage.show();
				}

			}
		});

		retourButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent prosposeEvent) {
				//	Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();


				chatStage.setFullScreen(true);
				optionStage.hide();
				chatStage.show();

			}
		});

	}


	/**
	 *
	 * @param values : list des criteres choisis par l'utilisateur
	 * a noter que chaque critere a un type diff�rent
	 * @return
	 */
	public List<Option> getOptionWithValues(List<Criterion> values,  Option[] optionsValues) {

		ArrayList<Option> options = new ArrayList<>(Arrays.asList(optionsValues));
		for(Criterion c: values) {

			for (Iterator<Option> iterator = options.iterator(); iterator.hasNext(); ) {
				Option o = iterator.next();
				if(!o.getValue(c.getClass()).equals(c))
					iterator.remove();
			}
		}

		return options;

	}
}
