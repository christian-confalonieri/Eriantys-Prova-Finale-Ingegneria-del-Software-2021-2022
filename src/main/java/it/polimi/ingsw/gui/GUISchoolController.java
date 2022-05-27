package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.entity.Player;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class GUISchoolController {
    @FXML
    private HBox greenHBox;
    @FXML
    private HBox redHBox;
    @FXML
    private HBox yellowHBox;
    @FXML
    private HBox pinkHBox;
    @FXML
    private HBox blueHBox;

    private Player playerModel;

    public void setPlayerModel(Player playerModel) {
        this.playerModel = playerModel;
    }

    public void render() {

    }
}
