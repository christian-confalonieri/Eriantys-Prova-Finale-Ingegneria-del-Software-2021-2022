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
     * We have assumed that this power acts only in case the other players already have the color tower to compare.
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
        PawnColor color;

        Map<Professor, Player> chosenProfessors = new HashMap<>();

        for(Player player : players) {
            playerSchool = player.getSchool();
            playerProfessors = playerSchool.getProfessorTable();

            for(Professor professor : playerProfessors) {
                color = professor.getColor();
                if(effectPlayerSchool.getStudentsDiningRoom(color).size() == playerSchool.getStudentsDiningRoom(color).size()) {
                    chosenProfessors.put(professor,player);

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

        Map<Professor,Player> chosenProfessors = getGameHandler().getGame().getEffectHandler().getChosenProfessors();
        Player effectPlayer = getGameHandler().getGame().getEffectHandler().getEffectPlayer();
        for(Professor professor : chosenProfessors.keySet()) {
            effectPlayer.getSchool().removeProfessor(professor);
            chosenProfessors.get(professor).getSchool().addProfessor(professor);
        }
    }

}
