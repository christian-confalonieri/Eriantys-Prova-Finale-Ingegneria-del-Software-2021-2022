package it.polimi.ingsw.model.game.rules;

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
        GameRules rules = gson.fromJson(rulesJson, GameRules.class);
        rules.validate();
        return rules;
    }

    private void validate() throws InvalidRulesException {
        islandsRules.validate();
        cloudsRules.validate();
        studentsRules.validate();
        towersRules.validate();
        coinRules.validate();
        playerRules.validate();
        teamRules.validate();
    }
}


