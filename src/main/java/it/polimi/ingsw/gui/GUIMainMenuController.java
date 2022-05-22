package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.PlayerLobby;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIMainMenuController {
    @FXML
    private ListView<String> lstLobbies;
    @FXML
    private Label lblMainMenu;
    @FXML
    private ChoiceBox<String> chcPlayers;
    @FXML
    private ChoiceBox<String> chcWizards;

    /**
     * @author Christian Confalonieri
     */
    public static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIMainMenuController.class.getResource("/it/polimi/ingsw/mainmenu-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiMainMenuController = (GUIMainMenuController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main-Menu");
        stage.setScene(scene);
        stage.show();

        Client.getInstance().getGui().guiCallMainMenu(GUIMainMenuController::updateLobbies);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void newGame() {
        try{
            LobbyService.newGameRequest(Integer.parseInt(chcPlayers.getValue()), null, Wizard.parseColor(chcWizards.getValue()));
        }
        catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void joinGame() {
        if(lstLobbies.getSelectionModel().getSelectedItem() == null) {
            try{
                LobbyService.joinGameRequest(Integer.parseInt(chcPlayers.getValue()), Wizard.parseColor(chcWizards.getValue()));
            }
            catch (InvalidColor e) {
                System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
            }
        }
        else {
            try{
                LobbyService.joinGameIdRequest(Client.getInstance().getAllServerLobbys().get(lstLobbies.getSelectionModel().getSelectedIndex()).getGameLobbyId(), Wizard.parseColor(chcWizards.getValue()));
            }
            catch (InvalidColor e) {
                System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void clearSelection() {
        lstLobbies.getSelectionModel().clearSelection(lstLobbies.getItems().indexOf(lstLobbies.getSelectionModel().getSelectedItem()));
    }

    /**
     * @author Christian Confalonieri
     */
    public void updateLobbies() {
        String[] lobbiesString =
                Client.getInstance().getAllServerLobbys().stream().map(GameLobby::toStringNoColors).toList().toArray(new String[0]);
        lstLobbies.setItems(FXCollections.observableArrayList(lobbiesString));
    }

    /**
     * @author Christian Confalonieri
     */
    public void errorLabelWrite(String string) {
        lblMainMenu.setTextFill(Color.ORANGERED);
        lblMainMenu.setText(string);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }

}
