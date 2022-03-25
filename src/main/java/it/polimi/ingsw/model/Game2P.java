package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

public class Game2P extends Game{
    @Override
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < 3; i++) {
                try {
                    Student s = bag.pickStudent();
                    bag.movePawnTo(cloud, s);
                } catch (EmptyBagException e) {};

            }

    }
}
