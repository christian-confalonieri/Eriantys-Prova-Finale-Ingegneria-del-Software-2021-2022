package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.controller.services.LoginService;
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
    @FXML
    private ImageView assistant1;
    @FXML
    private ImageView outlineAssistant1;
    @FXML
    private ImageView assistant2;
    @FXML
    private ImageView outlineAssistant2;
    @FXML
    private ImageView assistant3;
    @FXML
    private ImageView outlineAssistant3;
    @FXML
    private ImageView assistant4;
    @FXML
    private ImageView outlineAssistant4;
    @FXML
    private ImageView assistant5;
    @FXML
    private ImageView outlineAssistant5;
    @FXML
    private ImageView assistant6;
    @FXML
    private ImageView outlineAssistant6;
    @FXML
    private ImageView assistant7;
    @FXML
    private ImageView outlineAssistant7;
    @FXML
    private ImageView assistant8;
    @FXML
    private ImageView outlineAssistant8;
    @FXML
    private ImageView assistant9;
    @FXML
    private ImageView outlineAssistant9;
    @FXML
    private ImageView assistant10;
    @FXML
    private ImageView outlineAssistant10;
    @FXML
    private ImageView student1;
    @FXML
    private ImageView outlineStudent1;
    @FXML
    private ImageView student2;
    @FXML
    private ImageView outlineStudent2;
    @FXML
    private ImageView student3;
    @FXML
    private ImageView outlineStudent3;
    @FXML
    private ImageView student4;
    @FXML
    private ImageView outlineStudent4;
    @FXML
    private ImageView student5;
    @FXML
    private ImageView outlineStudent5;
    @FXML
    private ImageView student6;
    @FXML
    private ImageView outlineStudent6;
    @FXML
    private ImageView student7;
    @FXML
    private ImageView outlineStudent7;
    @FXML
    private ImageView student8;
    @FXML
    private ImageView outlineStudent8;
    @FXML
    private ImageView student9;
    @FXML
    private ImageView outlineStudent9;
    @FXML
    private ImageView diningRoomGreen;
    @FXML
    private ImageView outlineDiningRoomGreen;
    @FXML
    private ImageView diningRoomRed;
    @FXML
    private ImageView outlineDiningRoomRed;
    @FXML
    private ImageView diningRoomYellow;
    @FXML
    private ImageView outlineDiningRoomYellow;
    @FXML
    private ImageView diningRoomPink;
    @FXML
    private ImageView outlineDiningRoomPink;
    @FXML
    private ImageView diningRoomBlue;
    @FXML
    private ImageView outlineDiningRoomBlue;

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
        stage.setTitle("Eriantys");
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

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant1() {
        outlineAssistant1.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant1() {
        outlineAssistant1.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant2() {
        outlineAssistant2.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant2() {
        outlineAssistant2.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant3() {
        outlineAssistant3.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant3() {
        outlineAssistant3.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant4() {
        outlineAssistant4.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant4() {
        outlineAssistant4.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant5() {
        outlineAssistant5.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant5() {
        outlineAssistant5.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant6() {
        outlineAssistant6.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant6() {
        outlineAssistant6.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant7() {
        outlineAssistant7.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant7() {
        outlineAssistant7.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant8() {
        outlineAssistant8.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant8() {
        outlineAssistant8.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant9() {
        outlineAssistant9.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant9() {
        outlineAssistant9.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectAssistant10() {
        outlineAssistant10.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectAssistant10() {
        outlineAssistant10.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent1() {
        outlineStudent1.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent1() {
        outlineStudent1.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent2() {
        outlineStudent2.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent2() {
        outlineStudent2.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent3() {
        outlineStudent3.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent3() {
        outlineStudent3.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent4() {
        outlineStudent4.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent4() {
        outlineStudent4.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent5() {
        outlineStudent5.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent5() {
        outlineStudent5.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent6() {
        outlineStudent6.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent6() {
        outlineStudent6.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent7() {
        outlineStudent7.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent7() {
        outlineStudent7.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent8() {
        outlineStudent8.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent8() {
        outlineStudent8.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectStudent9() {
        outlineStudent9.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectStudent9() {
        outlineStudent9.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectDiningRoomGreen() {
        outlineDiningRoomGreen.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectDiningRoomGreen() {
        outlineDiningRoomGreen.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectDiningRoomRed() {
        outlineDiningRoomRed.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectDiningRoomRed() {
        outlineDiningRoomRed.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectDiningRoomYellow() {
        outlineDiningRoomYellow.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectDiningRoomYellow() {
        outlineDiningRoomYellow.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectDiningRoomPink() {
        outlineDiningRoomPink.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectDiningRoomPink() {
        outlineDiningRoomPink.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    public void selectDiningRoomBlue() {
        outlineDiningRoomBlue.setVisible(true);
    }

    /**
     * @author Christian Confalonieri
     */
    public void deselectDiningRoomBlue() {
        outlineDiningRoomBlue.setVisible(false);
    }

    /**
     * @author Christian Confalonieri
     */
    @FXML
    public void logout() {
        LoginService.logoutRequest();
    }
}
