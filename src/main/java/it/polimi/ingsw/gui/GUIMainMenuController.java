package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.server.GameLobby;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIMainMenuController {

    @FXML
    private ListView<String> lstLobbies;
    @FXML
    private Label lblMainMenu;
    @FXML
    private ChoiceBox<String> chcPlayers;
    @FXML
    private ChoiceBox<String> chcWizards;
    @FXML
    private ImageView wizardCardView;

    /**
     * Initialize the main menu scene and call the method to update the lobbies (and refresh the wizards)
     * @param stage The stage in which to open the scene
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
        Client.getInstance().getGui().guiCallMainMenu(GUIMainMenuController::refreshWizardView);
    }

    /**
     * Send to server a new game request
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
     * Send to server a join game request
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
     * Delete selection in the lobby list
     * @author Christian Confalonieri
     */
    @FXML
    public void clearSelection() {
        lstLobbies.getSelectionModel().clearSelection(lstLobbies.getItems().indexOf(lstLobbies.getSelectionModel().getSelectedItem()));
    }

    /**
     * Update the displayed lobbies
     * @author Christian Confalonieri
     */
    public void updateLobbies() {
        String[] lobbiesString = (String[]) Client.getInstance().getAllServerLobbys().stream()
                .map(GameLobby::toStringNoColors).toList().toArray(new String[0]);
        lstLobbies.setItems(FXCollections.observableArrayList(lobbiesString));
    }

    /**
     * Gets a string to print as an error
     * @param string the string to print as an error
     * @author Christian Confalonieri
     */
    public void errorLabelWrite(String string) {
        lblMainMenu.setTextFill(Color.ORANGERED);
        lblMainMenu.setText(string);
    }

    /**
     * Sends a logout request to the server
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }

    @FXML
    public void refreshWizardView() {
        switch(chcWizards.getValue()) {
            case "BLUE" -> wizardCardView.setImage(new Image(this.getClass().getResource("/assets/wizards/blueWizard.jpg").toExternalForm()));
            case "PURPLE" -> wizardCardView.setImage(new Image(this.getClass().getResource("/assets/wizards/purpleWizard.jpg").toExternalForm()));
            case "GREEN" -> wizardCardView.setImage(new Image(this.getClass().getResource("/assets/wizards/greenWizard.jpg").toExternalForm()));
            case "YELLOW"-> wizardCardView.setImage(new Image(this.getClass().getResource("/assets/wizards/yellowWizard.jpg").toExternalForm()));
        }
    }
}
