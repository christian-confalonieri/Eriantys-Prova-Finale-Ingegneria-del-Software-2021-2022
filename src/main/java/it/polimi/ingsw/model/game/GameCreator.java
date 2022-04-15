package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.exceptions.InvalidRulesException;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;

import java.util.List;
import java.util.SortedMap;

public class GameCreator {
    public static GameHandler createGame(List<PlayerLobby> playersData, GameRules gameRules) throws InvalidNewGameException {
        switch (playersData.size()) {
            case 2:
                return GameHandler.gameHandlerBuilder(new Game(playersData, gameRules));
            case 3:
                return GameHandler.gameHandlerBuilder(new Game(playersData, gameRules));
            case 4:
                return GameHandler4P.gameHandlerBuilder4P(new Game4P(playersData, gameRules));
        }
        throw new InvalidNewGameException("Number of players not supported");
    }
}
