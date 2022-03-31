package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHandlerTest {

    @Test
    void advance() {
    }

    @Test
    void TwoPlayerMatch() {
        List<String> names = new ArrayList<>();
        names.add("Pippo");
        names.add("Topolino");
        GameHandler gameHandler = null;
        try {
            gameHandler = GameCreator.createGame(names);
        } catch (InvalidNewGameException e) {
            e.printStackTrace();
        }
        Game board = gameHandler.getGame();
        assertNotNull(board);
    }
}