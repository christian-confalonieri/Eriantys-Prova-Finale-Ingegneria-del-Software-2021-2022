package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.*;

public class GUI extends Application {

    private GUIController guiController;
    private Future<GUIController> guiControllerFuture = new Future<GUIController>() {
        boolean done = false;

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return done;
        }

        @Override
        synchronized public GUIController get() throws InterruptedException {
            while (guiController == null) {
                this.wait();
            }
            done = true;
            return guiController;
        }

        @Override
        @Deprecated
        public GUIController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    @Override
    synchronized public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/login-view.fxml"));
        Parent root = fxmlLoader.load();
        Client.getInstance().setGui(this); // Attach the gui to the client (Launch is called by attachGui)

        guiController = (GUIController) fxmlLoader.getController(); // Loads the controller
        this.notifyAll(); // Wakes up the future waiting for the controller
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public GUIController getGuiController() {
        try {
            return guiControllerFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return guiController; // Not sure but maybe
        }
    }
}

