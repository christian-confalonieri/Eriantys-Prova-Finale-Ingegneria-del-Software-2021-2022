package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.power.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GUIPowerController {
    @FXML
    private GridPane powerStudentGrid;
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
        renderStudents();
    }

    /**
     * Opens the banner of the current character
     * @author Christian Confalonieri
     */
    public void selectPower() {
        GUIPowerBannerController.initSceneAndRender(powerCardModel);

//        if(!isActiveSelected) {
//            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.addSelectedPower(powerCardModel,cost,powerStudentGrid));
//            isActiveSelected = true;
//            anchorPane.setStyle("-fx-border-width: 5; -fx-border-color: WHITE");
//        }
//        else {
//            Client.getInstance().getGui().guiCallGame(GUIGameController::removeSelectedPower);
//            isActiveSelected = false;
//            anchorPane.setStyle("");
//        }
    }

    /**
     * Select on mouseover the current character
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
     * Deselects the current character on mouse exit
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


    public void renderStudents() {
        List<Student> powerStudents = new ArrayList<>();
        int noEntry = 0;
        powerStudentGrid.getChildren().clear();

        switch (powerCardModel.getType()) {
            case HERBALIST -> noEntry = ((Herbalist) powerCardModel).getNoEntryCards();
            case JESTER -> powerStudents = ((Jester) powerCardModel).getStudents();
            case PRINCESS -> powerStudents = ((Princess) powerCardModel).getStudents();
            case FRIAR -> powerStudents = ((Friar) powerCardModel).getStudents();
            default -> {
                return;
            }
        }

        int i = 0;
        for (Student student : powerStudents) {
            ImageView studentImage = getStudentImage(student.getColor(), 20);
//            studentImage.setOnMouseClicked((mouseEvent -> clickPowerStudentHandler(mouseEvent, student)));

            powerStudentGrid.add(studentImage, i % 3, i / 3);
            i++;
        }

        for(i=0; i<noEntry; i++) {
            ImageView noEntryImage = new ImageView(new Image(this.getClass().getResource("/assets/table/noEntry.png").toExternalForm()));
            noEntryImage.setPreserveRatio(true);
            noEntryImage.setFitHeight(20);
            powerStudentGrid.add(noEntryImage,i % 3, i / 3);
        }
    }

//    @FXML
//    private void clickPowerStudentHandler(MouseEvent mouseEvent, Student student) {
//        ImageView studentImageView = ((ImageView) mouseEvent.getSource());
//        Image studentImageSelected = getClickedStudentImage(student.getColor(), 20).getImage();
//        Image studentImageStandard = getStudentImage(student.getColor(), 20).getImage();
//        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.powerStudentClicked(studentImageView, student, studentImageSelected, studentImageStandard));
//    }


    private ImageView getStudentImage(PawnColor color, double height) {
        ImageView imageView = new ImageView(switch(color) {
            case GREEN -> new Image(this.getClass().getResource("/assets/students/green.png").toExternalForm());
            case RED -> new Image(this.getClass().getResource("/assets/students/red.png").toExternalForm());
            case PINK -> new Image(this.getClass().getResource("/assets/students/pink.png").toExternalForm());
            case BLUE -> new Image(this.getClass().getResource("/assets/students/blue.png").toExternalForm());
            case YELLOW -> new Image(this.getClass().getResource("/assets/students/yellow.png").toExternalForm());
        });
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

//    private ImageView getClickedStudentImage(PawnColor color, double height) {
//        ImageView imageView = new ImageView(switch(color) {
//            case GREEN -> new Image(this.getClass().getResource("/assets/students/green_temporary.png").toExternalForm());
//            case RED -> new Image(this.getClass().getResource("/assets/students/red_temporary.png").toExternalForm());
//            case PINK -> new Image(this.getClass().getResource("/assets/students/pink_temporary.png").toExternalForm());
//            case BLUE -> new Image(this.getClass().getResource("/assets/students/blue_temporary.png").toExternalForm());
//            case YELLOW -> new Image(this.getClass().getResource("/assets/students/yellow_temporary.png").toExternalForm());
//        });
//        imageView.setPreserveRatio(true);
//        imageView.setFitHeight(height);
//        return imageView;
//    }

}

