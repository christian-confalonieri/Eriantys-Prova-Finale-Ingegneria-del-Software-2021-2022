package it.polimi.ingsw.gui;

import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.PlayerLobby;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private AnchorPane mainmenu;

    @FXML
    private ListView<String> lstLobbies;
    @FXML
    private Label lblError;
    @FXML
    private ChoiceBox<String> chcPlayers;

    @FXML
    private ChoiceBox<String> chcWizards;

    private int playerRules = 2;
    private String wizard = "BLUE";
    private List<String> lobbies;

    private String selectedLobby;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000),
                        actionEvent -> {
                            // Call update method for every 1 sec.
                            updateLobbies();
                        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
    public void joinGame() throws IOException {
        if(lstLobbies.getSelectionModel().getSelectedItem() == null || lobbies.isEmpty()) {
            try{
                LobbyService.joinGameRequest(playerRules, Wizard.parseColor(wizard));
            }
            catch (InvalidColor e) {
                System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
            }
        }
        else {
            try{
                LobbyService.joinGameIdRequest(Client.getInstance().getAllServerLobbys().get(lobbies.indexOf(lstLobbies.getSelectionModel().getSelectedItem())).getGameLobbyId(), Wizard.parseColor(wizard));
            }
            catch (InvalidColor e) {
                System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
            }
        }

        // close the new game window
        ((Stage) lblError.getScene().getWindow()).close();

        // open the game windows
        initializeGame();
    }

    /**
     * @author Christian Confalonieri
     */
    private void initializeLobby() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Lobby");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Christian Confalonieri
     */
    private void initializeGame() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void getPlayerRules() {
        lstLobbies.getSelectionModel().clearSelection(lobbies.indexOf(lstLobbies.getSelectionModel().getSelectedItem()));
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
    private void updateLobbies() {
        String r;
        lobbies = new ArrayList<>();

        for (GameLobby gameLobby : Client.getInstance().getAllServerLobbys()) {
            r = "";
            for (PlayerLobby p :
                    gameLobby.getPlayersWaiting()) {
                r += getPlayerColorToString(p);
            }
            lobbies.add(gameLobby.getLobbySize() + "P\t" + r + "\t\t");
        }
        String[] lobbiesString = new String[lobbies.size()];

        for (int i = 0; i < lobbies.size(); i++) {
            lobbiesString[i] = lobbies.get(i);
        }
        if(lstLobbies != null) {
            lstLobbies.setItems(FXCollections.observableArrayList(lobbiesString));
        }
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void getSelectedLobby() {
        selectedLobby = lstLobbies.getSelectionModel().getSelectedItem();
    }
    private String getPlayerColorToString(PlayerLobby p) {
        return switch(p.getWizard()) {
            case GREEN -> "G";
            case YELLOW -> "Y";
            case PURPLE -> "P";
            case BLUE -> "B";
        };
    }
}
