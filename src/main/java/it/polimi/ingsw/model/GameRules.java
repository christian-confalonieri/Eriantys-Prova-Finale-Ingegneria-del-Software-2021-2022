package it.polimi.ingsw.model;

import com.google.gson.Gson;


public class GameRules {
    public IslandsRules islandsRules;
    public CloudsRules cloudsRules;
    public StudentsRules studentsRules;
    public TowersRules towersRules;
    public CoinRules coinRules;
    public PlayerRules playerRules;
    public boolean expertMode;
    public TeamRules teamRules;

    public static GameRules fromJson(String rulesJson) {
        Gson gson = new Gson();
        return gson.fromJson(rulesJson, GameRules.class);
    }
}



class IslandsRules {
    public final int numberOfIslands;

    public IslandsRules(int numberOfIslands) {
        this.numberOfIslands = numberOfIslands;
    }
}
class CloudsRules {
    public final int numberOfClouds;

    CloudsRules(int numberOfClouds) {
        this.numberOfClouds = numberOfClouds;
    }
}
class StudentsRules {
    public final int startingStudentsOnIsland;
    public final int startingStudentsEntrance;
    public final int turnStudents;

    StudentsRules(int startingStudentsOnIsland, int startingStudentsEntrance, int turnStudents) {
        this.startingStudentsOnIsland = startingStudentsOnIsland;
        this.startingStudentsEntrance = startingStudentsEntrance;
        this.turnStudents = turnStudents;
    }
}
class TowersRules {
    public final int numberOfTowers;

    TowersRules(int numberOfTowers) {
        this.numberOfTowers = numberOfTowers;
    }
}
class CoinRules {
    public final int startingCoinsPerPlayer;
    public final int startingBoardCoins;

    CoinRules(int startingCoinsPerPlayer, int startingBoardCoins) {
        this.startingCoinsPerPlayer = startingCoinsPerPlayer;
        this.startingBoardCoins = startingBoardCoins;
    }
}
class PlayerRules {
    public final int startingPlayer;

    PlayerRules(int startingPlayer) {
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