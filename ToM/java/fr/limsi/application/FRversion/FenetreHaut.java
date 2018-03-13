package fr.limsi.application.FRversion;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.ToM.ProbalisticModel.ToMNegotiatorProba.ADAPT;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;



public class FenetreHaut extends Application {
	public static int nbAgents = -1;
	public String situation;
	private String username;
	ADAPT state;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	private String agentName;
	private Negotiation<? extends Option> prefModel;
	 private FenetreBas chatBoard;
	 
	 
	public FenetreHaut(String agentName, String userName, ADAPT state){
		this.agentName = agentName;
		this.username = userName;
		this.state = state;
		nbAgents ++;
	}
	public FenetreBas getChatBoard() {
		return chatBoard;
	}
	public void setChatBoard(FenetreBas chatBoard) {
		this.chatBoard = chatBoard;
	}
	public Negotiation<? extends Option> getPrefModel() {
		return prefModel;
	}
	public void setPrefModel(Negotiation<? extends Option> prefModel) {
		this.prefModel = prefModel;
	}
	@Override
	public void start(Stage chatStage) {

		chatStage.setTitle(agentName);
		SplitPane sp = new SplitPane();
		sp.setOrientation(Orientation.VERTICAL);
		TextArea ta = new TextArea();
		//VBox vbox = new VBox(ta);
		FlowPane flow = new FlowPane(Orientation.VERTICAL);

		chatBoard= new FenetreBas(agentName);
		//Chat chat=new Chat();
		//Choice choice=new Choice();
		sp.getItems().addAll(flow,chatBoard);
		sp.setDividerPositions(0.3f);

		String image = FenetreHaut.class.getResource("white.jpg").toExternalForm();

		flow.setStyle("-fx-background-image: url('" + image + "'); "
				+
				"-fx-background-position: center center; " +
				"-fx-background-repeat: stretch;");
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		
		ta.setPrefSize(visualBounds.getWidth(), (visualBounds.getHeight()-5.0)/2);
		
		chatConsole console = new chatConsole(ta);
		
		PrintStream ps;
		try {
			ps = new PrintStream(console, true, "UTF-8");
			
			System.setOut(ps);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.setErr(ps);
		
		flow.getChildren().add(ta);
		Scene scene = new Scene(sp, visualBounds.getWidth(), visualBounds.getHeight());
		chatStage.setScene(scene);
		chatStage.setFullScreen(true);

		scene.getStylesheets().add
		(FenetreHaut.class.getResource("application2.css").toExternalForm());
		chatBoard.addElements(username,situation,chatStage, prefModel, nbAgents, state);
		chatStage.show();

	}
	
	public boolean successNegotiation(){
		return chatBoard.negotiationSuccess();
	}

}
