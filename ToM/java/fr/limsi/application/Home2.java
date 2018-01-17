package fr.limsi.application;

import fr.limsi.application.SaisiePref.dndTest.Acceuil;
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


public class Home2 extends Application {
	String username;
	public Home2(){

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
       // Scene scene = new Scene(pane, 400, 300);
        homeStage.setScene(scene);
        scene.getStylesheets().add
        (Home2.class.getResource("application2.css").toExternalForm());
        homeStage.show();
        UpPrincipalScreen2 chat=new UpPrincipalScreen2();
    	Stage chatStage=new Stage();

        /*The name label*/
        Label userName = new Label("The experience number");
        //grid.add(userName, 0, 1);
        userName.setId("label");

        /*The name box*/
        TextField userTextField = new TextField();
        //grid.add(userTextField, 1, 1);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText(
            "Write your name"
        );
        userTextField.setTooltip(tooltip);

        /*The model label*/
        Label pw = new Label("Situation:");
        //grid.add(pw, 0, 2);
        pw.setId("label");
        /*The model box*/
        ChoiceBox<String> cb = new ChoiceBox<String>();
        cb.setItems(FXCollections.observableArrayList(
            "restaurant", "movie")
        );
       // grid.add(cb, 1, 2);
        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText(
            "Choose the situation of the test"
        );
        cb.setTooltip(tooltip2);


        Button btn = new Button("submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
       // grid.add(hbBtn, 1, 4);
        final Text errorMessage = new Text();

        errorMessage.setId("errorMessage");

        /*Positions*/

      pane.setTopAnchor(sceneTitle,300.0);
        //setBottomAnchor(sceneTitle,200.0);
        pane.setLeftAnchor(sceneTitle,600.0);
        //setRightAnchor(sceneTitle,50.0);

        AnchorPane.setTopAnchor(userName,400.0);
        //setBottomAnchor(userName,200.0);
        AnchorPane.setLeftAnchor(userName,400.0);
        //setRightAnchor(userName,50.0);

        AnchorPane.setTopAnchor(pw,450.0);
        //setBottomAnchor(cb,200.0);
        AnchorPane.setLeftAnchor(pw,400.0);
        //setRightAnchor(cb,50.0);

        AnchorPane.setTopAnchor(userTextField,400.0);
        AnchorPane.setBottomAnchor(userTextField,585.0);
        AnchorPane.setLeftAnchor(userTextField,650.0);

        AnchorPane.setTopAnchor(cb,450.0);
        //setBottomAnchor(cb,200.0);
        AnchorPane.setLeftAnchor(cb,600.0);
        //setRightAnchor(cb,50.0);


        AnchorPane.setTopAnchor(hbBtn,500.0);
        //setBottomAnchor(cb,200.0);
        AnchorPane.setLeftAnchor(hbBtn,800.0);
        //setRightAnchor(cb,50.0);

        AnchorPane.setTopAnchor(errorMessage,550.0);
        //setBottomAnchor(cb,200.0);
        AnchorPane.setLeftAnchor(errorMessage,700.0);
        //setRightAnchor(cb,50.0);

        ObservableList list = pane.getChildren();

        list.addAll(sceneTitle/*,pw*/,userName,userTextField/*,cb*/,hbBtn);



        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            	Acceuil pref = new Acceuil();
            	pref.setVisible(true);
            	
            	chat.username=userTextField.getText();
            	chat.situation="restaurant";
            	chat.start(chatStage);
            	homeStage.hide();
            }
        });

    }

 public static void main(String[] args) {
	 launch(args);



    }
}