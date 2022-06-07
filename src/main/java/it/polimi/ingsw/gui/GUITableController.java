package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.power.PowerCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUITableController {
    @FXML
    private ImageView entranceBackground;
    @FXML
    private GridPane entranceGrid;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView professor1;
    @FXML
    private ImageView professor2;
    @FXML
    private ImageView professor3;
    @FXML
    private ImageView professor4;
    @FXML
    private ImageView professor5;
    private List<GUIIslandController> islandControllers;
    private List<AnchorPane> islandAnchorPanes;

    public List<GUIIslandController> getIslandControllers() {
        return islandControllers;
    }

    public List<AnchorPane> getIslandAnchorPanes() {
        return islandAnchorPanes;
    }

    private List<GUICloudController> cloudControllers;
    private List<AnchorPane> cloudAnchorPanes;

    public List<GUICloudController> getCloudControllers() {
        return cloudControllers;
    }

    public List<AnchorPane> getCloudAnchorPanes() {
        return cloudAnchorPanes;
    }

    private List<GUIPlayerTableController> playerTableControllers;
    private List<AnchorPane> playerTableAnchorPanes;

    public List<GUIPlayerTableController> getPlayerTableControllers() {
        return playerTableControllers;
    }

    public List<AnchorPane> getPlayerTableAnchorPanes() {
        return playerTableAnchorPanes;
    }

    private List<GUIPowerController> powerControllers;
    private List<AnchorPane> powerAnchorPanes;

    public List<GUIPowerController> getPowerControllers() {
        return powerControllers;
    }

    public List<AnchorPane> getPowerAnchorPanes() {
        return powerAnchorPanes;
    }

    @FXML
    private AnchorPane island1;
    @FXML
    private AnchorPane island2;
    @FXML
    private AnchorPane island3;
    @FXML
    private AnchorPane island4;
    @FXML
    private AnchorPane island5;
    @FXML
    private AnchorPane island6;
    @FXML
    private AnchorPane island7;
    @FXML
    private AnchorPane island8;
    @FXML
    private AnchorPane island9;
    @FXML
    private AnchorPane island10;
    @FXML
    private AnchorPane island11;
    @FXML
    private AnchorPane island12;
    
    @FXML
    private GUIIslandController island1Controller;
    @FXML
    private GUIIslandController island2Controller;
    @FXML
    private GUIIslandController island3Controller;
    @FXML
    private GUIIslandController island4Controller;
    @FXML
    private GUIIslandController island5Controller;
    @FXML
    private GUIIslandController island6Controller;
    @FXML
    private GUIIslandController island7Controller;
    @FXML
    private GUIIslandController island8Controller;
    @FXML
    private GUIIslandController island9Controller;
    @FXML
    private GUIIslandController island10Controller;
    @FXML
    private GUIIslandController island11Controller;
    @FXML
    private GUIIslandController island12Controller;
    
    @FXML
    private AnchorPane cloud1;
    @FXML
    private AnchorPane cloud2;
    @FXML
    private AnchorPane cloud3;
    @FXML
    private AnchorPane cloud4;

    @FXML
    private GUICloudController cloud1Controller;
    @FXML
    private GUICloudController cloud2Controller;
    @FXML
    private GUICloudController cloud3Controller;
    @FXML
    private GUICloudController cloud4Controller;

    @FXML
    private AnchorPane playerT1;
    @FXML
    private AnchorPane playerT2;
    @FXML
    private AnchorPane playerT3;
    @FXML
    private AnchorPane playerT4;

    @FXML
    private GUIPlayerTableController playerT1Controller;
    @FXML
    private GUIPlayerTableController playerT2Controller;
    @FXML
    private GUIPlayerTableController playerT3Controller;
    @FXML
    private GUIPlayerTableController playerT4Controller;
    
    @FXML
    private AnchorPane power1;
    @FXML
    private AnchorPane power2;
    @FXML
    private AnchorPane power3;

    @FXML
    private GUIPowerController power1Controller;
    @FXML
    private GUIPowerController power2Controller;
    @FXML
    private GUIPowerController power3Controller;

    /**
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    protected void initializeTable() {
        islandControllers = Stream.of(island1Controller, island2Controller, island3Controller, island4Controller, island5Controller, island6Controller, island7Controller, island8Controller, island9Controller, island10Controller, island11Controller, island12Controller).collect(Collectors.toList());
        islandAnchorPanes = Stream.of(island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12).collect(Collectors.toList());

        cloudAnchorPanes = List.of(cloud1, cloud2, cloud3, cloud4);
        cloudControllers = List.of(cloud1Controller, cloud2Controller, cloud3Controller, cloud4Controller);

        // Bind a GUIIsland with the Islands in the model
        List<Island> islands = Client.getInstance().getGameHandler().getGame().getIslands();
        Iterator<Island> islandIterator = islands.iterator();
        allIslandExecute(((anchorPane, guiIslandController) -> guiIslandController.setIslandModel(islandIterator.next())));

        // Set the island numbers and images
        for (int i = 0; i < islandControllers.size(); i++) {
            islandControllers.get(i).setIslandImage(i+1);
        }


        // Bind GUIClouds with the clouds in the model
        List<Cloud> clouds = Client.getInstance().getGameHandler().getGame().getClouds();
        Iterator<GUICloudController> cloudControllerIterator = cloudControllers.iterator();

        clouds.forEach(cloud -> cloudControllerIterator.next().setCloudModel(cloud));

        cloudControllers.stream().filter(Predicate.not(GUICloudController::isBoundToModel)).forEach(GUICloudController::remove);

        cloudControllers = cloudControllers.stream().filter(GUICloudController::isBoundToModel).toList();
        cloudAnchorPanes = cloudAnchorPanes.stream().limit(cloudControllers.size()).toList();


        // Set the cloud images
        for (int i = 0; i < cloudControllers.size(); i++) {
            cloudControllers.get(i).setCloudImage(i);
        }


        // Bind playerTable views with players
        playerTableControllers = List.of(playerT1Controller, playerT2Controller, playerT3Controller, playerT4Controller);
        playerTableAnchorPanes = List.of(playerT1, playerT2, playerT3, playerT4);

        Iterator<GUIPlayerTableController> playerTableControllerIt = playerTableControllers.iterator();
        for (Player player : Client.getInstance().getGameHandler().getGame().getPlayers()) {
            playerTableControllerIt.next().setPlayer(player);
        }
        playerTableControllers.stream().filter(Predicate.not(GUIPlayerTableController::isBoundToModel)).forEach(GUIPlayerTableController::remove);
        playerTableControllers = playerTableControllers.stream().filter(GUIPlayerTableController::isBoundToModel).toList();
        playerTableAnchorPanes = playerTableAnchorPanes.stream().limit(playerTableControllers.size()).toList();
        try {
            playerTableControllers.get(1).rotate();
            playerTableControllers.get(2).rotate();
        } catch (Exception e) {
            // 3 player not present
        }




        // Bind power card views with the characters in game
        powerControllers = List.of(power1Controller, power2Controller, power3Controller);
        powerAnchorPanes = List.of(power1, power2, power3);

        Iterator<GUIPowerController> powerControllerIt = powerControllers.iterator();
        for (PowerCard powerCard : Client.getInstance().getGameHandler().getGame().getPowerCards()) {
            powerControllerIt.next().setPowerCard(powerCard);
        }

    }


    /**
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    public void render() {
        clearErrors();

        int[] islandsX = new int[Client.getInstance().getGameHandler().getGame().getIslands().size()];
        int[] islandsY = new int[Client.getInstance().getGameHandler().getGame().getIslands().size()];
        for(int i=1;i<=Client.getInstance().getGameHandler().getGame().getIslands().size();i++) {
            islandsX[i-1] = switch (i) {
                case 1,12 -> 77;
                case 2,11 -> 162;
                case 3,10 -> 267;
                case 4,9 -> 386;
                case 5,8 -> 495;
                case 6,7 -> 573;
                default -> 0;
            };
            islandsY[i-1] = switch (i) {
                case 1,6 -> 202;
                case 2,5 -> 98;
                case 3,4 -> 28;
                case 7,12 -> 316;
                case 8,11 -> 422;
                case 9,10 -> 481;
                default -> 0;
            };
        }

        int[] cloudsX = new int[Client.getInstance().getGameHandler().getGame().getClouds().size()];
        int[] cloudsY = new int[Client.getInstance().getGameHandler().getGame().getClouds().size()];
        for(int i=1;i<=Client.getInstance().getGameHandler().getGame().getClouds().size();i++) {
            if(Client.getInstance().getGameHandler().getGame().getClouds().size() == 2) {
                cloudsX[i-1] = switch (i) {
                    case 1 -> 213;
                    case 2-> 444;
                    default -> 0;
                };
                cloudsY[i-1] = switch (i) {
                    case 1,2 -> 247;
                    default -> 0;
                };
            }
            else {
                cloudsX[i-1] = switch (i) {
                    case 1 -> 213;
                    case 2,4 -> 328;
                    case 3 -> 444;
                    default -> 0;
                };
                cloudsY[i-1] = switch (i) {
                    case 1,3 -> 247;
                    case 2 -> 158;
                    case 4 -> 345;
                    default -> 0;
                };
            }
        }

        int[] playerTableX = new int[] {0, 750, 750, 0};
        int[] playerTableY = new int[] {0, 0, 500, 500};

        int[] powerX = new int[] {750, 750, 750};
        int[] powerY = new int[] {130, 240, 350};


        Iterator<Integer> islandsXIt = Arrays.stream(islandsX).iterator();
        Iterator<Integer> islandsYIt = Arrays.stream(islandsY).iterator();

        allIslandExecute((islandPane, islandController) -> renderIsland(islandPane, islandController, islandsXIt.next(), islandsYIt.next()));

        Iterator<Integer> cloudsXIt = Arrays.stream(cloudsX).iterator();
        Iterator<Integer> cloudsYIt = Arrays.stream(cloudsY).iterator();

        allCloudExecute((cloudPane, cloudController) -> renderCloud(cloudPane, cloudController, cloudsXIt.next(), cloudsYIt.next()));


        Iterator<Integer> playerTableXIt = Arrays.stream(playerTableX).iterator();
        Iterator<Integer> playerTableYIt = Arrays.stream(playerTableY).iterator();

        allPlayerTableExecute((playerTablePane, playerTableController) -> renderPlayerTable(playerTablePane, playerTableController, playerTableXIt.next(), playerTableYIt.next()));


        Iterator<Integer> powerXIt = Arrays.stream(powerX).iterator();
        Iterator<Integer> powerYIt = Arrays.stream(powerY).iterator();

        allPowerExecute(((anchorPane, guiPowerController) -> renderPower(anchorPane, guiPowerController, powerXIt.next(), powerYIt.next())));

        setProfessors();

        renderPlayerEntrance();
    }

    protected void errorWrite(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    private void clearErrors() {
        errorLabel.setText("");
    }

    private void renderIsland(AnchorPane islandPane, GUIIslandController islandController, double x, double y) {
        if(Client.getInstance().getGameHandler().getGame().getIslands().contains(islandController.getIslandModel())) {
            islandPane.setLayoutX(x);
            islandPane.setLayoutY(y);
            islandController.render();
        }
        else {
            islandController.remove();
        }
    }

    private void renderPlayerEntrance() {
        Player playerModel = Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId());

        int studentsNumber = playerModel.getSchool().getEntrance().size();
        double height = 40;

        entranceGrid.getChildren().clear();
        entranceBackground.setOpacity(0);

        if (Client.getInstance().getGameHandler().getCurrentPlayer().getName().equals(Client.getInstance().getPlayerId()) &&
                Client.getInstance().getGameHandler().getGamePhase() == GamePhase.TURN &&
                Client.getInstance().getGameHandler().getTurnPhase() == TurnPhase.MOVESTUDENTS
        )   {
            entranceBackground.setOpacity(1);
            for (int i = 0; i < studentsNumber; i++) {
                Student student = playerModel.getSchool().getEntrance().get(i);
                PawnColor color = student.getColor();
                ImageView studentImage = getStudentImage(color, height);
                studentImage.setRotate(90);
                studentImage.setOnDragDetected(mouseEvent -> {
                    studentImage.setOpacity(0.5);

                    Dragboard db = studentImage.startDragAndDrop(TransferMode.ANY);

                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    db.setContent(content);
                });

                studentImage.setOnDragDone(dragEvent -> studentDragOnIslandDone(dragEvent, student));
                // studentImage.setOnMouseClicked(mouseEvent -> clickStudentHandler(mouseEvent, student));

                entranceGrid.add(studentImage, (i + 1) % 2, (i + 1) / 2);
            }
        }
    }

    private void studentDragOnIslandDone(DragEvent dragEvent, Student student) {
        ImageView studentImageView = ((ImageView) dragEvent.getSource());
        if(dragEvent.isAccepted()) {
            Image studentImageSelected = getClickedStudentImage(student.getColor(), 40).getImage();
            Image studentImageStandard = getStudentImage(student.getColor(), 40).getImage();
            String islandUUID = dragEvent.getDragboard().getString();
            Client.getInstance().getGui().guiCallGame(guiGameController -> guiGameController.studentOnIslandDragged(studentImageView, student, islandUUID, studentImageSelected, studentImageStandard));
        }
        studentImageView.setOpacity(1);
    }

    /**
     * @author Christian Confalonieri
     */
    private void renderCloud(AnchorPane cloudPane, GUICloudController cloudController, double x, double y) {
        cloudPane.setLayoutX(x);
        cloudPane.setLayoutY(y);
        cloudController.render();
    }


    private void renderPlayerTable(AnchorPane playerTablePane, GUIPlayerTableController playerTableController, double x, double y) {
        playerTablePane.setLayoutX(x);
        playerTablePane.setLayoutY(y);
        playerTableController.render();
    }

    private void renderPower(AnchorPane powerAnchorPane, GUIPowerController powerController, double x, double y) {
        powerAnchorPane.setLayoutX(x);
        powerAnchorPane.setLayoutY(y);
        powerController.render();
    }




    public void allIslandExecute(BiConsumer<AnchorPane, GUIIslandController> function) {
        Iterator<AnchorPane> islandAnchorPaneIt = islandAnchorPanes.iterator();
        Iterator<GUIIslandController> islandControllerIt = islandControllers.iterator();

        while (islandAnchorPaneIt.hasNext() && islandControllerIt.hasNext()) {
            function.accept(islandAnchorPaneIt.next(), islandControllerIt.next());
        }
    }

    /**
     * @author Christian Confalonieri
     */
    public void allCloudExecute(BiConsumer<AnchorPane, GUICloudController> function) {
        Iterator<AnchorPane> cloudAnchorPaneIt = cloudAnchorPanes.iterator();
        Iterator<GUICloudController> cloudControllerIt = cloudControllers.iterator();

        while (cloudAnchorPaneIt.hasNext() && cloudControllerIt.hasNext()) {
            function.accept(cloudAnchorPaneIt.next(), cloudControllerIt.next());
        }
    }

    public void allPlayerTableExecute(BiConsumer<AnchorPane, GUIPlayerTableController> function) {
        Iterator<AnchorPane> playerTableAnchorPaneIt = playerTableAnchorPanes.iterator();
        Iterator<GUIPlayerTableController> playerTableControllersIt = playerTableControllers.iterator();

        while (playerTableAnchorPaneIt.hasNext() && playerTableControllersIt.hasNext()) {
            function.accept(playerTableAnchorPaneIt.next(), playerTableControllersIt.next());
        }
    }

    public void allPowerExecute(BiConsumer<AnchorPane, GUIPowerController> function) {
        Iterator<AnchorPane> powerAnchorPaneIt = powerAnchorPanes.iterator();
        Iterator<GUIPowerController> powerControllerIt = powerControllers.iterator();

        while (powerAnchorPaneIt.hasNext() && powerControllerIt.hasNext()) {
            function.accept(powerAnchorPaneIt.next(), powerControllerIt.next());
        }
    }


    /**
     * @author Christian Confalonieri
     */
    private GUICloudController getCloudController(int number) {
        return switch(number) {
            case 1 -> cloud1Controller;
            case 2 -> cloud2Controller;
            case 3 -> cloud3Controller;
            case 4 -> cloud4Controller;
            default -> null;
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private GUIPowerController getPowerController(int number) {
        return switch(number) {
            case 1 -> power1Controller;
            case 2 -> power2Controller;
            case 3 -> power3Controller;
            default -> null;
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private void setProfessors() {
        clearProfessors();
        List<Professor> professors = Client.getInstance().getGameHandler().getGame().getBoardProfessors();
        for(int i=1;i<=professors.size();i++) {
            getGUIProfessor(i).setImage(getProfessorImage(professors.get(i-1)));
            getGUIProfessor(i).setOpacity(1);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private ImageView getGUIProfessor(int number) {
        return switch (number) {
            case 1 -> professor1;
            case 2 -> professor2;
            case 3 -> professor3;
            case 4 -> professor4;
            case 5 -> professor5;
            default -> null;
        };
    }

    /**
     * @author Christian Confalonieri
     */
    private Image getProfessorImage(Professor professor) {
        return switch(professor.getColor()) {
            case GREEN -> new Image(this.getClass().getResource("/assets/professors/green.png").toExternalForm());
            case RED -> new Image(this.getClass().getResource("/assets/professors/red.png").toExternalForm());
            case PINK -> new Image(this.getClass().getResource("/assets/professors/pink.png").toExternalForm());
            case BLUE -> new Image(this.getClass().getResource("/assets/professors/blue.png").toExternalForm());
            case YELLOW -> new Image(this.getClass().getResource("/assets/professors/yellow.png").toExternalForm());
        };
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

    /**
     * @author Christian Confalonieri
     */
    private void clearProfessors() {
        for(int i=1; i<=5; i++) {
            getGUIProfessor(i).setOpacity(0);
        }
    }
}
