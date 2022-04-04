package it.polimi.ingsw.model.game.rules;

import com.google.gson.Gson;
import it.polimi.ingsw.exceptions.InvalidRulesException;


public class GameRules { // TODO VALIDATE INSTEAD OF CONSTRUCTORS
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


