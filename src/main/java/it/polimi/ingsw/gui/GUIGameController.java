package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIGameController {
    @FXML
    private Label welcomeText;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private ImageView outlineCharacter1;
    @FXML
    private ImageView character1;
    @FXML
    private ImageView outlineCharacter2;
    @FXML
    private ImageView character2;
    @FXML
    private ImageView outlineCharacter3;
    @FXML
    private ImageView character3;
    @FXML
    private ImageView island1;
    @FXML
    private ImageView outlineIsland1;
    @FXML
    private ImageView island2;
    @FXML
    private ImageView outlineIsland2;
    @FXML
    private ImageView island3;
    @FXML
    private ImageView outlineIsland3;
    @FXML
    private ImageView island4;
    @FXML
    private ImageView outlineIsland4;
    @FXML
    private ImageView island5;
    @FXML
    private ImageView outlineIsland5;
    @FXML
    private ImageView island6;
    @FXML
    private ImageView outlineIsland6;
    @FXML
    private ImageView island7;
    @FXML
    private ImageView outlineIsland7;
    @FXML
    private ImageView island8;
    @FXML
    private ImageView outlineIsland8;
    @FXML
    private ImageView island9;
    @FXML
    private ImageView outlineIsland9;
    @FXML
    private ImageView island10;
    @FXML
    private ImageView outlineIsland10;
    @FXML
    private ImageView island11;
    @FXML
    private ImageView outlineIsland11;
    @FXML
    private ImageView island12;
    @FXML
    private ImageView outlineIsland12;
    @FXML
    private ImageView cloud1;
    @FXML
    private ImageView outlineCloud1;
    @FXML
    private ImageView cloud2;
    @FXML
    private ImageView outlineCloud2;
    @FXML
    private ImageView cloud3;
    @FXML
    private ImageView outlineCloud3;
    @FXML
    private ImageView cloud4;
    @FXML
    private ImageView outlineCloud4;

    /**
     * @author Christian Confalonieri
     */
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
        stage.setTitle("Table");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCharacter1() {
        outlineCharacter1.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCharacter1() {
        outlineCharacter1.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCharacter2() {
        outlineCharacter2.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCharacter2() {
        outlineCharacter2.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCharacter3() {
        outlineCharacter3.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCharacter3() {
        outlineCharacter3.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland1() {
        outlineIsland1.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland1() {
        outlineIsland1.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland2() {
        outlineIsland2.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland2() {
        outlineIsland2.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland3() {
        outlineIsland3.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland3() {
        outlineIsland3.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland4() {
        outlineIsland4.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland4() {
        outlineIsland4.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland5() {
        outlineIsland5.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland5() {
        outlineIsland5.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland6() {
        outlineIsland6.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland6() {
        outlineIsland6.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland7() {
        outlineIsland7.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland7() {
        outlineIsland7.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland8() {
        outlineIsland8.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland8() {
        outlineIsland8.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland9() {
        outlineIsland9.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland9() {
        outlineIsland9.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland10() {
        outlineIsland10.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland10() {
        outlineIsland10.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland11() {
        outlineIsland11.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland11() {
        outlineIsland11.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectIsland12() {
        outlineIsland12.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectIsland12() {
        outlineIsland12.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCloud1() {
        outlineCloud1.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCloud1() {
        outlineCloud1.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCloud2() {
        outlineCloud2.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCloud2() {
        outlineCloud2.setVisible(false);
    }
    /**
     * @author Christian Confalonieri
     */
    public void selectCloud3() {
        outlineCloud3.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCloud3() {
        outlineCloud3.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectCloud4() {
        outlineCloud4.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectCloud4() {
        outlineCloud4.setVisible(false);
    }
}
