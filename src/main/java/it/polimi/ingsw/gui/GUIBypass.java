package it.polimi.ingsw.gui;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class GUIBypass extends GUI{

    @Override
    public void start(Stage stage) throws IOException {

    }

    @Override
    public void guiCallLogin(Consumer<GUILoginController> call) {

    }

    @Override
    public void guiCallMainMenu(Consumer<GUIMainMenuController> call) {

    }

    @Override
    public void guiCallLobby(Consumer<GUILobbyController> call) {

    }

    @Override
    public void guiCallGame(Consumer<GUIGameController> call) {

    }

    @Override
    public void guiCallLoading(Consumer<GUILoadingController> call) {
    }

    @Override
    public void notifyStateChange() {

    }


}
