package it.polimi.ingsw.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GUIController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
