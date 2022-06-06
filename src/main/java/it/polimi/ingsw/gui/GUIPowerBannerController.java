package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.power.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIPowerBannerController {

    @FXML
    private ImageView powerCard;
    @FXML
    private Label cost;
    @FXML
    private Label powerName;
    @FXML
    private Label powerEffect;
    @FXML
    private Label cardStudentsLabel;
    @FXML
    private Label entranceStudentsLabel;
    @FXML
    private GridPane gridCardStudents;
    @FXML
    private GridPane gridEntrance;
    @FXML
    private ChoiceBox<String> islandChoiceBox;
    @FXML
    private CheckBox checkBoxRed;
    @FXML
    private CheckBox checkBoxBlue;
    @FXML
    private CheckBox checkBoxGreen;
    @FXML
    private CheckBox checkBoxPink;
    @FXML
    private CheckBox checkBoxYellow;
    @FXML
    private Label messageLabel;
    @FXML
    private ChoiceBox<String> colorChoiceBox;

    protected static void initSceneAndRender(PowerCard powerCard) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIMainMenuController.class.getResource("/it/polimi/ingsw/powerbanner-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Power");
        stage.setScene(scene);
        stage.show();

        ((GUIPowerBannerController) fxmlLoader.getController()).renderPower(powerCard);
    }


    /**
     * @author Christian Confalonieri
     */
    private void renderPower(PowerCard powerCard) {
        this.powerCard.setImage(setPowerCard(powerCard));
        cost.setText(Integer.toString(powerCard.getCost()));
        powerName.setText(powerCard.getType().toString());
        powerEffect.setText(setPowerEffect(powerCard));
        messageLabel.setText(getPowerMessage(powerCard));

        renderCardStudents(powerCard);
        renderEntranceStudents(powerCard);
        renderIslandChoiceBox(powerCard);
        renderDiningRoomCheckBox(powerCard);
        renderInputPosition(powerCard);
    }

    /**
     * @author Christian Confalonieri
     */
    private void renderCardStudents(PowerCard powerCard) {
        List<Student> powerStudents = new ArrayList<>();
        int noEntry = 0;
        gridCardStudents.getChildren().clear();

        switch (powerCard.getType()) {
            case HERBALIST -> noEntry = ((Herbalist) powerCard).getNoEntryCards();
            case JESTER -> powerStudents = ((Jester) powerCard).getStudents();
            case PRINCESS -> powerStudents = ((Princess) powerCard).getStudents();
            case FRIAR -> powerStudents = ((Friar) powerCard).getStudents();
            default -> {
                return;
            }
        }

        int i = 0;
        for (Student student : powerStudents) {
            ImageView studentImage = getStudentImage(student.getColor(), 20);
            studentImage.setOnMouseClicked((mouseEvent -> clickPowerStudentHandler(mouseEvent, student)));

            gridCardStudents.add(studentImage, i % 3, i / 3);
            i++;
        }

        for(i=0; i<noEntry; i++) {
            ImageView noEntryImage = new ImageView(new Image(this.getClass().getResource("/assets/table/noEntry.png").toExternalForm()));
            noEntryImage.setPreserveRatio(true);
            noEntryImage.setFitHeight(20);
            gridCardStudents.add(noEntryImage,i % 3, i / 3);
        }

        switch(powerCard.getType()) {
            case HERBALIST -> cardStudentsLabel.setText("No entry tiles on the card");
            case JESTER, PRINCESS, FRIAR -> cardStudentsLabel.setText("Students on the card");
            default -> {
                gridCardStudents.setOpacity(0);
                cardStudentsLabel.setOpacity(0);
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private void renderEntranceStudents(PowerCard powerCard) {
        switch(powerCard.getType()) {
            case JESTER, MINSTREL -> {
                int i = 0;
                for (Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                    ImageView studentImage = getStudentImage(student.getColor(), 20);
                    studentImage.setOnMouseClicked((mouseEvent -> clickPowerStudentHandler(mouseEvent, student)));
                    gridEntrance.add(studentImage, i % 5, i / 5);
                    i++;
                }
            }
            default -> {
                gridEntrance.setOpacity(0);
                entranceStudentsLabel.setOpacity(0);
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private  void renderIslandChoiceBox(PowerCard powerCard) {
        switch(powerCard.getType()) {
            case HERBALIST, FRIAR, HERALD -> {}
            default -> islandChoiceBox.setOpacity(0);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private void renderDiningRoomCheckBox(PowerCard powerCard) {
        switch(powerCard.getType()) {
            case HARVESTER,THIEF -> {
                checkBoxRed.setOpacity(0);
                checkBoxBlue.setOpacity(0);
                checkBoxGreen.setOpacity(0);
                checkBoxYellow.setOpacity(0);
                checkBoxPink.setOpacity(0);
            }
            case MINSTREL ->  colorChoiceBox.setOpacity(0);
            default -> {
                colorChoiceBox.setOpacity(0);
                checkBoxRed.setOpacity(0);
                checkBoxBlue.setOpacity(0);
                checkBoxGreen.setOpacity(0);
                checkBoxYellow.setOpacity(0);
                checkBoxPink.setOpacity(0);
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private void renderInputPosition(PowerCard powerCard) {
        switch(powerCard.getType()) {
            case PRINCESS -> {
                gridCardStudents.setLayoutX(180);
                cardStudentsLabel.setLayoutX(171);
            }
            case FRIAR, HERBALIST -> {
                gridCardStudents.setLayoutX(144);
                cardStudentsLabel.setLayoutX(135);
                islandChoiceBox.setLayoutX(231);
            }
            case JESTER -> {
                gridCardStudents.setLayoutX(142);
                cardStudentsLabel.setLayoutX(133);
                gridEntrance.setLayoutX(226);
                entranceStudentsLabel.setLayoutX(246);
            }
            case MINSTREL -> {
                gridEntrance.setLayoutX(109);
                entranceStudentsLabel.setLayoutX(129);
                checkBoxRed.setLayoutX(223);
                checkBoxBlue.setLayoutX(223);
                checkBoxGreen.setLayoutX(301);
                checkBoxYellow.setLayoutX(223);
                checkBoxPink.setLayoutX(301);
            }
            case HERALD -> {
                islandChoiceBox.setLayoutX(183);
            }
            case HARVESTER, THIEF -> {
                colorChoiceBox.setLayoutX(183);
            }
        }
    }

    private Image setPowerCard(PowerCard powerCard) {
        return switch(powerCard.getType()) {
            case HERBALIST -> new Image(this.getClass().getResource("/assets/characterCards/edited/herbalist_edited.png").toExternalForm());
            case FARMER -> new Image(this.getClass().getResource("/assets/characterCards/edited/farmer_edited.png").toExternalForm());
            case CENTAUR -> new Image(this.getClass().getResource("/assets/characterCards/edited/centaur_edited.png").toExternalForm());
            case FRIAR -> new Image(this.getClass().getResource("/assets/characterCards/edited/friar_edited.png").toExternalForm());
            case HARVESTER -> new Image(this.getClass().getResource("/assets/characterCards/edited/harvester_edited.png").toExternalForm());
            case HERALD -> new Image(this.getClass().getResource("/assets/characterCards/edited/herald_edited.png").toExternalForm());
            case JESTER -> new Image(this.getClass().getResource("/assets/characterCards/edited/jester_edited.png").toExternalForm());
            case KNIGHT -> new Image(this.getClass().getResource("/assets/characterCards/edited/knight.png").toExternalForm());
            case MAILMAN -> new Image(this.getClass().getResource("/assets/characterCards/edited/mailman_edited.png").toExternalForm());
            case MINSTREL -> new Image(this.getClass().getResource("/assets/characterCards/edited/minstrel_edited.png").toExternalForm());
            case PRINCESS -> new Image(this.getClass().getResource("/assets/characterCards/edited/princess_edited.png").toExternalForm());
            case THIEF -> new Image(this.getClass().getResource("/assets/characterCards/edited/thief_edited.png").toExternalForm());
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private String setPowerEffect(PowerCard powerCard) {
        return switch(powerCard.getType()) {
            case HERBALIST -> "Place a No Entry tile on an Island of your choice.\n" +
                    "The first time Mother Nature ends her movement there,\n" +
                    "put the No Entry tile back onto this card DO NOT calculate influence on that Island, or place any Towers.";
            case FARMER -> "During this turn, you take control of any number of Professors even if you have the same " +
                    "number of Students as the player who currently controls them.";
            case CENTAUR -> "When resolving a Conquering on an Island, Towers do not count towards influence.";
            case FRIAR -> "Take 1 Student from this card and place it on an Island of your choice. Then, draw a " +
                    "new Student from the Bag and place it on this card.";
            case HARVESTER -> "Choose a color of Student: during the influence calculation this turn, that color " +
                    "adds no influence.";
            case HERALD -> "Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\n" +
                    "Mother Nature will still move and the Island where she ends her movement will also be resolved.";
            case JESTER -> "You may take up to 3 Students from this card and replace them with the same number of " +
                    "Students from your Entrance.";
            case KNIGHT -> "During the influence calculation this turn, you count as having 2 more influence.";
            case MAILMAN -> "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant " +
                    "card you’ve played.";
            case MINSTREL -> "You may exchange up to 2 Students between your Entrance and your Dining Room.";
            case PRINCESS -> "Take 1 Student from this card and place it in your Dining Room.\n" +
                    "Then, draw a new Student from the Bag and place it on this card.";
            case THIEF -> "Choose a type of Student: every player (including yourself)\n" +
                    "must return 3 Students of that type from their Dining Room to the bag.\n" +
                    "If any player has fewer than 3 Students of that type, return as many Students as they have.";
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private String getPowerMessage(PowerCard powerCard) {
        return switch(powerCard.getType()) {
            case HERBALIST, HERALD -> "Choose an island";
            case FRIAR -> "Choose a student and an island";
            case HARVESTER, THIEF -> "Choose a color";
            case JESTER -> "Choose up to 3 students both on the card and in the entrance";
            case MINSTREL -> "Choose up to two students to exchange between entrance and dining room";
            case PRINCESS -> "Choose a student";
            default -> "Press OK";
        };
    }

    @FXML
    private void clickPowerStudentHandler(MouseEvent mouseEvent, Student student) {
        ImageView studentImageView = ((ImageView) mouseEvent.getSource());
        Image studentImageSelected = getClickedStudentImage(student.getColor(), 20).getImage();
        Image studentImageStandard = getStudentImage(student.getColor(), 20).getImage();
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.powerStudentClicked(studentImageView, student, studentImageSelected, studentImageStandard));
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

    private ImageView getClickedStudentImage(PawnColor color, double height) {
        ImageView imageView = new ImageView(switch(color) {
            case GREEN -> new Image(this.getClass().getResource("/assets/students/green_temporary.png").toExternalForm());
            case RED -> new Image(this.getClass().getResource("/assets/students/red_temporary.png").toExternalForm());
            case PINK -> new Image(this.getClass().getResource("/assets/students/pink_temporary.png").toExternalForm());
            case BLUE -> new Image(this.getClass().getResource("/assets/students/blue_temporary.png").toExternalForm());
            case YELLOW -> new Image(this.getClass().getResource("/assets/students/yellow_temporary.png").toExternalForm());
        });
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }

    @FXML
    private void powerRequest() {

    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    private void cancel() {
        ((Stage) powerCard.getScene().getWindow()).close();
    }
}
