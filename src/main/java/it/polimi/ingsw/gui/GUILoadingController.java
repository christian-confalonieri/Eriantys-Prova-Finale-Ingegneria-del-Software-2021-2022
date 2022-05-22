package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GUILoadingController {
    @FXML
    public ImageView loadingImage;

    @FXML
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUILoginController.class.getResource("/it/polimi/ingsw/loading-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiLoadingController = (GUILoadingController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Loading...");
        stage.setScene(scene);
        stage.show();

        Client.getInstance().getGui().guiCallLoading(GUILoadingController::loadImage);
    }

    public void loadImage() {
        loadingImage.setImage(new Image(this.getClass().getResource("/assets/loadingScreen/eriantys.jpeg").toExternalForm()));
    }
}
