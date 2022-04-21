package it.polimi.ingsw.model;

import it.polimi.ingsw.model.entity.Island;
import it.polimi.ingsw.model.entity.MotherNature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MotherNatureTest {

    private final Island myIsland1 = new Island();
    private final MotherNature myMN1 = new MotherNature(myIsland1);

    @Test
    void isOnTest () {
        Island res = myMN1.isOn();
        assertEquals(res, myIsland1);
    }

    @Test
    void moveTest () {
        assertEquals(myMN1.move(1), myIsland1.getNextIsland());
    }

}
