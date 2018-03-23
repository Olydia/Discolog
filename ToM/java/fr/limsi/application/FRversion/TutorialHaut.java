package fr.limsi.application.FRversion;

import java.io.PrintStream;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;



public class TutorialHaut extends Application {
	String situation;
	String username;
	@Override
	public void start(Stage chatStage) {

		chatStage.setTitle("Negotiator Agent");
		SplitPane sp = new SplitPane();
		sp.setOrientation(Orientation.VERTICAL);
		TextArea ta = new TextArea();
		//VBox vbox = new VBox(ta);
		FlowPane flow = new FlowPane(Orientation.VERTICAL);
		TutorialBas chatBoard= new TutorialBas();
		//Chat chat=new Chat();
		//Choice choice=new Choice();
		sp.getItems().addAll(flow,chatBoard);
		sp.setDividerPositions(0.3f);

		String image = TutorialHaut.class.getResource("white.jpg").toExternalForm();

		flow.setStyle("-fx-background-image: url('" + image + "'); "
				+
				"-fx-background-position: center center; " +
				"-fx-background-repeat: stretch;");
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		ta.setPrefSize(visualBounds.getWidth(), (visualBounds.getHeight()-5.0)/2);
		chatConsole console = new chatConsole(ta);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
		flow.getChildren().add(ta);
		Scene scene = new Scene(sp, visualBounds.getWidth(), visualBounds.getHeight());
		chatStage.setScene(scene);
		chatStage.setFullScreen(true);

		scene.getStylesheets().add
		(TutorialHaut.class.getResource("application2.css").toExternalForm());
		chatStage.show();
		chatBoard.addElements(username,situation,chatStage/*,chatBoard.interact(username)*/);

	}

	/*
	public static void main(String[] args) {
		launch(args);
	}*/
}
