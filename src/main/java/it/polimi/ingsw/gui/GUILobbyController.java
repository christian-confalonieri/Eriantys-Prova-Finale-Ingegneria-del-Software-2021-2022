package it.polimi.ingsw.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUILobbyController {

    /**
     * @author Christian Confalonieri
     */
    private void initializeLobby() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/lobby-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();
    }

}
