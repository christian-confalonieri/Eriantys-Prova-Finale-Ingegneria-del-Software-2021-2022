package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Cloud;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GUICloudController {

    @FXML
    private AnchorPane cloudPane;
    @FXML
    private ImageView cloudBackground;
    @FXML
    private ImageView student1;
    @FXML
    private ImageView student2;
    @FXML
    private ImageView student3;
    @FXML
    private ImageView outline;

    private Cloud cloudModel;

    /**
     * @author Christian Confalonieri
     */
    protected void setCloudModel(Cloud cloudModel) {
        this.cloudModel = cloudModel;
    }

    /**
     * @author Christian Confalonieri
     */
    protected Cloud getCloudModel() {
        return cloudModel;
    }

    protected void render() {
    }

    /**
     * @author Christian Confalonieri
     */
    public void remove() {
        cloudPane.setDisable(true);
        cloudPane.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void selectCloud() {
        if(outline.getOpacity()==0.5) {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedCloud(cloudModel));
            outline.setOpacity(1);
        }
        else {
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.removeSelectedCloud(cloudModel));
            outline.setOpacity(0.5);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void highlightCloud() {
        if(outline.getOpacity() == 0) {
            outline.setOpacity(0.5);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void unhighlightCloud() {
        if(outline.getOpacity() == 0.5) {
            outline.setOpacity(0);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public void setClouds(Integer number) {
        setCloudImage(number);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void setCloudImage(int number) {
        switch (number) {
            case 1 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud1.png").toExternalForm()));
            case 2 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud2.png").toExternalForm()));
            case 3 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud3.png").toExternalForm()));
            case 4 -> cloudBackground.setImage(new Image(this.getClass().getResource("/assets/table/cloud4.png").toExternalForm()));
        }
    }
}
