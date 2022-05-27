package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.entity.Cloud;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUIGameController {

    private List<Island> selectedIslands = new ArrayList<>();

    private List<Cloud> selectedClouds = new ArrayList<>();



    private List<GUISchoolController> schoolControllers;
    private List<AnchorPane> schoolAnchorPanes;

    @FXML
    public AnchorPane school1;
    @FXML
    private GUISchoolController school1Controller;

    @FXML
    private AnchorPane school2;
    @FXML
    private GUISchoolController school2Controller;

    @FXML
    private AnchorPane school3;
    @FXML
    private GUISchoolController school3Controller;

    @FXML
    private AnchorPane school4;
    @FXML
    private GUISchoolController school4Controller;


    @FXML
    private AnchorPane table;
    @FXML
    private GUITableController tableController;

    @FXML
    public TabPane tabPane;


    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIGameController.class.getResource("/it/polimi/ingsw/match-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiGameController = (GUIGameController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root);
        stage.setTitle("Eriantys");
        stage.setScene(scene);
        stage.show();

        Client.getInstance().getGui().guiCallGame(GUIGameController::initializeTable);
        Client.getInstance().getGui().guiCallGame(GUIGameController::initializeSchools);

        Client.getInstance().getGui().guiCallGame(GUIGameController::render);
    }

    public void initializeTable() {
        tableController.initializeTable();
    }

    public void initializeSchools() {
        schoolAnchorPanes = Stream.of(school1, school2, school3, school4).collect(Collectors.toList());
        schoolControllers = Stream.of(school1Controller, school2Controller, school3Controller, school4Controller).collect(Collectors.toList());

        Iterator<AnchorPane> schoolsAnchorPaneIt = schoolAnchorPanes.iterator();
        Iterator<GUISchoolController> schoolControllersIt = schoolControllers.iterator();
        Iterator<Tab> tabsIt = tabPane.getTabs().stream().skip(1).iterator();

        for (Player player : Client.getInstance().getGameHandler().getGame().getPlayers()) {
            GUISchoolController sController = schoolControllersIt.next();
            AnchorPane sPane = schoolsAnchorPaneIt.next();
            Tab sTab = tabsIt.next();

            sController.setPlayerModel(player);
            sTab.setText(player.getName() + "'s school");
        }
        List<GUISchoolController> toRemoveC = new ArrayList<>();
        List<AnchorPane> toRemoveA = new ArrayList<>();
        while(schoolsAnchorPaneIt.hasNext()) {
            GUISchoolController sController = schoolControllersIt.next();
            AnchorPane sPane = schoolsAnchorPaneIt.next();
            Tab sTab = tabsIt.next();

            sPane.setDisable(true);
            sPane.setVisible(false);
            toRemoveC.add(sController);
            toRemoveA.add(sPane);
            sTab.setText("");
            sTab.setDisable(true);
        }
        schoolControllers.removeAll(toRemoveC);
        schoolAnchorPanes.remove(toRemoveA);

    }

    protected void renderTable() {
        tableController.render();
    }

    public void render() {
        tableController.render();
        schoolControllers.forEach(GUISchoolController::render);
    }

    public void addSelectedIsland(Island island) {
        selectedIslands.add(island);
    }

    public void removeSelectedIsland(Island island) {
        selectedIslands.remove(island);
    }

    public void addSelectedCloud(Cloud cloud) {
        selectedClouds.add(cloud);
    }

    public void removeSelectedCloud(Cloud cloud) {
        selectedClouds.remove(cloud);
    }

    public void clearSelectedIslands() {
        selectedIslands.clear();
    }


}
