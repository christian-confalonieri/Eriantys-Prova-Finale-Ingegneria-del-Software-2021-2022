package it.polimi.ingsw.model.power;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.GameCreator;
import it.polimi.ingsw.model.game.GameHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Christian Confalonieri
 */
class JesterTest {

    /**
     * @author Christian Confalonieri
     */
    @Test
    void power() throws InvalidNewGameException, IOException, InvalidRulesException {
        GameHandler gameHandler = createGame();

        Jester jester = new Jester(gameHandler);
        List<PowerCard> powerCards = new ArrayList<>();
        powerCards.add(jester);
        gameHandler.getGame().setPowerCards(powerCards);

        assertEquals(PowerType.JESTER,jester.getType());
        assertEquals(1,jester.getCost());
        assertEquals(6,jester.getStudents().size());

        List<Student> students1 = new ArrayList<>();
        for(int i=0;i<3;i++) {
            students1.add(jester.getStudents().get(i));
        }
        gameHandler.getGame().getEffectHandler().setChosenStudents1(students1);

        List<Student> students2 = new ArrayList<>();
        for(int i=0;i<3;i++) {
            students2.add(gameHandler.getCurrentPlayer().getSchool().getEntrance().get(i));
        }
        gameHandler.getGame().getEffectHandler().setChosenStudents2(students2);

        assert(jester.getStudents().containsAll(students1));
        assert(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students2));
        assertEquals(6,jester.getStudents().size());
        assertEquals(7,gameHandler.getCurrentPlayer().getSchool().getEntrance().size());

        jester.power();

        assert(jester.getStudents().containsAll(students2));
        assert(gameHandler.getCurrentPlayer().getSchool().getEntrance().containsAll(students1));
        assertEquals(6,jester.getStudents().size());
        assertEquals(7,gameHandler.getCurrentPlayer().getSchool().getEntrance().size());

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