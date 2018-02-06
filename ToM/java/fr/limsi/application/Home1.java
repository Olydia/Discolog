package fr.limsi.application;

import java.awt.Dimension;
import java.util.List;

import javax.swing.SwingUtilities;

import fr.limsi.application.SaisiePref.dndTest.Acceuil;
import fr.limsi.negotiate.Negotiation;
import fr.limsi.negotiate.Option;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
	String username;

	public Home1(){

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

		homeStage.setScene(scene);
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

		pane.setTopAnchor(sceneTitle,300.0);

		pane.setLeftAnchor(sceneTitle,600.0);

		AnchorPane.setTopAnchor(userName,400.0);

		AnchorPane.setLeftAnchor(userName,400.0);

		AnchorPane.setTopAnchor(pw,450.0);

		AnchorPane.setLeftAnchor(pw,400.0);

		AnchorPane.setTopAnchor(userTextField,400.0);
		AnchorPane.setBottomAnchor(userTextField,585.0);
		AnchorPane.setLeftAnchor(userTextField,650.0);

		AnchorPane.setTopAnchor(cb,450.0);

		AnchorPane.setLeftAnchor(cb,600.0);

		AnchorPane.setTopAnchor(hbBtn,500.0);

		AnchorPane.setLeftAnchor(hbBtn,800.0);

		AnchorPane.setTopAnchor(errorMessage,550.0);

		AnchorPane.setLeftAnchor(errorMessage,700.0);

		ObservableList list = pane.getChildren();

		list.addAll(sceneTitle/*,pw*/,userName,userTextField/*,cb*/,hbBtn);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Acceuil frame = new Acceuil();
				frame.setMinimumSize(new Dimension(200, 200));
				frame.pack();
				frame.setVisible(true);


				List<Negotiation<? extends Option>> negotiators = frame.getNegotiators();
				UpPrincipalScreen1 chat=new UpPrincipalScreen1();
				chat.setPrefModel(negotiators.get(0));
				Stage chatStage=new Stage();
				chat.username=userTextField.getText();
				chat.situation=/*(String) cb.getValue()*/"restaurant";
				chat.start(chatStage);

			}
		});

	}

	public static void main(String[] args) {


		launch(args);



	}
}