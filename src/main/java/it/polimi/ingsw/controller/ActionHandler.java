package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.cli.ConsoleColor;
import it.polimi.ingsw.controller.services.GameService;
import it.polimi.ingsw.controller.services.LobbyService;
import it.polimi.ingsw.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.server.Server;

public class ActionHandler {

    /**
     * Builds an action object from a json
     *
     * @param json the json to convert
     * @return an action object converted from the json
     * @throws InvalidAction if the action is bad formatted or is of an unknown type
     */
    public static Action fromJson(String json) throws InvalidAction {
        Gson gson = new Gson();
        try {
            Action action = gson.fromJson(json, Action.class);

            return switch (action.getActionType()) {
                case LOGIN -> gson.fromJson(json, LoginAction.class);
                case LOGOUT -> gson.fromJson(json, LogoutAction.class);
                case PLAYCARD -> gson.fromJson(json, PlayCardAction.class);
                case NEWGAME -> gson.fromJson(json, NewGameAction.class);
                case MOVESTUDENTS -> gson.fromJson(json, MoveStudentsAction.class);
                case MOVEMOTHERNATURE -> gson.fromJson(json, MoveMotherNatureAction.class);
                case MOVECLOUD -> gson.fromJson(json, MoveCloudAction.class);
                case POWER -> gson.fromJson(json, PowerAction.class);
                case JOINGAME -> gson.fromJson(json, JoinGameAction.class);
                case GETGAME -> gson.fromJson(json, GetGameAction.class);
                case ACK -> throw new InvalidAction("ACK Recieved by the server");
                case GETALLLOBBYS -> gson.fromJson(json, GetAllLobbysAction.class);
            };
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new InvalidAction("Bad formatted JSON");
        } catch (NullPointerException e) { // action.getActionType is null
            throw new InvalidAction("Empty or bad action");
        }
    }

    public static String toJson(Action action) {
        Gson gson = new Gson();
        return gson.toJson(action, action.getClass());
    }

    /**
     * Calls the services that are associated to the action if the action is not ignorable
     * An action is ignorable if
     *  - It's a PlayAction but the player is not logged in any game or is logged in-game but it's not his turn
     *  - It's a MenuAction but the player is in game
     * @param action the action to execute
     * @param clientNet the ClientNetworkHandler from where the action was received
     */

    public static void actionServiceInvoker(Action action, ClientNetworkHandler clientNet) throws InvalidAction {
        if (action.getActionType() != ActionType.LOGIN && !Server.getInstance().isAssigned(action.getPlayerId())) { // The playerId sent is not online
            throw new InvalidAction("Action is referencing a player not online");
        }

        if (ignorePlayAction(action)) {
            throw new InvalidAction("PlayAction ignored");
        }
        if (ignoreMenuAction(action)) {
            throw new InvalidAction("MenuAction ignored");
        }

        System.out.println(ConsoleColor.BLUE + clientNet.toString() + " Action services invoked" + ConsoleColor.RESET);
        switch (action.getActionType()) {
            case LOGIN -> LoginService.clientLogin((LoginAction) action, clientNet);
            case LOGOUT -> LoginService.clientLogout((LogoutAction) action, clientNet);
            case PLAYCARD -> GameService.playCard((PlayCardAction) action);
            case NEWGAME -> LobbyService.newGame((NewGameAction) action);
            case MOVESTUDENTS -> GameService.moveStudents((MoveStudentsAction) action);
            case MOVEMOTHERNATURE -> GameService.moveMotherNature((MoveMotherNatureAction) action);
            case MOVECLOUD -> GameService.moveCloud((MoveCloudAction) action);
            case POWER -> GameService.power((PowerAction) action);
            case JOINGAME -> LobbyService.joinGame((JoinGameAction) action);
            case GETGAME -> LobbyService.getGame((GetGameAction) action);
            case GETALLLOBBYS -> LobbyService.getAllLobbys((GetAllLobbysAction) action);
        }
    }

    /**
     * Returns true if the action is a playAction and is sent by a client not logged in any game or
     * is logged in a game, but he's not the current player
     *
     * @param action the action sent by a client
     * @return true if the action has to be ignored
     */
    private static boolean ignorePlayAction (Action action) {
        if (action instanceof PlayAction) {
            if (Server.getInstance().isInGame(action.getPlayerId())) {
                String actionPlayerName = Server.getInstance().getInGamePlayer(action.getPlayerId()).getName();
                String currentPlayerName = Server.getInstance().getGameHandler(action.getPlayerId()).getCurrentPlayer().getName();

                return !actionPlayerName.equals(currentPlayerName); // returns if the player is the current player
            }
            else return true; // Player is not in a game
        }
        return false; // Not a play action
    }

    /**
     * Return true if the action is a menu action and sent by a client that is logged in a game
     * @param action the action sent by the client
     * @return true if the action has to be ignored
     */
    private static boolean ignoreMenuAction (Action action) {
        if (action instanceof MenuAction) {
            // return if player is in a game
            return Server.getInstance().isInGame(action.getPlayerId());
        }
        return false; // Not a menu action
    }
}
