package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Student;

import java.util.List;

public class Jester extends PowerCard {

    private List<Student> students;

    public Jester(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.JESTER);
        setCost(1);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
