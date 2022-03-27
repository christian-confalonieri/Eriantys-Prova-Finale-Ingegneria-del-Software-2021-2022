package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.util.List;

public class GameCreator {
    public static Game createGame(List<String> names) throws InvalidNewGameException{
        switch (names.size()) {
            case 2:
                return new Game2P(names);
            case 3:
                return new Game3P(names);
            case 4:
                return new Game4P(names);
        }
        throw new InvalidNewGameException();
    }
}
