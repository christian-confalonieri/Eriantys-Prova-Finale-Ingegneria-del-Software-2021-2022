package it.polimi.ingsw.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameController {
    public void actionListener() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/actions/Action.json")));
        Action action = ActionHandler.fromJson(json);
        action.execute();
    }

}
