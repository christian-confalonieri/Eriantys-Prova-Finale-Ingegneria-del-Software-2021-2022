package it.polimi.ingsw.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class GUI extends Application {

    protected GUILoadingController guiLoadingController;
    private Future<GUILoadingController> guiLoadingControllerFuture = new Future<>() {
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
        synchronized public GUILoadingController get() throws InterruptedException {
            while (guiLoadingController == null) {
                this.wait();
            }
            done = true;
            return guiLoadingController;
        }

        @Override
        @Deprecated
        public GUILoadingController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    protected GUILoginController guiLoginController;
    private Future<GUILoginController> guiLoginControllerFuture = new Future<>() {
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
        synchronized public GUILoginController get() throws InterruptedException {
            while (guiLoginController == null) {
                this.wait();
            }
            done = true;
            return guiLoginController;
        }

        @Override
        @Deprecated
        public GUILoginController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    protected GUILobbyController guiLobbyController;
    private Future<GUILobbyController> guiLobbyControllerFuture = new Future<>() {
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
        synchronized public GUILobbyController get() throws InterruptedException {
            while (guiLobbyController == null) {
                this.wait();
            }
            done = true;
            return guiLobbyController;
        }

        @Override
        @Deprecated
        public GUILobbyController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    protected GUIMainMenuController guiMainMenuController;
    private Future<GUIMainMenuController> guiMainMenuControllerFuture = new Future<>() {
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
        synchronized public GUIMainMenuController get() throws InterruptedException {
            while (guiMainMenuController == null) {
                this.wait();
            }
            done = true;
            return guiMainMenuController;
        }

        @Override
        @Deprecated
        public GUIMainMenuController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    protected GUIGameController guiGameController;
    private Future<GUIGameController> guiGameControllerFuture = new Future<>() {
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
        synchronized public GUIGameController get() throws InterruptedException {
            while (guiGameController == null) {
                this.wait();
            }
            done = true;
            return guiGameController;
        }

        @Override
        @Deprecated
        public GUIGameController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };

    protected GUIScoreboardController guiScoreboardController;
    private Future<GUIScoreboardController> guiScoreboardControllerFuture = new Future<>() {
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
        synchronized public GUIScoreboardController get() throws InterruptedException {
            while (guiScoreboardController == null) {
                this.wait();
            }
            done = true;
            return guiScoreboardController;
        }

        @Override
        @Deprecated
        public GUIScoreboardController get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };


    @Override
    public void start(Stage stage) throws IOException {
        Client.getInstance().setGui(this); // Attach the gui to the client (Launch is called by attachGui)
        GUILoadingController.initSceneAndController(stage);
    }

    public void guiCallLoading(Consumer<GUILoadingController> call) {
        if(Client.getInstance().getClientState() == ClientState.LOADING) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiLoadingControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void guiCallLogin(Consumer<GUILoginController> call) {
        if(Client.getInstance().getClientState() == ClientState.LOGIN) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiLoginControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void guiCallMainMenu(Consumer<GUIMainMenuController> call) {
        if(Client.getInstance().getClientState() == ClientState.MAINMENU) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiMainMenuControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void guiCallLobby(Consumer<GUILobbyController> call) {
        if(Client.getInstance().getClientState() == ClientState.WAITINGLOBBY) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiLobbyControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void guiCallGame(Consumer<GUIGameController> call) {
        if(Client.getInstance().getClientState() == ClientState.INGAME) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiGameControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void guiCallScoreboard(Consumer<GUIScoreboardController> call) {
        if(Client.getInstance().getClientState() == ClientState.ENDGAME) {
            Platform.runLater(() -> {
                try {
                    call.accept(this.guiScoreboardControllerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }



    public void notifyStateChange() {
        Platform.runLater( () -> {
            if(Stage.getWindows().size() > 1) {
                try { Stage.getWindows().forEach(window -> ((Stage) window).close()); }
                catch (Exception e) { System.out.println("Tried closing windows out of bounds");}
            }
            Stage currentStageWindow = Stage.getWindows().size() != 0 ? ((Stage)(Stage.getWindows().get(0))) : new Stage();

            currentStageWindow.setOnCloseRequest(windowEvent -> {
                        Client.getInstance().getNetworkController().shutdown();
                        Client.getInstance().interruptReconnection();
                    }
            );

            switch(Client.getInstance().getClientState()) {
                case LOADING -> GUILoadingController.initSceneAndController(currentStageWindow);
                case LOGIN -> GUILoginController.initSceneAndController(currentStageWindow);
                case MAINMENU -> GUIMainMenuController.initSceneAndController(currentStageWindow);
                case WAITINGLOBBY -> GUILobbyController.initSceneAndController(currentStageWindow);
                case INGAME -> GUIGameController.initSceneAndController(currentStageWindow);
                case ENDGAME -> GUIScoreboardController.initSceneAndController(currentStageWindow);
            }
        });
    }
}

