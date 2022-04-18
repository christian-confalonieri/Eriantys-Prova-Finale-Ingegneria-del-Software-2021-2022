package it.polimi.ingsw.model.entity;

import java.util.UUID;

public abstract class Entity {
    private final String uuid;

    @Override
    public boolean equals(Object obj) {
        return this.getClass().equals(obj.getClass()) && this.uuid.equals(((Entity) obj).uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public Entity() {
        this.uuid = UUID.randomUUID().toString();
    }
}
