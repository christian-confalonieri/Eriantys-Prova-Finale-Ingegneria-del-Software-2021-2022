package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.util.ArrayList;
import java.util.List;

public class Game2P extends Game{
    @Override
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < 3; i++) {
                try {
                    Student s = bag.pickStudent();
                    bag.movePawnTo(cloud, s);
                } catch (EmptyBagException e) {}

            }
    }

    /**
     * Construct and initialize a 2 player game
     *
     * @param playerNames The list of the player names
     */
    public Game2P(List<String> playerNames) throws InvalidNewGameException {
        int nOfIsland = 12;
        int nOfStartingStudents = PawnColor.values().length * 2;
        int nOfTotalStudents = PawnColor.values().length * 26;
        int nOfClouds = 2;
        int nOfTowersPerColor = 8;
        int nOfProfessors = PawnColor.values().length;
        int nOfStudentsInEntry = 7;


        islands = new ArrayList<>();
        for (int i = 0; i < nOfIsland; i++) {
            islands.add(new Island());
        }

        motherNature = new MotherNature(islands.get(0));

        List<Student> startingStudents = Student.generateNStudents(nOfStartingStudents);
        if (startingStudents == null) throw new InvalidNewGameException();

        bag = new Bag(startingStudents);
        for (Island island : islands) {
            try {
                island.addPawn(bag.pickStudent());
            } catch (EmptyBagException e) {
                e.printStackTrace();
            }
        }
        // Add all pawns of island 0 and 6 back to the bag
        islands.get(0).(bag, islands.get(0).getStudents().get(0));



    }
}
