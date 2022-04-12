package it.polimi.ingsw.action;

import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.power.EffectHandler;

/**
 * @author Christian Confalonieri
 */
public class PowerAction extends PlayAction{
    PowerType type;
    EffectHandler effectHandler;
    public PowerAction(ActionType actionType, String playerId, PowerType type, EffectHandler effectHandler) {
        super(actionType, playerId);
        this.type = type;
        this.effectHandler = effectHandler;
    }

    public PowerType getType() {
        return type;
    }

    public void setType(PowerType type) {
        this.type = type;
    }

    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public void setEffectHandler(EffectHandler effectHandler) {
        this.effectHandler = effectHandler;
    }
}
