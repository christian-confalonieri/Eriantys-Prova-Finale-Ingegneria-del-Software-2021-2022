package it.polimi.ingsw.controller;

import it.polimi.ingsw.action.Action;
import it.polimi.ingsw.action.LoginAction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ActionHandlerTest {

    @Test
    void fromJson() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/actions/Action.json")));
        Action action = ActionHandler.fromJson(json);
        assertInstanceOf(LoginAction.class, action);
        LoginAction loginAction = (LoginAction) action;
        assertEquals(loginAction.getPlayerId(), "abcd-1234");
        assertNotNull(action);
    }
}