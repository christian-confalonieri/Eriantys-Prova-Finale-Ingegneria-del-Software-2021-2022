package it.polimi.ingsw.model;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.entity.*;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.TowerColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.power.EffectHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


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
