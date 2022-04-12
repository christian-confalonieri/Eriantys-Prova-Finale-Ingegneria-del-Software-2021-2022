package it.polimi.ingsw.action;

import it.polimi.ingsw.model.entity.Cloud;

/**
 * @author Christian Confalonieri
 */
public class MoveCloudAction extends PlayAction{
    private final Cloud cloud;

    public MoveCloudAction(String playerId, Cloud cloud) {
        super(ActionType.MOVECLOUD, playerId);
        this.cloud = cloud;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
