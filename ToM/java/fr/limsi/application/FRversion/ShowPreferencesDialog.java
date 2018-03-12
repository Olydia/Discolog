package fr.limsi.application.FRversion;

import java.util.HashMap;
import java.util.List;

import fr.limsi.negotiate.restaurant.FR.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ShowPreferencesDialog extends Dialog<String> {

	public ShowPreferencesDialog(HashMap<String, List<String>> preferencesUser){

		VBox cuisine =createList("Cuisine", preferencesUser.get("Cuisine"));
		VBox location =createList("Localisation", preferencesUser.get("Localisation"));
		VBox cost =createList("Prix", preferencesUser.get("Prix"));
		String a = Ambiance.class.getSimpleName();
		VBox athm =createList("Ambiance", preferencesUser.get(a));


		// Create the GridPane
		GridPane pane = new GridPane();
		// Set the horizontal and vertical gaps between children
		pane.setHgap(10);
		pane.setVgap(5);       

		pane.addColumn(0, cuisine);
		pane.addColumn(1, location);
		pane.addColumn(2, cost);
		pane.addColumn(3, athm);

		// Set the Style-properties of the GridPane
//		pane.setStyle("-fx-padding: 10;" +
//				"-fx-border-style: solid inside;" +
//				"-fx-border-width: 2;" +
//				"-fx-border-insets: 5;" +
//				"-fx-border-radius: 5;" +
//				"-fx-border-color: blue;");
		pane.setPrefSize(640, 300);
		getDialogPane().setContent(pane);
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

	}
	public VBox createList(String name , List<String> elems){
		VBox data = new VBox();
		Label label = new Label(name);
		ObservableList<String> list = 
				FXCollections.<String>observableArrayList(elems);

		// Create the ListView for the criteria
		ListView<String> criteria = new ListView<>(list);
		// Set the Orientation of the ListView
		criteria.setOrientation(Orientation.VERTICAL);

		// Set the Size of the ListView
		criteria.setPrefSize(150, 250);

		data.setSpacing(10);
		// Add the Label and the List to the VBox
		data.getChildren().addAll(label,criteria);
		return data;
	}
}
