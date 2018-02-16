package fr.limsi.application;

import java.io.File;
import java.util.List;

import fr.limsi.application.SaisiePref.dndTest.Acceuil;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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



public class Home1 extends Application {
	String username = "1";
	//WriteHistory writer;
	File history ;
	public Home1(/*Acceuil frame*/){
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
		(Home1.class.getResource("application2.css").toExternalForm());
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
				
				Acceuil frame = new Acceuil(username, history);
				Stage acceuil = new Stage();
				frame.start(acceuil);
            	homeStage.hide();     
            	
            	Task<Void> task = new Task<Void>() {
            	    @Override public Void call() {
            	    	while (!Acceuil.isDone()){
            	    		try {
            	    			Thread.sleep(200);
            	    		} catch (InterruptedException e1) {
            	    			// TODO Auto-generated catch block
            	    			e1.printStackTrace();
            	    		}
            	    	}
            	        return null;
            	    }
            	};
            	
            	 task.setOnSucceeded(elem -> {
     		        // update UI with result
            		 acceuil.hide();
            		 frame.startAgents();
     		    });
            	Thread aa = new Thread(task);
            	aa.start();
            	try {
					aa.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
        		
    
        		
        		
//        		
			}
		
		});
		
	}

	
	public void pause(){
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		Home1 home = new Home1();
		home.launch(args);

		

		
	}
	
	
}