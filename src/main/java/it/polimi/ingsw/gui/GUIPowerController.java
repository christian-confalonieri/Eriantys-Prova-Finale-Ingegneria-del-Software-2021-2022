package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.power.PowerCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GUIPowerController {
    @FXML
    private ImageView outline;
    @FXML
    private Label cost;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView powerCard;

    private PowerCard powerCardModel;

    private boolean isActiveSelected = false;

    public void setPowerCard(PowerCard powerCard) {
        this.powerCardModel = powerCard;
        switch(powerCard.getType()) {
            case HERBALIST -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/herbalist_edited.png").toExternalForm()));
            case FARMER -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/farmer_edited.png").toExternalForm()));
            case CENTAUR -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/centaur_edited.png").toExternalForm()));
            case FRIAR -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/friar_edited.png").toExternalForm()));
            case HARVESTER -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/harvester_edited.png").toExternalForm()));
            case HERALD -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/herald_edited.png").toExternalForm()));
            case JESTER -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/jester_edited.png").toExternalForm()));
            case KNIGHT -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/knight.png").toExternalForm()));
            case MAILMAN -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/mailman_edited.png").toExternalForm()));
            case MINSTREL -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/minstrel_edited.png").toExternalForm()));
            case PRINCESS -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/princess_edited.png").toExternalForm()));
            case THIEF -> this.powerCard.setImage(new Image(this.getClass().getResource("/assets/characterCards/edited/thief_edited.png").toExternalForm()));
        }
    }

    public void render() {
        cost.setText(String.valueOf(powerCardModel.getCost()));
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectPower() {
        if(!isActiveSelected) {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedPower(powerCardModel));
            isActiveSelected = true;
            anchorPane.setStyle("-fx-border-width: 5; -fx-border-color: WHITE");
        }
        else {
            Client.getInstance().getGui().guiCallGame(GUIGameController::removeSelectedPower);
            isActiveSelected = false;
            anchorPane.setStyle("");
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void highlightPower() {
        anchorPane.toFront();
        anchorPane.setScaleX(1.75);
        anchorPane.setScaleY(1.75);
        if(isActiveSelected) {
            anchorPane.setStyle("-fx-border-width: 5; -fx-border-color: WHITE");
        }
        else {
            anchorPane.setStyle("-fx-border-width: 5; -fx-border-color: rgba(255,255,255,0.5)");
        }

    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void unhighlightPower() {
        anchorPane.setScaleX(1);
        anchorPane.setScaleY(1);

        if (isActiveSelected) {
            anchorPane.setStyle("-fx-border-width: 5; -fx-border-color: WHITE");
        } else {
            anchorPane.setStyle("");
        }
    }

    public void unselectPower() {

    }
}

