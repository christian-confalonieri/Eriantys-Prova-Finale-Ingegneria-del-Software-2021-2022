package it.polimi.ingsw.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {


    @Test
    void actionExecutor() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/actions/Action.json")));
        GameController controller = new GameController();
        controller.actionExecutor(json);
    }
}