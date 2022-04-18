package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Cloud;

/**
 * @author Christian Confalonieri
 */
public class MoveCloudAction extends PlayAction{
    private final String cloudUUID;

    public MoveCloudAction(String playerId, String cloudUUID) {
        super(ActionType.MOVECLOUD, playerId);
        this.cloudUUID = cloudUUID;
    }

    public String getCloudUUID() {
        return cloudUUID;
    }
}
