package it.polimi.ingsw.client.controller.services;

import com.google.gson.Gson;
import it.polimi.ingsw.action.ACK;
import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.action.LogoutAction;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.ClientActionHandler;
import it.polimi.ingsw.controller.ActionHandler;


public class LoginService {
    public static void loginRequest(String playerId) {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(new LoginAction(playerId)));
    }

    public static void login(LoginAction loginAction) {
        Client.getInstance().setPlayerId(loginAction.getPlayerId());
        Client.getInstance().setClientState(ClientState.MAINMENU);
        Client.getInstance().getGui().notifyStateChange();
    }

    public static void failedLogin(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
        Client.getInstance().getGui().guiCallLogin(guiLoginController -> guiLoginController.errorLabelWrite(failedAck.getMessage()));
    }


    public static void logout(LogoutAction logoutAction) {
        Client.getInstance().setPlayerId(null);
        Client.getInstance().setClientState(ClientState.LOGIN);
        Client.getInstance().setPollAllLobbies(true);
    }

    public static void logoutRequest() {
        Client.getInstance().getNetworkController().send(ClientActionHandler.toJson(new LogoutAction(Client.getInstance().getPlayerId())));
    }

}
