package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.util.List;

public class GameCreator {
    public static GameHandler createGame(List<String> names) throws InvalidNewGameException{
        switch (names.size()) {
            case 2:
                return new GameHandler(new Game2P(names));
            case 3:
                return new GameHandler(new Game3P(names));
            case 4:
                return new GameHandler4P(new Game4P(names));
        }
        throw new InvalidNewGameException();
    }
}
