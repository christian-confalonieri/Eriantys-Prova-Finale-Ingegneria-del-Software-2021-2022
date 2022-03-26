package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private final List<Player> team;
    private final Player towerPlayer;

    /**
     * Build a team with two players
     * The two players have to be alreay instatiated, with one player having the towers and the other not
     * @param team the list of players of the team
     */
    public Team(List<Player> team) {
        this.team = team;
        this.towerPlayer = team.stream().filter(p -> !p.getSchool().getTowers().isEmpty()).findAny().get();
    }

    public List<Tower> getTeamTowers() {
        return towerPlayer.getSchool().getTowers();
    }

    public List<Professor> getTeamProfessors() {
        List<Professor> teamProfessors = new ArrayList<>();
        for (Player p : team) {
            teamProfessors.addAll(p.getSchool().getProfessorTable());
        }
        return teamProfessors;
    }

    public List<Player> getTeam() {
        return team;
    }
}
