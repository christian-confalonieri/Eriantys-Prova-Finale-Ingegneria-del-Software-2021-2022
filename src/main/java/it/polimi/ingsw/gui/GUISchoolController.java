package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class GUISchoolController {

    /**
     * TOWERS
     */
    @FXML
    private GridPane towerGrid;

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


    /**
     * LANES
     */
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
        renderTowers();
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

    private void renderTowers() {
        if(playerModel.getTowerColor() == null) return;

        int towerNumber = playerModel.getSchool().getTowers().size();
        double height = 50;

        int inViewTowers = towerGrid.getChildren().size();

        if(inViewTowers == towerNumber) return;
        if(inViewTowers < towerNumber) {
            while(inViewTowers < towerNumber) {
                towerGrid.add(getTowerImage(playerModel.getTowerColor(), height), inViewTowers % 2, inViewTowers / 2);
                inViewTowers++;
            }
        }
        else {
            while(inViewTowers > towerNumber) {
                towerGrid.getChildren().remove(inViewTowers - 1);
                inViewTowers--;
            }
        }
    }

    private ImageView getTowerImage(TowerColor towerColor, double height) {
        ImageView imageView = new ImageView(switch (towerColor) {
            case WHITE -> new Image(this.getClass().getResource("/assets/towers/white.png").toExternalForm());
            case BLACK -> new Image(this.getClass().getResource("/assets/towers/black.png").toExternalForm());
            case GREY -> new Image(this.getClass().getResource("/assets/towers/grey.png").toExternalForm());
        });
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }
}
