package it.polimi.ingsw.controller;

import it.polimi.ingsw.action.*;
import it.polimi.ingsw.exceptions.InvalidAction;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ActionHandlerTest {

    @Test
    void fromJson() throws IOException, InvalidAction {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/actions/LoginTestMessage.json")));
        Action action = ActionHandler.fromJson(json);
        assertInstanceOf(LoginAction.class, action);
        LoginAction loginAction = (LoginAction) action;
        assertEquals(loginAction.getPlayerId(), "abcd-1234");
        assertNotNull(action);
    }

    @Test
    void toJson() throws IOException, InvalidRulesException {
        String playerId = "leonardoa00";
        String lobbyId = "lobbyid-example-unique-identifier";
        Wizard wizard = Wizard.BLUE;
        String rulesFile = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesFile);


        LoginAction loginAction = new LoginAction(playerId);
        // JoinGameAction joinGameAction = new JoinGameAction(playerId, lobbyId, wizard); TODO
        NewGameAction newGameAction = new NewGameAction(playerId, 2, gameRules, wizard);
        LogoutAction logoutAction = new LogoutAction(playerId);

        String json = ActionHandler.toJson(logoutAction);
        System.out.println(json);
    }

    @Test
    void actionServiceInvoker() {
    }
}