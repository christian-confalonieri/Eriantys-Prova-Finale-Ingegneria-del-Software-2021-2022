package it.polimi.ingsw.model;

import java.util.List;

public class Team {
    private final List<Player> team;

    public Team(List<Player> team) {
        this.team = team;
    }

    public List<Player> getTeam() {
        return team;
    }
}
