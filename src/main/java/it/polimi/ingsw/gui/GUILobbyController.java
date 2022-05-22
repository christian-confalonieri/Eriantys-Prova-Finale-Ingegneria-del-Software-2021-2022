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
import java.util.ArrayList;
import java.util.List;

public class GUILobbyController {

    private int prevLobbyNumber; // variable used to anticipate updates from the server
    // of the number of players in the client-side lobby. (The number is in fact already updated when entering a new lobby).
    @FXML
    private Label lblWaitingLobby;

    @FXML
    private ListView<String> lstWaitingLobby;

    /**
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

        Client.getInstance().getGui().guiCallLobby(GUILobbyController::updateConnectedPlayers);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }

    /**
     * @author Christian Confalonieri
     */
    public void updateConnectedPlayers() {
        List<String> allServerLobbies = Client.getInstance().getAllServerLobbys().stream().map(GameLobby::getGameLobbyId).toList();

        if(allServerLobbies.contains(Client.getInstance().getGameLobby().getGameLobbyId())) {
            for(GameLobby gameLobby : Client.getInstance().getAllServerLobbys()) {
                if(gameLobby.getGameLobbyId().equals(Client.getInstance().getGameLobby().getGameLobbyId())) {
                    if(prevLobbyNumber < Client.getInstance().getGameLobby().getWaitingPlayersNumber()) {
                        // case you enter a lobby that has already been updated by the server (so it already exists in allServerLobbies)
                        lblWaitingLobby.setText(Client.getInstance().getGameLobby().getWaitingPlayersNumber() + "/" + Client.getInstance().getGameLobby().getGameRules().cloudsRules.numberOfClouds);
                        lstWaitingLobby.setItems(FXCollections.observableArrayList(Client.getInstance().getGameLobby().toStringPlayers()));
                    }
                    else {
                        // general case of updating players inside a lobby
                        lblWaitingLobby.setText(gameLobby.getWaitingPlayersNumber() + "/" + gameLobby.getGameRules().cloudsRules.numberOfClouds);
                        lstWaitingLobby.setItems(FXCollections.observableArrayList(gameLobby.toStringPlayers()));
                    }
                    prevLobbyNumber = Client.getInstance().getGameLobby().getWaitingPlayersNumber();
                    break;
                }
            }
        }
        else {
            // case you enter a lobby that has not yet been updated by the server
            lblWaitingLobby.setText(Client.getInstance().getGameLobby().getWaitingPlayersNumber() + "/" + Client.getInstance().getGameLobby().getGameRules().cloudsRules.numberOfClouds);
            lstWaitingLobby.setItems(FXCollections.observableArrayList(Client.getInstance().getGameLobby().toStringPlayers()));
            prevLobbyNumber = Client.getInstance().getGameLobby().getWaitingPlayersNumber();
        }
    }
}
