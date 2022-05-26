package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Island;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GUIIslandController {
    @FXML
    private Label labelIslandCountAndSelected;
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
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedIsland(islandModel));
        if(outline.getOpacity()==0.5) {
            labelIslandCountAndSelected.setStyle("-fx-text-fill: RED");
            outline.setOpacity(1);
        }
        else {
            labelIslandCountAndSelected.setStyle("-fx-text-fill: BLACK");
            outline.setOpacity(0.5);
        }
    }

    @FXML
    private void deselectIsland() {
        labelIslandCountAndSelected.setStyle("-fx-text-fill: BLACK");
    }

    @FXML
    private void highlightIsland() {
        if(outline.getOpacity() == 0) {
            outline.setOpacity(0.5);
        }
    }

    @FXML
    private void unhighlightIsland() {
        if(outline.getOpacity() == 0.5) {
            outline.setOpacity(0);
        }
    }

    @FXML
    public void setIslandNumber(int number) {
        labelIslandCountAndSelected.setText(String.valueOf(number));
    }


}
