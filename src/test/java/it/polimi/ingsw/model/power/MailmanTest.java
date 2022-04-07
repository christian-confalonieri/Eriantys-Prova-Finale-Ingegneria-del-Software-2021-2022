package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MailmanTest {

    @Test
    void power() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Mailman mailman = new Mailman(gameHandler);

        assertEquals(PowerType.MAILMAN,mailman.getType());
        assertEquals(1,mailman.getCost());

        Island island = gameHandler.getGame().getIslands().get(0);
        gameHandler.getGame().getMotherNature().setIsland(island);


        assertEquals(gameHandler.getGame().getIslands().get(0), gameHandler.getGame().getMotherNature().isOn());

        gameHandler.getGame().getEffectHandler().setAdditionalMoves(0);
        mailman.power();
        assertEquals(gameHandler.getGame().getIslands().get(0), gameHandler.getGame().getMotherNature().isOn());


        gameHandler.getGame().getEffectHandler().setAdditionalMoves(1);
        mailman.power();
        assertEquals(gameHandler.getGame().getIslands().get(1), gameHandler.getGame().getMotherNature().isOn());

        gameHandler.getGame().getEffectHandler().setAdditionalMoves(2);
        mailman.power();
        assertEquals(gameHandler.getGame().getIslands().get(3), gameHandler.getGame().getMotherNature().isOn());

    }

    private GameHandler createGame() throws IOException, InvalidNewGameException, InvalidRulesException {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Mario", Wizard.PURPLE);
        playerData.put("Luigi",Wizard.GREEN);
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameHandler gameHandler = GameCreator.createGame(playerData, rulesJson);
        return gameHandler;
    }
}