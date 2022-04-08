package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.action.ActionPlayCard;
import it.polimi.ingsw.action.LoginAction;

public class ActionHandler {

    public static Action fromJson(String json) {
        Gson gson = new Gson();
        Action action = gson.fromJson(json, Action.class);

        Action castedAction = null;
        switch (action.getActionType()) {
            case "login":
                castedAction = gson.fromJson(json, LoginAction.class);
                break;
            case "playcard":
                castedAction = gson.fromJson(json, ActionPlayCard.class);
                break;
        }

        return castedAction;
    }
}
