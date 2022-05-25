package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIGameController {

    @FXML
    public AnchorPane school1;
    @FXML
    private GUISchoolController school1Controller;

    @FXML
    private AnchorPane school2;
    @FXML
    private GUISchoolController school2Controller;

    @FXML
    private AnchorPane school3;
    @FXML
    private GUISchoolController school3Controller;

    @FXML
    private AnchorPane school4;
    @FXML
    private GUISchoolController school4Controller;


    @FXML
    private AnchorPane table;
    @FXML
    private GUITableController tableController;


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
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();
    }

}
