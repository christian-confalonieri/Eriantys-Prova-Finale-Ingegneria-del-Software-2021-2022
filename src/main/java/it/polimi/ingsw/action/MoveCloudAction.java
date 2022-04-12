package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Cloud;

/**
 * @author Christian Confalonieri
 */
public class MoveCloudAction extends PlayAction{
    Cloud cloud;
    public MoveCloudAction(ActionType actionType, String playerId, Cloud cloud) {
        super(actionType, playerId);
        this.cloud = cloud;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
