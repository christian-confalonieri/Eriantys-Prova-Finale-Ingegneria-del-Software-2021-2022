package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.enumeration.Wizard;
import javafx.css.converter.PaintConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
        switch (position) {
            case 1 -> this.position.setTextFill(Color.GOLD);
            case 2 -> this.position.setTextFill(Color.SILVER);
            case 3 -> this.position.setTextFill(Color.ROSYBROWN);
        }
    }

    public void setPlayer(String player) {
        this.player.setText(player);
    }

    public void setWizard(Wizard wizard) {
        switch(wizard) {
            case BLUE -> this.wizard.setImage(new Image(this.getClass().getResource("/assets/wizards/blueWizard.jpg").toExternalForm()));
            case PURPLE -> this.wizard.setImage(new Image(this.getClass().getResource("/assets/wizards/purpleWizard.jpg").toExternalForm()));
            case GREEN -> this.wizard.setImage(new Image(this.getClass().getResource("/assets/wizards/greenWizard.jpg").toExternalForm()));
            case YELLOW-> this.wizard.setImage(new Image(this.getClass().getResource("/assets/wizards/yellowWizard.jpg").toExternalForm()));
        }
    }

    public void setBackground(int number) {
        switch(number) {
            case 2 -> this.background.setImage(new Image(this.getClass().getResource("/assets/leaderboard/leaderBackground2.png").toExternalForm()));
            case 3 -> this.background.setImage(new Image(this.getClass().getResource("/assets/leaderboard/leaderBackground3.png").toExternalForm()));
            default -> this.background.setImage(new Image(this.getClass().getResource("/assets/leaderboard/leaderBackground1.png").toExternalForm()));
        }
    }
}
