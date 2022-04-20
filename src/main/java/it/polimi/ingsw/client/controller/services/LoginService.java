package it.polimi.ingsw.client.controller.services;

import com.google.gson.Gson;
import it.polimi.ingsw.action.ACK;
import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;


public class LoginService {
    public static void loginRequest(String playerId) {
        Gson gson = new Gson();
        Client.getInstance().getNetworkController().send(gson.toJson(new LoginAction(playerId)));
    }

    public static void login(LoginAction loginAction) {
        Client.getInstance().setPlayerId(loginAction.getPlayerId());
        Client.getInstance().setClientState(ClientState.MAINMENU);
    }

    public static void failedLogin(ACK failedAck) {
        System.out.println(ConsoleColor.YELLOW + failedAck.getMessage() + ConsoleColor.RESET);
    }

}
