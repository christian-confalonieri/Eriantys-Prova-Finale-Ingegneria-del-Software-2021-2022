package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game4P extends Game{

    private List<Team> teams;

    @Override
    public void refillClouds() {
        for (Cloud cloud : clouds)
            for (int i = 0; i < 3; i++) {
                try {
                    Student s = bag.pickStudent();
                    cloud.addStudent(s);
                } catch (EmptyBagException e) {}

            }

    }

    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Get the current game leaderboard.
     * Can be used in the middle of the game too.
     *
     * The player leaderboard is calculated according to teams rules
     * The first two players are the leading team, where the last 2 are the last team.
     *
     * @return the game leaderboard
     */
    public List<Player> getLeaderBoard() {
        List<Team> teamLeaderBoard = teams.stream()
                .sorted((t1, t2) -> {
                    if(t1.getTeamTowers().size() == t2.getTeamTowers().size())
                        return (t1.getTeamProfessors().size() > t2.getTeamProfessors().size()) ? 1 : -1;
                    return (t1.getTeamTowers().size() == t2.getTeamTowers().size()) ? 1 : -1;
                }).collect(Collectors.toList());

        List<Player> leaderBoard = new ArrayList<>();
        for (Team t : teamLeaderBoard) leaderBoard.addAll(t.getPlayers());
        return leaderBoard;
    }


    /**
     * Construct and initialize a 4 player game
     * @param playerNames The list of the player names
     */
    protected Game4P(List<String> playerNames) {

    }

}
