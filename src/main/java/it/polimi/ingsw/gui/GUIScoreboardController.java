package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GUIScoreboardController {
    @FXML
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIScoreboardController.class.getResource("/it/polimi/ingsw/scoreboard-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiScoreboardController = (GUIScoreboardController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root, 500, 800);
        stage.setTitle("Loading...");
        stage.setScene(scene);
        stage.show();

    }

    public void renderScoreBoard(List<String> scoreboardIds) {
        List<Player> playersScoreboard = scoreboardIds.stream().map(id -> Client.getInstance().getGameHandler().getGame().getPlayerFromId(id)).toList();

    }
}
