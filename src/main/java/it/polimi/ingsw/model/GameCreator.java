package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.InvalidNewGameException;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.SortedMap;

public class GameCreator {
    public static GameHandler createGame(SortedMap<String, Wizard> playersData, String rulesJson) throws InvalidNewGameException{
        GameRules gameRules = GameRules.fromJson(rulesJson);
        switch (playersData.size()) {
            case 2:
                return new GameHandler(new Game2P(playersData, gameRules));
            case 3:
                return new GameHandler(new Game3P(playersData, gameRules));
            case 4:
                return new GameHandler4P(new Game4P(playersData, gameRules));
        }
        throw new InvalidNewGameException();
    }
}
