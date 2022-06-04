package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Wizard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GUIScoreboardController {
    @FXML
    private VBox leaderboardVBox;
    @FXML
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIScoreboardController.class.getResource("/it/polimi/ingsw/leaderboard-view.fxml"));
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
        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();

    }

    public void renderScoreBoard(List<String> scoreboardIds) {
        List<Player> playersScoreboard = scoreboardIds.stream().map(id -> Client.getInstance().getGameHandler().getGame().getPlayerFromId(id)).toList();
        int count = 1;
        leaderboardVBox.getChildren().clear();
        for(Player player : playersScoreboard) {
            AnchorPane anchorPane = loadLeaderboardBox(count, player.getWizard(), player.getName());
            leaderboardVBox.getChildren().add(anchorPane);
            count++;
        }
        switch (scoreboardIds.size()) {
            case 1 -> { leaderboardVBox.setLayoutX(120); leaderboardVBox.setLayoutY(167);}
            case 2 -> { leaderboardVBox.setLayoutX(120); leaderboardVBox.setLayoutY(118);}
            case 3 -> { leaderboardVBox.setLayoutX(120); leaderboardVBox.setLayoutY(70);}
            default -> { leaderboardVBox.setLayoutX(120); leaderboardVBox.setLayoutY(19.0);}
        }
    }

    private AnchorPane loadLeaderboardBox(int position, Wizard wizard, String player) {
       FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/it/polimi/ingsw/leaderboardbox-view.fxml"));
       AnchorPane anchorPane = null;
       try {
           anchorPane = loader.load();
       }catch(IOException e) {
           //
       }
       GUIScoreboardboxController guiScoreboardboxController = loader.getController();
       guiScoreboardboxController.setBackground(position);
       guiScoreboardboxController.setPlayer(player);
       guiScoreboardboxController.setPosition(position);
       guiScoreboardboxController.setWizard(wizard);
       return anchorPane;
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }
}
