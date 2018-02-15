package fr.limsi.application;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.SwingUtilities;

import fr.limsi.application.SaisiePref.dndTest.Selection;

public class Test extends Application {

    @Override
    public void start (Stage stage) {
        final SwingNode swingNode = new SwingNode();

        createSwingContent(swingNode);

        StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);

        stage.setTitle("Swing in JavaFX");
        stage.setScene(new Scene(pane, 400, 300));
        stage.show();
        }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
				Selection select = new Selection();
                swingNode.setContent(select);
            }
        });
    }
}