package it.polimi.ingsw.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIPowerBannerController {

    protected static void initSceneAndController(Label cost, GridPane powerStudentGrid) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIMainMenuController.class.getResource("/it/polimi/ingsw/powerbanner-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Power");
        stage.setScene(scene);
        stage.show();
    }
}
