package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Team;
import it.polimi.ingsw.model.game.Game4P;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class GUIPlayerTableController {
    @FXML
    private VBox vBox;
    @FXML
    private Label teamName;
    @FXML
    private Label coinsLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView wizardCard;
    @FXML
    private Label playerName;

    private Player playerModel;

    public boolean isBoundToModel() { return !(playerModel == null);}

    public void setPlayer(Player player) {
        playerModel = player;
        String name = player.getName();
        if (Client.getInstance().getPlayerId().equals(playerModel.getName())) {
            name += " (YOU)";
        }
        playerName.setText(name);

        if (Client.getInstance().getGameHandler().getGame().getClass().equals(Game4P.class)) {
            List<Team> teamList = ((Game4P) Client.getInstance().getGameHandler().getGame()).getTeams();
            if (teamList.get(0).getPlayers().contains(player))
                teamName.setText("TEAM 1");
            else
                teamName.setText("TEAM 2");
        } else {
            vBox.getChildren().remove(teamName);
        }

        switch(player.getWizard()) {
            case BLUE -> wizardCard.setImage(new Image(this.getClass().getResource("/assets/wizards/blueWizard.jpg").toExternalForm()));
            case PURPLE -> wizardCard.setImage(new Image(this.getClass().getResource("/assets/wizards/purpleWizard.jpg").toExternalForm()));
            case GREEN -> wizardCard.setImage(new Image(this.getClass().getResource("/assets/wizards/greenWizard.jpg").toExternalForm()));
            case YELLOW-> wizardCard.setImage(new Image(this.getClass().getResource("/assets/wizards/yellowWizard.jpg").toExternalForm()));
        }
    }

    public void render() {
        if(Client.getInstance().getGameHandler().getCurrentPlayer().equals(playerModel)) {
            playerName.setStyle("-fx-text-fill: YELLOW");
        }
        else {
            playerName.setStyle("-fx-text-fill: BLACK");
        }
        coinsLabel.setText(String.valueOf(playerModel.getCoins()));
    }

    public void remove() {
        anchorPane.setDisable(true);
        anchorPane.setVisible(false);
    }

    public void rotate() {
        ((HBox) anchorPane.getChildren().get(0)).getChildren().get(0).toFront();
    }
}
