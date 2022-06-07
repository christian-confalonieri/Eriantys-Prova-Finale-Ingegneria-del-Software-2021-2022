package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GUISchoolController {

    @FXML
    private ImageView schoolBackground;
    @FXML
    private Label errorLabel;
    @FXML
    private Label gamePhaseLabel;

    /**
     * ENTRANCE
     */
    @FXML
    private GridPane entranceGrid;

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

    private PawnColor laneSelected;


    /**
     * CARDS
     */
    @FXML
    private HBox deckBox;
    @FXML
    private ImageView lastPlayedCard;


    private Player playerModel;

    public void setPlayerModel(Player playerModel) {
        this.playerModel = playerModel;
    }

    public void render() {
        resetErrors();

        renderLane(greenHBox, PawnColor.GREEN);
        renderLane(redHBox, PawnColor.RED);
        renderLane(yellowHBox, PawnColor.YELLOW);
        renderLane(pinkHBox, PawnColor.PINK);
        renderLane(blueHBox, PawnColor.BLUE);
        renderProfessors();
        renderTowers();
        renderEntrance();
        renderHandDeck();
        renderLastPlayedCard();
        renderGamePhaseLabel();
    }

    private void renderGamePhaseLabel() {
        if(!Client.getInstance().getGameHandler().getCurrentPlayer().getName().equals(playerModel.getName())) {
            gamePhaseLabel.setText("WAIT YOUR TURN...");
            return;
        }
        if(Client.getInstance().getGameHandler().getGamePhase() == GamePhase.PREPARATION) {
            gamePhaseLabel.setText("PLAY A CARD");
            return;
        }
        switch (Client.getInstance().getGameHandler().getTurnPhase()) {
            case MOVESTUDENTS -> gamePhaseLabel.setText("MOVE STUDENTS");
            case MOVEMOTHER -> gamePhaseLabel.setText("MOVE MOTHER NATURE");
            case MOVEFROMCLOUD -> gamePhaseLabel.setText("PICK A CLOUD");
        }
    }

    private void renderLane(HBox hBox, PawnColor pawnColor) {
        int studentsNumber = playerModel.getSchool().getStudentsDiningRoom(pawnColor).size();
        double height = hBox.getHeight();

        int inLaneStudents = hBox.getChildren().size();

        if(inLaneStudents == studentsNumber) return;
        if(inLaneStudents < studentsNumber) {
            while(inLaneStudents < studentsNumber) {
                ImageView imageView = getStudentImage(pawnColor, height);
                imageView.setMouseTransparent(true);
                hBox.getChildren().add(imageView);
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

    private void renderEntrance() {
        int studentsNumber = playerModel.getSchool().getEntrance().size();
        double height = 40;
        entranceGrid.getChildren().clear();
        for (int i = 0; i < studentsNumber; i++) {
            Student student = playerModel.getSchool().getEntrance().get(i);
            PawnColor color = student.getColor();
            ImageView studentImage = getStudentImage(color, height);

            studentImage.setOnDragDetected(mouseEvent -> {
                studentImage.setOpacity(0.5);

                Dragboard db = studentImage.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString("");
                //content.putString("Pippo");
                db.setContent(content);
            });
            studentImage.setOnDragDone(dragEvent -> studentDragDone(dragEvent, student));

            studentImage.setOnMouseClicked(mouseEvent -> clickStudentHandler(mouseEvent, student));

            entranceGrid.add(studentImage, (i+1) % 2, (i+1) / 2);
        }
    }

    private void renderHandDeck() {
        deckBox.getChildren().clear();
        if (playerModel.getName().equals(Client.getInstance().getPlayerId())) {
            for (Card card : playerModel.getHandCards()) {
                ImageView image = getAssistantImage(card, 100, 70);
                HBox borderBox = new HBox();
                borderBox.getChildren().add(image);
                borderBox.setOnMouseEntered(this::cardHighlight);
                borderBox.setOnMouseExited(this::cardUnhighlight);
                borderBox.setOnMouseClicked((mouseEvent) -> selectCard(mouseEvent, card));
                deckBox.getChildren().add(borderBox);
            }
        }
    }

    private void renderLastPlayedCard() {
        double height = 100;
        double width = 70;

        Card card = playerModel.getLastPlayedCard();
        if (card == null) {
//            lastPlayedCard.setImage(getWizardImage(playerModel.getWizard(), height, width).getImage());
            lastPlayedCard.setOpacity(0);
        }
        else {
            lastPlayedCard.setOpacity(1);
            lastPlayedCard.setImage(getAssistantImage(card, height, width).getImage());
        }
    }


    private ImageView getWizardImage(Wizard wizard, double height, double width) {
        ImageView imageView = new ImageView(switch (wizard) {
            case BLUE -> new Image(this.getClass().getResource("/assets/wizards/blueWizard.jpg").toExternalForm());
            case PURPLE -> new Image(this.getClass().getResource("/assets/wizards/purpleWizard.jpg").toExternalForm());
            case GREEN -> new Image(this.getClass().getResource("/assets/wizards/greenWizard.jpg").toExternalForm());
            case YELLOW-> new Image(this.getClass().getResource("/assets/wizards/yellowWizard.jpg").toExternalForm());
        });
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
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

    private ImageView getAssistantImage(Card card, double height, double width) {
        ImageView imageView = new ImageView(switch(card) {
            case ONE -> new Image(this.getClass().getResource("/assets/assistantCards/one.png").toExternalForm());
            case TWO -> new Image(this.getClass().getResource("/assets/assistantCards/two.png").toExternalForm());
            case THREE -> new Image(this.getClass().getResource("/assets/assistantCards/three.png").toExternalForm());
            case FOUR -> new Image(this.getClass().getResource("/assets/assistantCards/four.png").toExternalForm());
            case FIVE -> new Image(this.getClass().getResource("/assets/assistantCards/five.png").toExternalForm());
            case SIX -> new Image(this.getClass().getResource("/assets/assistantCards/six.png").toExternalForm());
            case SEVEN -> new Image(this.getClass().getResource("/assets/assistantCards/seven.png").toExternalForm());
            case EIGHT -> new Image(this.getClass().getResource("/assets/assistantCards/eight.png").toExternalForm());
            case NINE -> new Image(this.getClass().getResource("/assets/assistantCards/nine.png").toExternalForm());
            case TEN -> new Image(this.getClass().getResource("/assets/assistantCards/ten.png").toExternalForm());
        });
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }

    @FXML
    private void cardHighlight(MouseEvent mouseEvent) {
        HBox hBoxContainer = (HBox) mouseEvent.getTarget();
        hBoxContainer.setScaleX(1.75);
        hBoxContainer.setScaleY(1.75);
        hBoxContainer.setStyle("-fx-border-color: rgba(255,255,255,0.5); -fx-border-width: 5;");
        hBoxContainer.setViewOrder(-1);
    }

    private void cardUnhighlight(MouseEvent mouseEvent) {
        HBox hBoxContainer = (HBox) mouseEvent.getTarget();
        hBoxContainer.setScaleX(1);
        hBoxContainer.setScaleY(1);
        hBoxContainer.setStyle("");
        hBoxContainer.setViewOrder(0);
    }

    private void selectCard(MouseEvent mouseEvent, Card card) {
        if(Client.getInstance().getGameHandler().getCurrentPlayer().getName().equals(Client.getInstance().getPlayerId())) {
            ((HBox) mouseEvent.getSource()).setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            GameService.playCardRequest(card);
        }
        else
            errorWrite("Wait your turn to play a card");
    }

    public void errorWrite(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    private void resetErrors() {
        errorLabel.setText("");
    }


    @FXML
    private void laneHighLight(MouseEvent mouseEvent) {
        if (!((HBox) mouseEvent.getSource()).getStyle().contains("WHITE"))
            ((HBox) mouseEvent.getSource()).setStyle("-fx-border-color: rgba(255,255,255,0.5); -fx-border-width: 5;");
    }

    @FXML
    private void laneUnhighlight(MouseEvent mouseEvent) {
        if (!((HBox) mouseEvent.getSource()).getStyle().contains("WHITE"))
            ((HBox) mouseEvent.getSource()).setStyle("");
    }


    public void selectLaneGreen() {
        if (laneSelected != PawnColor.GREEN) {
            greenHBox.setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            if(laneSelected != null) getHBoxFromColor(laneSelected).setStyle("");
            laneSelected = PawnColor.GREEN;
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.setSelectedLane(PawnColor.GREEN));
        }
        else {
            laneSelected = null;
            greenHBox.setStyle("");
            Client.getInstance().getGui().guiCallGame(GUIGameController::clearSelectedLane);
        }
    }

    public void selectLaneRed() {
        if (laneSelected != PawnColor.RED) {
            redHBox.setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            if(laneSelected != null) getHBoxFromColor(laneSelected).setStyle("");
            laneSelected = PawnColor.RED;
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.setSelectedLane(PawnColor.RED));
        }
        else {
            laneSelected = null;
            redHBox.setStyle("");
            Client.getInstance().getGui().guiCallGame(GUIGameController::clearSelectedLane);
        }
    }

    public void selectLaneYellow() {
        if (laneSelected != PawnColor.YELLOW) {
            yellowHBox.setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            if(laneSelected != null) getHBoxFromColor(laneSelected).setStyle("");
            laneSelected = PawnColor.YELLOW;
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.setSelectedLane(PawnColor.YELLOW));
        }
        else {
            laneSelected = null;
            yellowHBox.setStyle("");
            Client.getInstance().getGui().guiCallGame(GUIGameController::clearSelectedLane);
        }
    }

    public void selectLanePink() {
        if (laneSelected != PawnColor.PINK) {
            pinkHBox.setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            if(laneSelected != null) getHBoxFromColor(laneSelected).setStyle("");
            laneSelected = PawnColor.PINK;
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.setSelectedLane(PawnColor.PINK));
        }
        else {
            laneSelected = null;
            pinkHBox.setStyle("");
            Client.getInstance().getGui().guiCallGame(GUIGameController::clearSelectedLane);
        }
    }

    public void selectLaneBlue() {
        if (laneSelected != PawnColor.BLUE) {
            blueHBox.setStyle("-fx-border-color: WHITE; -fx-border-width: 5;");
            if(laneSelected != null) getHBoxFromColor(laneSelected).setStyle("");
            laneSelected = PawnColor.BLUE;
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.setSelectedLane(PawnColor.BLUE));
        }
        else {
            laneSelected = null;
            blueHBox.setStyle("");
            Client.getInstance().getGui().guiCallGame(GUIGameController::clearSelectedLane);
        }
    }

    private HBox getHBoxFromColor(PawnColor color) {
        return switch (color) {
            case YELLOW -> yellowHBox;
            case GREEN -> greenHBox;
            case RED -> redHBox;
            case PINK -> pinkHBox;
            case BLUE -> blueHBox;
        };
    }


    @FXML
    private void clickStudentHandler(MouseEvent mouseEvent, Student student) {
        ImageView studentImageView = ((ImageView) mouseEvent.getSource());
        Image studentImageSelected = getClickedStudentImage(student.getColor(), 40).getImage();
        Image studentImageStandard = getStudentImage(student.getColor(), 40).getImage();
        Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.studentOnSchoolClicked(studentImageView, student, studentImageSelected, studentImageStandard));
    }


    @FXML
    private void studentDragDone(DragEvent dragEvent, Student student) {
        ImageView studentImageView = ((ImageView) dragEvent.getSource());
        if (dragEvent.isAccepted()) {
            Image studentImageSelected = getClickedStudentImage(student.getColor(), 40).getImage();
            Image studentImageStandard = getStudentImage(student.getColor(), 40).getImage();
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.studentOnSchoolClicked(studentImageView, student, studentImageSelected, studentImageStandard));
        }
        studentImageView.setOpacity(1);
    }

    @FXML
    private void onSchoolDropped(DragEvent dragEvent) {
        //String s = dragEvent.getDragboard().getString();
        dragEvent.setDropCompleted(true);
    }

    @FXML
    private void onSchoolOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
    }
}
