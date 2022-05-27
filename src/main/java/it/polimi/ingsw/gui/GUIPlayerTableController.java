package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GUIPlayerTableController {
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
        playerName.setText(player.getName());
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
    }

    public void remove() {
        anchorPane.setDisable(true);
        anchorPane.setVisible(false);
    }
}
