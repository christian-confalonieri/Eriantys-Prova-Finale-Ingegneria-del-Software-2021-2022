package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Student;

import java.util.List;

public class Princess extends PowerCard {

    private List<Student> students;

    public Princess(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.PRINCESS);
        setCost(2);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
