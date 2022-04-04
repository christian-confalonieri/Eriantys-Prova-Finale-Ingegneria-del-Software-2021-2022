package it.polimi.ingsw.model.powers;

import it.polimi.ingsw.model.GameHandler;
import it.polimi.ingsw.model.Student;

import java.util.List;

public class Friar extends PowerCard {

    private List<Student> students;

    public Friar(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FRIAR);
        setCost(1);
    }

    @Override
    public void power() {
        super.power();

        //TODO

        getGameHandler().getGame().getEffectHandler().setEffectActive(false);
    }

}
