package fr.limsi.application.FRversion.SaisiePref.dndTestFR;

import java.util.List;
import java.util.Optional;

import fr.limsi.application.PasswordDialog;
import fr.limsi.negotiate.Criterion;
import fr.limsi.negotiate.restaurant.Cuisine;
import fr.limsi.negotiate.restaurant.totalOrderedModels;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PrefView extends Dialog  {

	
    public PrefView (List<? extends Criterion> values) {
    	ObservableList<? extends Criterion> elements = FXCollections.observableArrayList(values);

        this.setTitle("ListView Experiment 1");

        ListView<? extends Criterion> listView = new ListView(elements);
        
        HBox hbox = new HBox(listView);
        getDialogPane().setContent(hbox);


    }

    public static void main(String[] args) {
    	totalOrderedModels m = new totalOrderedModels();
    	PrefView pd = new PrefView(m.model1().getValueNegotiation(Cuisine.class).getSelf().sortValues());
		Optional<String> result = pd.showAndWait();   
		}
}