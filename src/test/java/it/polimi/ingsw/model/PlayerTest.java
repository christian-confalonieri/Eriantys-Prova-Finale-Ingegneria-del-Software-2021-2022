package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.Card;
import org.junit.jupiter.api.Test;


public class PlayerTest {

    private final Player myPlayer1 = new Player();

    @Test
    void PlayCardTest () {

        myPlayer1.playCard(Card.ONE);

        assertEquals(9, myPlayer1.getHandCards().size());
    }
}
