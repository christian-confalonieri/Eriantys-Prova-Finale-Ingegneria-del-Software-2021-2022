package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController{
    @FXML
    private Label welcomeText;

    // login-view
    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtUsername;

    //mainmenu-view
    @FXML
    private Label lblError;
    @FXML
    private ChoiceBox<String> chcPlayers;

    @FXML
    private ChoiceBox<String> chcWizards;

    private int playerRules;
    private String wizard;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void login() throws IOException {
        if(txtUsername.getText().contains(" ")) {
            lblStatus.setText("Invalid login command");
        }
        else {
            LoginService.loginRequest(txtUsername.getText());
            // close the login window
            ((Stage) txtUsername.getScene().getWindow()).close();

            // open the main menu window
            initializeMainMenu();
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private void initializeMainMenu() throws IOException {
        playerRules = 2;
        wizard = "BLUE";

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/mainmenu-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main-Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void newGame() throws IOException {
        try{
            LobbyService.newGameRequest(playerRules, null, Wizard.parseColor(wizard));
        }
        catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        }
        // close the new game window
        ((Stage) lblError.getScene().getWindow()).close();

        // open the main menu window
        initializeLobby();
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void getPlayerRules() {
        playerRules = Integer.parseInt(chcPlayers.getValue());
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void getSelectedWizard() {
        wizard = chcWizards.getValue();
    }

    /**
     * @author Christian Confalonieri
     */
    private void initializeLobby() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main-Menu");
        stage.setScene(scene);
        stage.show();
    }
}
