package it.polimi.ingsw.gui;

import it.polimi.ingsw.model.entity.Island;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GUIIslandController {
    @FXML
    private ImageView noEntry;
    @FXML
    private ImageView islandBackground;
    @FXML
    private ImageView motherNature;
    @FXML
    private ImageView student1;
    @FXML
    private ImageView student2;
    @FXML
    private ImageView outline;
    @FXML
    private Label labelStudent1;
    @FXML
    private Label labelStudent2;
    @FXML
    private ImageView student5;
    @FXML
    private ImageView student4;
    @FXML
    private Label labelStudent5;
    @FXML
    private Label labelStudent4;
    @FXML
    private ImageView student3;
    @FXML
    private Label labelStudent3;
    @FXML
    private AnchorPane islandPane;


    private Island islandModel;

    protected void setIslandModel(Island islandModel) {
        this.islandModel = islandModel;
    }

    protected Island getIslandModel() {
        return islandModel;
    }

    protected void render() {

    }

    public void remove() {
        islandPane.setDisable(true);
        islandPane.setVisible(false);
    }

    @FXML
    private void selectIsland() {
    }

    @FXML
    private void deselectIsland() {
    }

    @FXML
    private void highlightIsland() {
        outline.setOpacity(0.5);
    }

    @FXML
    private void unhighlightIsland() {
        outline.setOpacity(0);
    }
}
