package it.polimi.ingsw.client.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.action.*;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.model.game.Game;

public class ClientActionHandler {

    public static String toJson(Action action) {
        Gson gson = new Gson();
        return gson.toJson(action, action.getClass());
    }

    public static void actionServiceInvoker(Action action) {
        switch (action.getActionType()) {
            case LOGIN -> LoginService.login((LoginAction) action);
            case GETGAME -> GameService.getGame((GetGameAction) action);
            case ACK -> ackHandler((ACK) action);
        }
    }

    public static void ackHandler(ACK ack) {
        switch (ack.isAckOfAction()) {
            case LOGIN -> {
                if(!ack.isOk()) LoginService.failedLogin(ack);
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
            };
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new InvalidAction("Bad formatted JSON");
        } catch (NullPointerException e) { // action.getActionType is null
            throw new InvalidAction("Empty or bad action");
        }
    }
}
