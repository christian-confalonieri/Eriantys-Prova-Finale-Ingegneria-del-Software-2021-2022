package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIGameController {
    @FXML
    private Label welcomeText;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * @author Christian Confalonieri
     */
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIGameController.class.getResource("/it/polimi/ingsw/match-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiGameController = (GUIGameController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root);
        stage.setTitle("Table");
        stage.setScene(scene);
        stage.show();
    }
}
