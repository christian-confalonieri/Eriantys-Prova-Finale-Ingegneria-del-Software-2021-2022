package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUITestApplication extends Application {

    @FXML
    private AnchorPane anchorPaneTable;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GUILoginController.class.getResource("/it/polimi/ingsw/table-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();
    }
}
