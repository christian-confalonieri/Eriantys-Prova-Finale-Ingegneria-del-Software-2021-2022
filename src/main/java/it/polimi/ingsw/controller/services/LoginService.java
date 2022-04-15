package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.action.LogoutAction;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

import java.util.UUID;

public class LoginService {

    public static void clientLogin(LoginAction action, ClientNetworkHandler netHandler) throws InvalidAction {

        if(Server.getInstance().isAssigned(netHandler)) {
            System.out.println(ConsoleColor.YELLOW + netHandler.toString() + " client already logged in" + ConsoleColor.RESET);
            // Ignore double login
            return;
        }

        if (action.getPlayerId() == null || action.getPlayerId().isEmpty()) {
            throw new InvalidAction("Login: Invalid login id");
        }

        if (Server.getInstance().isAssigned(action.getPlayerId())) {
            // Player already logged in to a connection
            // TODO replace with InvalidLogin client message
            netHandler.send("InvalidLogin");

            throw new InvalidAction("Login: playerId already taken");
        }

        // Player is not logged in the server net connections
        Server.getInstance().assignConnection(action.getPlayerId(), netHandler);
        netHandler.send("Logged In");

        System.out.println(ConsoleColor.CYAN + netHandler.toString() + " logged in as: " + action.getPlayerId() + ConsoleColor.RESET);
        // For persistence check if the player is in game and if so replay the game
    }

    public static void clientLogout(LogoutAction action, ClientNetworkHandler networkHandler) throws InvalidAction {
        if (Server.getInstance().isAssigned(action.getPlayerId())) {
            // If is in game??
            Server.getInstance().unassignConnection(action.getPlayerId());
            Server.getInstance().exitLobbys(action.getPlayerId());
            System.out.println(ConsoleColor.CYAN + networkHandler.toString() + " logged out from: " + action.getPlayerId() + ConsoleColor.RESET);
        }
        else {
            throw new InvalidAction("Logout: invalid id to log out");
        }
    }
}
