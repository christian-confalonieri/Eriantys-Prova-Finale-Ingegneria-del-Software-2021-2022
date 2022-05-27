package it.polimi.ingsw.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class GUITest {

    @Test
    public void objectLoadingTest() {
        new Thread(() -> GUITestApplication.launch(GUITestApplication.class)).start();

        AnchorPane parent;
        GUIIslandController islandController;
        try {
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource("/it/polimi/ingsw/island-view.fxml"));
            parent = loader.load();
            islandController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }



        while(true);
    }
}
