package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.entity.School;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.game.GameHandler;
import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.entity.Professor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EFFECT: During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.
 *
 * @author Christian Confalonieri
 */
public class Farmer extends PowerCard {

    private Map<Player, Professor> movedProfessors;

    /**
     * In the constructor the character type and its usage cost is set.
     *
     * @param gameHandler
     * @author Christian Confalonieri
     */
    public Farmer(GameHandler gameHandler) {
        super(gameHandler);
        setType(PowerType.FARMER);
        setCost(2);
    }

    /**
     * This method should be activated after having moved the students from the entrance to the dining room
     * (and having called the professorRelocate method),
     * in this way it rechecks the schools and relocates the professors even if the students of the same color are equal in number.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void power() {
        super.power();

        Player effectPlayer = getGameHandler().getGame().getEffectHandler().getEffectPlayer();
        School effectPlayerSchool = effectPlayer.getSchool();

        List<Player> players = new ArrayList<>(getGameHandler().getOrderedTurnPlayers());
        players.remove(effectPlayer);

        List<Professor> playerProfessors;
        School playerSchool;

        Map<Player, List<Professor>> chosenProfessors = new HashMap<>();

        for(Player player : players) {
            playerSchool = player.getSchool();
            playerProfessors = playerSchool.getProfessorTable();

            for(Professor professor: playerProfessors) {
                PawnColor color = professor.getColor();
                if(effectPlayerSchool.getStudentsDiningRoom(color).size() == playerSchool.getStudentsDiningRoom(color).size()) {
                    List<Professor> professors = chosenProfessors.get(player);
                    professors.add(professor);
                    chosenProfessors.put(player,professors);

                    playerSchool.removeProfessor(professor);
                    effectPlayerSchool.addProfessor(professor);
                }
            }
        }
        getGameHandler().getGame().getEffectHandler().setChosenProfessors(chosenProfessors);

    }

    /**
     * At the end of the turn it relocates the professors moved via this power to their respective owners.
     *
     * @author Christian Confalonieri
     */
    @Override
    public void endPower() {
        super.endPower();

        Map<Player, List<Professor>> chosenProfessors = getGameHandler().getGame().getEffectHandler().getChosenProfessors();
        List<Professor> professors;
        Player effectPlayer = getGameHandler().getGame().getEffectHandler().getEffectPlayer();
        List<Player> players = new ArrayList<>();
        players.addAll(getGameHandler().getOrderedTurnPlayers());
        players.remove(effectPlayer);

        while(!chosenProfessors.isEmpty()){
            for(Player player : players) {
                if(chosenProfessors.containsKey(player)) {
                    professors = chosenProfessors.get(player);
                    for(Professor professor : professors) {
                        getGameHandler().getGame().getEffectHandler().getEffectPlayer().getSchool().removeProfessor(professor);
                        player.getSchool().addProfessor(professor);
                    }
                }
            }
        }
    }

}
