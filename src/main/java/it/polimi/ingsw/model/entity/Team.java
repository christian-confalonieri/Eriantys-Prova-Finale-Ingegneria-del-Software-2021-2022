package it.polimi.ingsw.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final List<Player> players;
    private final Player towerPlayer;

    /**
     * Build a team with two players
     * The two players have to be already instantiated, with one player having the towers and the other not
     * @param players the list of players of the team
     */
    public Team(List<Player> players) {
        this.players = players;
        this.towerPlayer = players.stream().filter(p -> !p.getSchool().getTowers().isEmpty()).findAny().get();
    }

    public List<Tower> getTeamTowers() {
        return towerPlayer.getSchool().getTowers();
    }

    public List<Professor> getTeamProfessors() {
        List<Professor> teamProfessors = new ArrayList<>();
        for (Player p : players) {
            teamProfessors.addAll(p.getSchool().getProfessorTable());
        }
        return teamProfessors;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
