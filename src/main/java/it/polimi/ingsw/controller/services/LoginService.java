package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.network.ClientNetworkHandler;

public class LoginService {
    public static void clientLogin(LoginAction action, ClientNetworkHandler netHandler) {
        System.out.println("LOGIN " + action.getPlayerId());

        if (action.getPlayerId() == null || action.getPlayerId().isEmpty()) {
            // Generate a new id
            // Will return the serialized id to send back

        }
        else {
            // check if present and connected to a game
            // Will return the serialized id and the game where it was logged in
        }
    }
}
