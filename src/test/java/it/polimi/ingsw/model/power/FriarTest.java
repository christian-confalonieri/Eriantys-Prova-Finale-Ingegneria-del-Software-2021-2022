package it.polimi.ingsw.model.power;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.entity.Island;
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

class FriarTest {

    @Test
    void power() throws InvalidNewGameException, InvalidRulesException, IOException {
        SortedMap<String, Wizard> playerData = new TreeMap<>();
        playerData.put("Mario", Wizard.PURPLE);
        playerData.put("Luigi",Wizard.GREEN);
        String rulesJson;
        rulesJson = new String(Files.readAllBytes(Paths.get("src/main/resources/Rules2P.json")));
        GameHandler gameHandler;
        gameHandler = GameCreator.createGame(playerData, rulesJson);

        Island island = new Island();
        Friar friar = new Friar(gameHandler);

        assertEquals(PowerType.FRIAR,friar.getType());
        assertEquals(1,friar.getCost());
        assertEquals(4,friar.getStudents().size());

        gameHandler.getGame().getEffectHandler().setChosenIsland(island);
        List<Student> students = new ArrayList<>();
        students.add(friar.getStudents().get(0));
        gameHandler.getGame().getEffectHandler().setChosenStudents1(students);

        friar.power();

        assertEquals(4,friar.getStudents().size());
        assertEquals(1,island.getStudents().size());
        assertEquals(students.get(0),island.getStudents().get(0));
    }
}