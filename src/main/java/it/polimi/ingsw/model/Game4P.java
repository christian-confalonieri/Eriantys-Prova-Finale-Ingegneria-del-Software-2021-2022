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
                    bag.movePawnTo(cloud, s);
                } catch (EmptyBagException e) {};

            }

    }

    /**
     * Check if the game is in an end situation and update the gameState if so
     *
     * @return true if the game has ended
     */
    @Override
    public boolean checkEndGame() {
        boolean finished =  // One team has finished his towers
                teams.stream().map(t -> t.getTeamTowers().size()).anyMatch(size -> size == 0) ||
                        // There are only 3 islands
                        islands.size() <= 3 ||
                        // The cards or the students finished and all the players finished their turns
                        (players.stream().map(p -> p.getHandCards().size()).anyMatch(size -> size == 0) ) //&& // TODO)
                        // The students in the bag finished and all the players finished their turns
                        || bag.isEmpty() //&& // TODO
                ;
        gameState.setEnded(finished);
        return finished;
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
        for (Team t : teamLeaderBoard) leaderBoard.addAll(t.getTeam());
        return leaderBoard;
    }
}
