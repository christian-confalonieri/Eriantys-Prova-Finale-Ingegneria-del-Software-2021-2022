package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.PawnColor;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;

public class GUISchoolController {

    /**
     * PROFESSORS
     */
    @FXML
    private ImageView blueProfessor;
    @FXML
    private ImageView greenProfessor;
    @FXML
    private ImageView redProfessor;
    @FXML
    private ImageView pinkProfessor;
    @FXML
    private ImageView yellowProfessor;



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
        renderLane(greenHBox, PawnColor.GREEN);
        renderLane(redHBox, PawnColor.RED);
        renderLane(yellowHBox, PawnColor.YELLOW);
        renderLane(pinkHBox, PawnColor.PINK);
        renderLane(blueHBox, PawnColor.BLUE);
        renderProfessors();
    }

    private void renderLane(HBox hBox, PawnColor pawnColor) {
        int studentsNumber = playerModel.getSchool().getStudentsDiningRoom(pawnColor).size();
        double height = hBox.getHeight();

        int inLaneStudents = hBox.getChildren().size();

        if(inLaneStudents == studentsNumber) return;
        if(inLaneStudents < studentsNumber) {
            while(inLaneStudents < studentsNumber) {
                hBox.getChildren().add(getStudentImage(pawnColor, height));
                inLaneStudents++;
            }
        }
        else {
            while(inLaneStudents > studentsNumber) {
                hBox.getChildren().remove(inLaneStudents - 1);
                inLaneStudents--;
            }
        }
    }

    private void renderProfessors() {
        blueProfessor.setVisible(playerModel.getSchool().hasProfessor(PawnColor.BLUE));
        greenProfessor.setVisible(playerModel.getSchool().hasProfessor(PawnColor.GREEN));
        yellowProfessor.setVisible(playerModel.getSchool().hasProfessor(PawnColor.YELLOW));
        pinkProfessor.setVisible(playerModel.getSchool().hasProfessor(PawnColor.PINK));
        redProfessor.setVisible(playerModel.getSchool().hasProfessor(PawnColor.RED));
    }

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
}
