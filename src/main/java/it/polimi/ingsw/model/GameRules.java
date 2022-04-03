package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.InvalidRulesException;


public class GameRules {
    public IslandsRules islandsRules;
    public CloudsRules cloudsRules;
    public StudentsRules studentsRules;
    public TowersRules towersRules;
    public CoinRules coinRules;
    public PlayerRules playerRules;
    public boolean expertMode;
    public TeamRules teamRules;

    public static GameRules fromJson(String rulesJson) throws InvalidRulesException {
        Gson gson = new Gson();
        return gson.fromJson(rulesJson, GameRules.class);
    }
}



class IslandsRules {
    public final int numberOfIslands;

    public IslandsRules(int numberOfIslands) throws InvalidRulesException {
        if (numberOfIslands <= 3) throw new InvalidRulesException();
        this.numberOfIslands = numberOfIslands;
    }
}
class CloudsRules {
    public final int numberOfClouds;

    CloudsRules(int numberOfClouds) throws InvalidRulesException {
        if (numberOfClouds < 0) throw new InvalidRulesException();
        this.numberOfClouds = numberOfClouds;
    }
}
class StudentsRules {
    public final int startingStudentsOnIsland;
    public final int startingStudentsEntrance;
    public final int turnStudents;

    StudentsRules(int startingStudentsOnIsland, int startingStudentsEntrance, int turnStudents) throws InvalidRulesException {
        if(startingStudentsOnIsland < 0) throw new InvalidRulesException();
        this.startingStudentsOnIsland = startingStudentsOnIsland;
        if (startingStudentsEntrance < 0) throw new InvalidRulesException();
        this.startingStudentsEntrance = startingStudentsEntrance;
        if (turnStudents < 0) throw new InvalidRulesException();
        this.turnStudents = turnStudents;
    }
}
class TowersRules {
    public final int numberOfTowers;

    TowersRules(int numberOfTowers) throws InvalidRulesException {
        if(numberOfTowers <= 0) throw new InvalidRulesException();
        this.numberOfTowers = numberOfTowers;
    }
}
class CoinRules {
    public final int startingCoinsPerPlayer;
    public final int startingBoardCoins;

    CoinRules(int startingCoinsPerPlayer, int startingBoardCoins) throws InvalidRulesException {
        if (startingCoinsPerPlayer < 0) throw new InvalidRulesException();
        this.startingCoinsPerPlayer = startingCoinsPerPlayer;
        if (startingBoardCoins < 0) throw new InvalidRulesException();
        this.startingBoardCoins = startingBoardCoins;
    }
}
class PlayerRules {
    public final int startingPlayer;

    PlayerRules(int startingPlayer) throws InvalidRulesException {
        if (startingPlayer < 0) throw new InvalidRulesException();
        this.startingPlayer = startingPlayer;
    }
}
class TeamRules {
    public final int[] teamOne;
    public final int[] teamTwo;

    TeamRules(int[] teamOne, int[] teamTwo) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
    }
}