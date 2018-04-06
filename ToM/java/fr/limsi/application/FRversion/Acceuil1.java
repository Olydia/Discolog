package fr.limsi.application.FRversion;



import java.io.File;


import fr.limsi.application.FRversion.SaisiePref.dndTestFR.PrefAcceuil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class Acceuil1 extends Application {
	String username = " ";
	//WriteHistory writer;
	File history ;
	File powerUser;
	File dialogueTurn;
	public Acceuil1(/*Acceuil frame*/){
		//this.frame = frame;
		// writer = new WriteHistory();

	}

	@Override
	public void init(){
		
	}
	@Override
	public void start(Stage homeStage) {
		
		homeStage.setTitle("Negotiation Agent");
		AnchorPane pane = new AnchorPane();

		/*The welcome text*/
		Text sceneTitle = new Text("Welcome");
		sceneTitle.setId("welcome-text");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

		Scene scene = new Scene(pane, visualBounds.getWidth(), visualBounds.getHeight());
		homeStage.setScene(scene);
		homeStage.setFullScreen(true);
		
		scene.getStylesheets().add
		(Acceuil1.class.getResource("application2.css").toExternalForm());
		homeStage.show();


		/*The name label*/
		Label userName = new Label("The experience number:");

		userName.setId("label");

		/*The name box*/
		TextField userTextField = new TextField();

		final Tooltip tooltip = new Tooltip();
		tooltip.setText(
				"Write your name"
				);
		userTextField.setTooltip(tooltip);

		/*The model label*/
		Label pw = new Label("Situation:");
		pw.setId("label");
		/*The model box*/
		ChoiceBox<String> cb = new ChoiceBox<String>();
		cb.setItems(FXCollections.observableArrayList(
				"restaurant", "movie")
				);

		final Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
				"Choose the situation of the test"
				);
		cb.setTooltip(tooltip2);


		Button btn = new Button("submit");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		final Text errorMessage = new Text();

		errorMessage.setId("errorMessage");

		/*Positions*/

		pane.setTopAnchor(sceneTitle,200.0);

		pane.setLeftAnchor(sceneTitle,600.0);

		AnchorPane.setTopAnchor(userName,350.0);

		AnchorPane.setLeftAnchor(userName,300.0);

		AnchorPane.setTopAnchor(userTextField,400.0);
		AnchorPane.setBottomAnchor(userTextField,585.0);
		AnchorPane.setLeftAnchor(userTextField,280 + userName.getWidth());



		AnchorPane.setTopAnchor(hbBtn,550.0);

		AnchorPane.setLeftAnchor(hbBtn,800.0);

		AnchorPane.setTopAnchor(errorMessage,550.0);

		AnchorPane.setLeftAnchor(errorMessage,700.0);

		ObservableList<Node> list = pane.getChildren();

		list.addAll(sceneTitle,userName,userTextField,hbBtn);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				username=userTextField.getText();
				history = new File(System.getProperty("user.dir")+File.separator+"Participant"+
				username+".txt");
				
				String init = "\n"+ username + ";";
				File nbTours =  new File(System.getProperty("user.dir")+File.separator+"turnsdialogue.txt");
				File userPower =  new File(System.getProperty("user.dir")+File.separator+"power.txt");
				
				WriteHistory writer = new WriteHistory();
				
				writer.write(init, nbTours);
				writer.write(init, userPower);
				
				PrefAcceuil frame = new PrefAcceuil(username, history);
				Stage acceuil = new Stage();
				frame.start(acceuil);
            	homeStage.hide();     
			}
		
		});
		
	}

	
//	public void pause(){
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
	

	public static void main(String[] args) {
		Acceuil1 home = new Acceuil1();
		home.launch(args);

		

		
	}
	
	
}