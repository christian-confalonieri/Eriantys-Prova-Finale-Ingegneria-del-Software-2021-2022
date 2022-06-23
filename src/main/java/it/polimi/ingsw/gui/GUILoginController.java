package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GUILoginController {
    // login-view
    @FXML
    private Label lblLogin;

    @FXML
    private TextField txtUsername;

    /**
     * After doing a simple check on the entered text it sends the login request to the server
     * @throws IOException
     * @author Christian Confalonieri
     */
    @FXML
    public void login() throws IOException {
        if(txtUsername.getText().contains(" ")) {
            errorLabelWrite("Spaces are not allowed");
        }
        else {
            LoginService.loginRequest(txtUsername.getText());
        }
    }

    public void errorLabelWrite(String string) {
        lblLogin.setTextFill(Color.ORANGERED);
        lblLogin.setText(string);
    }


    @FXML
    protected static void initSceneAndController(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GUILoginController.class.getResource("/it/polimi/ingsw/login-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (Client.getInstance().getGui()) {
            Client.getInstance().getGui().guiLoginController = (GUILoginController) fxmlLoader.getController(); // Loads the controller
            Client.getInstance().getGui().notifyAll(); // Wakes up the future waiting for the controller
        }
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }


}
