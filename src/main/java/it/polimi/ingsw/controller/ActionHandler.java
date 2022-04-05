package it.polimi.ingsw.controller;

import com.google.gson.Gson;

public class ActionHandler {

    public static Action fromJson(String json) {
        Gson gson = new Gson();
        Action action = gson.fromJson(json, Action.class);

        Action castedAction = null;
        switch (action.actionType) {
            case "login":
                castedAction = gson.fromJson(json, LoginAction.class);
                break;
        }

        return castedAction;
    }
}
