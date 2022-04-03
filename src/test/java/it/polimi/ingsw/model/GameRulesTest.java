package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidRulesException;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameRulesTest {

    @Test
    void fromJson() {
        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameRules gameRules = null;
        try {
            gameRules = GameRules.fromJson(rulesJson);
        } catch (InvalidRulesException e) {
            e.printStackTrace();
        }
        assertNotNull(gameRules.playerRules);
    }
}