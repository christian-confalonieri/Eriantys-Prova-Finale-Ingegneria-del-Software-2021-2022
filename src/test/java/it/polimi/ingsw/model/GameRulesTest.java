package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameRulesTest {

    @Test
    void fromJson() {
        String rulesJson = null;
        try {
            rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2Psimple.json")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameRules gameRules = GameRules.fromJson(rulesJson);
        assertNotNull(gameRules.playerRules);
    }
}