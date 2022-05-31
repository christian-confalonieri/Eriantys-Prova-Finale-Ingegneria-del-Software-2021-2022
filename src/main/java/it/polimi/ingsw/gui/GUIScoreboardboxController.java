package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.enumeration.Wizard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GUIScoreboardboxController {
    @FXML
    private Label position;
    @FXML
    private Label player;
    @FXML
    private ImageView wizard;
    @FXML
    private ImageView background;

    public void setPosition(int position) {
        this.position.setText(Integer.toString(position));
    }

    public void setPlayer(String player) {

    }

    public void setWizard(Wizard wizard) {

    }

    public void setBackground(int number) {

    }
}
