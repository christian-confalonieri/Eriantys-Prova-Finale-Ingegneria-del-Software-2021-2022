package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUIController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtUsername;

    public void printOnWelcomeText(String s) {
        welcomeText.setText(s);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * @author Christian Confalonieri
     */
    public void Login(ActionEvent event) throws IOException {
        if(txtUsername.getText().contains(" ")) {
            lblStatus.setText("Invalid login command");
        }
        else {
            LoginService.loginRequest(txtUsername.getText());
            // close the login window
            ((Stage) txtUsername.getScene().getWindow()).close();

            // open the lobby window
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/hello-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 300, 200);
            stage.setScene(scene);
            stage.show();
        }
    }
}
