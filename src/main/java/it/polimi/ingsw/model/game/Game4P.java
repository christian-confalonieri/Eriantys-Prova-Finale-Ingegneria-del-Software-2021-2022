package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.exceptions.InvalidNewGameException;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.School;
import it.polimi.ingsw.model.entity.Team;
import it.polimi.ingsw.model.entity.Tower;
import it.polimi.ingsw.model.enumeration.TowerColor;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.game.rules.GameRules;
import it.polimi.ingsw.server.PlayerLobby;

import java.util.*;

import java.util.stream.Collectors;

public class Game4P extends Game{

    private List<Team> teams;

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
     *
     * @param playersData The list of PlayerLobby objects, containing the name of the player and the wizard of the player
     * @param gameRules the class containing all the parameters for game creation
     */
    protected Game4P(List<PlayerLobby> playersData, GameRules gameRules) throws InvalidNewGameException {
        super(gameRules);

        if(playersData.size() != gameRules.cloudsRules.numberOfClouds)
            throw new InvalidNewGameException("Bad rules: number of players doesn't match number of clouds");

        // Creates the players
        players = new ArrayList<>();
        for (PlayerLobby pData : playersData) {
            players.add(new Player(pData.getPlayerId(), pData.getWizard(), new School(), null, gameRules.coinRules.startingCoinsPerPlayer));
        }


        // Create the players with their school and entry students
        for (Player player : players) {
            for (int i = 0; i < gameRules.studentsRules.startingStudentsEntrance; i++) {
                try {
                    player.getSchool().addEntrance(bag.pickStudent());
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }
            }
        }

        // Add the towers to the team leaders
        Player teamLeader = players.get(gameRules.teamRules.teamOne[0]);
        teamLeader.setTowerColor(TowerColor.WHITE);
        for (int i = 0; i < gameRules.towersRules.numberOfTowers; i++) {
            teamLeader.getSchool().addTower(new Tower(TowerColor.WHITE, teamLeader));
        }
        teamLeader = players.get(gameRules.teamRules.teamTwo[0]);
        teamLeader.setTowerColor(TowerColor.BLACK);
        for (int i = 0; i < gameRules.towersRules.numberOfTowers; i++) {
            teamLeader.getSchool().addTower(new Tower(TowerColor.BLACK, teamLeader));
        }

        // Creates the teams (Important to construct a team after assigning the towers)
        teams = new ArrayList<>();
        List<Player> teamOneList = new ArrayList<>();
        teamOneList.add(players.get(gameRules.teamRules.teamOne[0]));
        teamOneList.add(players.get(gameRules.teamRules.teamOne[1]));
        List<Player> teamTwoList = new ArrayList<>();
        teamTwoList.add(players.get(gameRules.teamRules.teamTwo[0]));
        teamTwoList.add(players.get(gameRules.teamRules.teamTwo[1]));
        teams.add(new Team(teamOneList));
        teams.add(new Team(teamTwoList));
    }

}
