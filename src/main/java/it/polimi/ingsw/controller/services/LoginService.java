package it.polimi.ingsw.controller.services;

import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.ActionHandler;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

public class LoginService {

    public static void clientLogin(LoginAction action, ClientNetworkHandler netHandler) throws InvalidAction {
        if(Server.getInstance().isAssigned(netHandler)) {
            System.out.println(ConsoleColor.YELLOW + netHandler.toString() + " client already logged in" + ConsoleColor.RESET);
            netHandler.send(ActionHandler.toJson(new ACK("", ActionType.LOGIN, "Client already logged in", false)));
            throw new InvalidAction("Login: client already logged in");
        }

        if (action.getPlayerId() == null || action.getPlayerId().isEmpty()) {
            netHandler.send(ActionHandler.toJson(new ACK("", ActionType.LOGIN, "Invalid login id", false)));
            throw new InvalidAction("Login: Invalid login id");
        }

        if (Server.getInstance().isAssigned(action.getPlayerId())) {
            // Player already logged in to a connection
            netHandler.send(ActionHandler.toJson(new ACK(action.getPlayerId(), ActionType.LOGIN, "PlayerId already taken", false)));
            throw new InvalidAction("Login: playerId already taken");
        }

        // Player is not logged in the server net connections
        Server.getInstance().assignConnection(action.getPlayerId(), netHandler);
        netHandler.send(ActionHandler.toJson(action));

        System.out.println(ConsoleColor.CYAN + netHandler.toString() + " logged in as: " + action.getPlayerId() + ConsoleColor.RESET);
        // For persistence check if the player is in game and if so replay the game
    }

    public static void clientLogout(LogoutAction action, ClientNetworkHandler networkHandler) throws InvalidAction {
        if (Server.getInstance().isAssigned(action.getPlayerId())) {
            Server.getInstance().unassignConnection(action.getPlayerId());

            if(Server.getInstance().isInGame(action.getPlayerId())) {

                GameHandler gameHandler = Server.getInstance().getGameHandler(action.getPlayerId());
                Server.getInstance().removePlayerFromGame(action.getPlayerId());

                if(gameHandler.getOrderedTurnPlayers().stream().map(Player::getName).filter(id -> !id.equals(action.getPlayerId()))
                        .allMatch(id -> Server.getInstance().isInGame(id))) { // If you disconnected and all other players are in game (You are the first to disconnect)
                    Server.getInstance().getConnectionsForGameBroadcast(gameHandler).forEach(net -> net.send(
                            ActionHandler.toJson(new GameInterruptedAction(action.getPlayerId(),
                                    gameHandler.getGame().getLeaderBoard().stream().map(Player::getName).filter(id -> !id.equals(action.getPlayerId())).toList()))
                    ));
                    gameHandler.getOrderedTurnPlayers().stream().map(Player::getName).forEach(id -> Server.getInstance().removePlayerFromGame(id));
                }

                if (gameHandler
                        .getOrderedTurnPlayers().stream().map(Player::getName).noneMatch(id -> Server.getInstance().isInGame(id)))
                {   // All players disconnected
                    System.out.println(ConsoleColor.YELLOW + "All player disconnected from " + gameHandler + ". Game deleted.");
                    Server.getInstance().removeGame(gameHandler);
                }
            }

            Server.getInstance().exitLobbys(action.getPlayerId());
            System.out.println(ConsoleColor.CYAN + networkHandler.toString() + " logged out from: " + action.getPlayerId() + ConsoleColor.RESET);

            networkHandler.send(ActionHandler.toJson(action));
        }
        else {
            networkHandler.send(ActionHandler.toJson(new LogoutAction(action.getPlayerId())));
            throw new InvalidAction("Logout: invalid id to log out");
        }
    }
}
