package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.action.PlayCardAction;
import it.polimi.ingsw.action.LoginAction;
import it.polimi.ingsw.exceptions.InvalidAction;

public class ActionHandler {

    public static Action fromJson(String json) throws InvalidAction {
        Gson gson = new Gson();
        try {
            Action action = gson.fromJson(json, Action.class);

            Action castedAction = null;
            switch (action.getActionType()) {
                case LOGIN:
                    castedAction = gson.fromJson(json, LoginAction.class);
                    break;
                case PLAYCARD:
                    castedAction = gson.fromJson(json, PlayCardAction.class);
                    break;
            }

            return castedAction;
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new InvalidAction("Invalid json conversion");
        }
    }
}
