package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.List;

public class Game4P extends Game{

    private List<Team> teams;

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
