package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;

import java.util.SortedMap;

public class GameCreator {
    public static GameHandler createGame(SortedMap<String, Wizard> playersData, String rulesJson) throws InvalidNewGameException, InvalidRulesException {
        GameRules gameRules = GameRules.fromJson(rulesJson);
        switch (playersData.size()) {
            case 2:
                return new GameHandler(new Game(playersData, gameRules));
            case 3:
                return new GameHandler(new Game(playersData, gameRules));
            case 4:
                return new GameHandler4P(new Game4P(playersData, gameRules));
        }
        throw new InvalidNewGameException("Number of players not supported");
    }
}
