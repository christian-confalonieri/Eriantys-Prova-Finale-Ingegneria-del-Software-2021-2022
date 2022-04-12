package it.polimi.ingsw.model.power;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.game.rules.GameRules;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Christian Confalonieri
 */
class FriarTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power() throws InvalidNewGameException, InvalidRulesException, IOException {
        GameHandler gameHandler = createGame();

        Island island = gameHandler.getGame().getIslands().get(0);
        Friar friar = new Friar(gameHandler);

        assertEquals(PowerType.FRIAR,friar.getType());
        assertEquals(1,friar.getCost());
        assertEquals(4,friar.getStudents().size());

        List<Player> players = gameHandler.getOrderedTurnPlayers();
        // 0: Luigi, 1: Mario
        gameHandler.getGame().getEffectHandler().setChosenIsland(island);
        List<Student> students = new ArrayList<>();
        students.add(friar.getStudents().get(0));
        gameHandler.getGame().getEffectHandler().setChosenStudents1(students);

        friar.power();

        assertEquals(4,friar.getStudents().size());
        assertEquals(1,island.getStudents().size());
        assertEquals(students.get(0),island.getStudents().get(0));
    }

    private GameHandler createGame() throws IOException, InvalidNewGameException, InvalidRulesException {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Mario", Wizard.PURPLE);
        playerData.put("Luigi",Wizard.GREEN);
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameRules gameRules = GameRules.fromJson(rulesJson);
        GameHandler gameHandler = GameCreator.createGame(playerData, gameRules);
        return gameHandler;
    }
}