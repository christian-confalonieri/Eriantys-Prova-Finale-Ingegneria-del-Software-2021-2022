package it.polimi.ingsw.client.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.client.controller.services.NetworkService;
import it.polimi.ingsw.exceptions.InvalidAction;

public class ClientActionHandler {

    public static String toJson(Action action) {
        Gson gson = new Gson();
        return gson.toJson(action, action.getClass());
    }

    public static void actionServiceInvoker(Action action) {
        switch (action.getActionType()) {
            case LOGIN -> LoginService.login((LoginAction) action);
            case GETGAME -> LobbyService.getGame((GetGameAction) action);
            case ACK -> ackHandler((ACK) action);
            case PLAYCARD -> GameService.playCard((PlayCardAction) action);
            case MOVESTUDENTS -> GameService.moveStudents((MoveStudentsAction) action);
            case MOVEMOTHERNATURE -> GameService.moveMotherNature((MoveMotherNatureAction) action);
            case MOVECLOUD -> GameService.moveCloud((MoveCloudAction) action);
            case POWER -> GameService.power((PowerAction) action);
            case LOGOUT -> LoginService.logout((LogoutAction) action);
            case GETALLLOBBYS -> LobbyService.getAllLobbys((GetAllLobbysAction) action);
            // case NEWGAME -> // Client should recieve an ack
            case JOINGAME -> LobbyService.joinGame((JoinGameAction) action);
            case PING -> NetworkService.recvPing((PING) action);
            case PONG -> NetworkService.recvPong((PONG) action);
            case GAMEINTERRUPTED -> GameService.gameInterrupted((GameInterruptedAction) action);
        }
    }

    public static void ackHandler(ACK ack) {
        switch (ack.isAckOfAction()) {
            case LOGIN -> {
                if(!ack.isOk()) LoginService.failedLogin(ack);
            }
            case PLAYCARD -> {
                if(!ack.isOk()) GameService.failedPlayCard(ack);
            }
            case MOVESTUDENTS -> {
                if(!ack.isOk()) GameService.failedMoveStudents(ack);
            }
            case MOVEMOTHERNATURE -> {
                if(!ack.isOk()) GameService.failedMoveMotherNature(ack);
            }
            case MOVECLOUD -> {
                if(!ack.isOk()) GameService.failedMoveCloud(ack);
            }
            case POWER -> {
                if (!ack.isOk()) GameService.failedPower(ack);
            }
            case NEWGAME -> {
                if(ack.isOk()) LobbyService.newGameOk(ack);
                if(!ack.isOk()) LobbyService.newGameFailed(ack);
            }
            case JOINGAME -> {
                if(!ack.isOk()) LobbyService.joinGameFailed();
            }
        }
    }

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
                case ACK -> gson.fromJson(json, ACK.class);
                case GETALLLOBBYS -> gson.fromJson(json, GetAllLobbysAction.class);
                case PING -> gson.fromJson(json, PING.class);
                case PONG -> gson.fromJson(json, PONG.class);
                case GAMEINTERRUPTED -> gson.fromJson(json, GameInterruptedAction.class);
            };
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new InvalidAction("Bad formatted JSON");
        } catch (NullPointerException e) { // action.getActionType is null
            throw new InvalidAction("Empty or bad action");
        }
    }
}
