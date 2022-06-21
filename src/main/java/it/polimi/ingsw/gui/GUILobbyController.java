package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.server.GameLobby;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class GUILobbyController {
    @FXML
    private Label lblWaitingLobby;

    @FXML
    private ListView<String> lstWaitingLobby;

    /**
     * Initialize the lobby scene and call the method to update the connected players
     * @author Christian Confalonieri
     */
    @FXML
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUILobbyController.class.getResource("/it/polimi/ingsw/lobby-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiLobbyController = (GUILobbyController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();

        Client.getInstance().getGui().guiCallLobby(GUILobbyController -> GUILobbyController.updateConnectedPlayers(Client.getInstance().getGameLobby()));
    }

    /**
     * Sends a logout request to the server
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }

    /**
     * Updates the displayed list and the displayed number of connected players
     * @author Christian Confalonieri
     */
    public void updateConnectedPlayers(GameLobby gameLobby) {
        lblWaitingLobby.setText(gameLobby.getWaitingPlayersNumber() + "/" + gameLobby.getGameRules().cloudsRules.numberOfClouds);
        lstWaitingLobby.setItems(FXCollections.observableArrayList(gameLobby.toStringArrayPlayers()));
    }
}
