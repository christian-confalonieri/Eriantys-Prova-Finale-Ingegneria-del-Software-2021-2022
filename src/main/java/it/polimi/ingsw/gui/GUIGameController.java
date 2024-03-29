package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.power.PowerCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUIGameController {

    private List<Island> selectedIslands = new ArrayList<>();

    private List<Student> selectedPowerStudents = new ArrayList<>();


    private List<Student> selectedEntranceToSchoolStudents = new ArrayList<>();
    private Map<Student, String> selectedEntranceToIslandStudents = new HashMap<>();

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

    private GUISchoolController clientSchoolController;

    private GUISchoolController getClientSchoolController() {
        return clientSchoolController;
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
            if(player.getName().equals(Client.getInstance().getPlayerId())) clientSchoolController = sController;
            sTab.setText(player.getName() + "'s school");
        }
        List<GUISchoolController> toRemoveC = new ArrayList<>();
        List<AnchorPane> toRemoveA = new ArrayList<>();
        while(schoolsAnchorPaneIt.hasNext()) {
            GUISchoolController sController = schoolControllersIt.next();
            AnchorPane sPane = schoolsAnchorPaneIt.next();
            Tab sTab = tabsIt.next();

            toRemoveC.add(sController);
            toRemoveA.add(sPane);
            sTab.setText("");
        }
        schoolControllers.removeAll(toRemoveC);
        schoolAnchorPanes.remove(toRemoveA);

        //new tab order
        List<Tab> tabs = new ArrayList<>();
        Player clientPlayer = Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId());
        int indexPlayer = Client.getInstance().getGameHandler().getGame().getPlayers().indexOf(clientPlayer);

        //I add table to the new list
        tabs.add(tabPane.getTabs().get(0));
        tabPane.getTabs().remove(tabPane.getTabs().get(0));

        //I am adding the client school to the new list
        tabs.add(tabPane.getTabs().get(indexPlayer));
        tabPane.getTabs().remove(tabPane.getTabs().get(indexPlayer));

        //I will add the remaining schools to the list (And I removed the unused tabs)
        for(Tab tab : tabPane.getTabs()) {
            if(!tab.getText().equals("")) {
                tabs.add(tab);
            }
        }
        tabPane.getTabs().clear();

        //I update tabPane
        tabPane.getTabs().addAll(tabs);


    }

    public void render() {
        clearSelectedStudents();

        tableController.render();
        schoolControllers.forEach(GUISchoolController::render);
    }

    public void errorWrite(String errorMessage) {
        tableController.errorWrite(errorMessage);
        schoolControllers.forEach(guiSchoolController -> guiSchoolController.errorWrite(errorMessage));
    }

    public void addSelectedIsland(Island island) {
        selectedIslands.add(island);
    }

    public void removeSelectedIsland(Island island) {
        selectedIslands.remove(island);
    }

    public void clearSelectedIslands() {
        selectedIslands.clear();
    }


    protected void studentOnSchoolClicked(ImageView studentImage, Student student, Image studentSelectedImage, Image studentStandardImage) {
        if(!selectedEntranceToSchoolStudents.contains(student)) {
            studentImage.setImage(studentSelectedImage);
            selectedEntranceToSchoolStudents.add(student);
            tableController.disableStudentFromSchool(student);
        }
        else {
            studentImage.setImage(studentStandardImage);
            selectedEntranceToSchoolStudents.remove(student);
            tableController.enableStudentFromSchool(student);
        }

        if (selectedEntranceToSchoolStudents.size() + selectedEntranceToIslandStudents.size() ==
                Client.getInstance().getGameHandler().getGame().getGameRules().studentsRules.turnStudents) {
            GameService.moveStudentsRequest(selectedEntranceToSchoolStudents, selectedEntranceToIslandStudents);
        }
    }

    protected void studentOnIslandDragged(ImageView studentImage, Student student, String islandUUID, Image studentSelectedImage, Image studentStandardImage) {
        if(!selectedEntranceToIslandStudents.containsKey(student) && islandUUID != null) {
            studentImage.setImage(studentSelectedImage);
            selectedEntranceToIslandStudents.put(student, islandUUID);
            getClientSchoolController().disableStudentFromTable(student);
        }
        else {
            studentImage.setImage(studentStandardImage);
            selectedEntranceToIslandStudents.remove(student);
            getClientSchoolController().enableStudentFromTable(student);
        }

        if (selectedEntranceToSchoolStudents.size() + selectedEntranceToIslandStudents.size() ==
                Client.getInstance().getGameHandler().getGame().getGameRules().studentsRules.turnStudents) {
            GameService.moveStudentsRequest(selectedEntranceToSchoolStudents, selectedEntranceToIslandStudents);
        }
    }

    public void clearSelectedStudents() {
        selectedEntranceToSchoolStudents.clear();
        selectedPowerStudents.clear();
        selectedEntranceToIslandStudents.clear();
    }

    /**
     * Sends a logout request to the server
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }
}
