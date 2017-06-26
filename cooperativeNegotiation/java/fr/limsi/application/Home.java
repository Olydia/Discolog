package fr.limsi.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.wpi.cetask.Shell;
import edu.wpi.disco.Console;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import fr.limsi.negotiate.ExampleAgent;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

public class Home extends Application {
	String username;
	public Home(){

	}

	@Override
    public void start(Stage homeStage) {
        homeStage.setTitle("Negotiation Agent");
        AnchorPane pane = new AnchorPane();
       // pane.setAlignment(Pos.CENTER);
        //The alignment property changes the default position of the grid from
        //the top left of the scene to the center.
        /*grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
*/
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
        (Home.class.getResource("application2.css").toExternalForm());
        homeStage.show();
        Chat chat=new Chat();
    	Stage chatStage=new Stage();

        /*The name label*/
        Label userName = new Label("User Name:");
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
        AnchorPane.setLeftAnchor(userTextField,600.0);

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

        list.addAll(sceneTitle,pw,userName,userTextField,cb,hbBtn);



        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (cb.getValue()==null){
                	//grid.getChildren().remove(errorMessage);
                	//grid.add(errorMessage, 1, 7);
                	errorMessage.setText("You must choose a situation");
                	list.add(errorMessage);
                }
                else{
            	chat.username=userTextField.getText();
            	chat.situation=(String) cb.getValue();
            	chat.start(chatStage);
            	homeStage.hide();
                }
            }
        });

    }

 public static void main(String[] args) {
	 launch(args);
	/* totalOrderedModels model = new totalOrderedModels();

		Interaction interaction = new Interaction(
				new ExampleAgent("agent", model.model1()),
				new User("user"),
				args.length > 0 && args[0].length() > 0 ? args[0] : null);
		interaction.load("models/Negotiate.xml");
		((ExampleAgent) interaction.getSystem()).setRelation(0.9);

		// do not guess recipes, since using DecompositionPlugin below
		interaction.setGuess(false);
		// TODO: enable random choice among applicable utterances (disabled
		//       for now to make debugging easier
		// Agent.RANDOM = new Random(12345);
		interaction.start(true); // give user first turn
			String lien="C:\\Users\\dhouib\\AppData\\Local\\Temp\\Console.test";
			Console cnsl=new Console(lien,interaction);
		    String cmd="fr.limsi.negotiate.lang.RejectState/createProposal(Packages.fr.limsi.negotiate.restaurant.Location.SOUTH_SIDE)/Packages.fr.limsi.negotiate.restaurant.Location.SOUTH_SIDE";

		    cnsl.execute(cmd);
		    String FILENAME = "C:\\Users\\dhouib\\AppData\\Local\\Temp\\Console.test";
		    BufferedReader br = null;
			FileReader fr = null;

			try {

				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);

				String sCurrentLine;

				br = new BufferedReader(new FileReader(FILENAME));

				while ((sCurrentLine = br.readLine()) != null) {
					try{
						File ff=new File("C:\\Users\\dhouib\\Desktop\\test.txt");
						ff.createNewFile();
						FileWriter ffw=new FileWriter(ff);
						ffw.write(sCurrentLine);  // �crire une ligne dans le fichier resultat.txt
						ffw.write("\n"); // forcer le passage � la ligne
						ffw.close(); // fermer le fichier � la fin des traitements
						} catch (Exception e) {}
					System.out.println("1");
					}




			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}*/


    }
}