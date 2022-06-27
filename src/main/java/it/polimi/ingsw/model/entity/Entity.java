package it.polimi.ingsw.model.entity;

import java.util.UUID;

/**
 * Entity is the super type of all the "table objects" in the game.
 * It defines a equal relation based of an Unique Identifier, so that
 * the relation is valid even after serialization of the objects.
 */
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
