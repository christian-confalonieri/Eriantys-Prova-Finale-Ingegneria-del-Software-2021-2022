package it.polimi.ingsw.controller;


import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameController {
    public void actionExecutor(String json) {
        Action action = null;
        try {
            action = ActionHandler.fromJson(json);
            action.execute();
        } catch (InvalidAction e) {
            e.printStackTrace();
        }
    }

}
