package Cluedo.GameObjects;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.Helpers.Type;

import java.util.*;
import java.util.stream.Collectors;

public class Room {

    private Type type;

    private List<Entity> entities;


    public Room(Type aType) {
        type = aType;
        entities = new ArrayList<>();
    }

    /**
     * Sets the room type
     * @param aType The room type to set it to
     */
    public boolean setType(Type aType) {
        boolean wasSet = false;
        type = aType;
        wasSet = true;
        return wasSet;
    }

    /**
     * Get the type of this room
     * @return The type of this room
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the collection of entities inside of the room
     * @return The entities in this room
     */
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * Gets the number of entities in this room
     * @return
     */
    public int numberOfEntities() {
        return entities.size();
    }

    /**
     * Checks whether or not the room has entities in it
     * @return Whether or not the room has entities in it or not
     */
    public boolean hasEntities() {
        return entities.size() > 0;
    }

    /**
     * Adds an entity to the room
     * @param aEntity   The entity to add to the room
     */
    public boolean addEntity(Entity aEntity) {
        boolean wasAdded = false;
        if (entities.contains(aEntity)) {
            return false;
        }
        entities.add(aEntity);
        wasAdded = true;
        return wasAdded;
    }

    /**
     * removes an entity from the room
     * @param aEntity   The entity to remove
     * @return
     */
    public boolean removeEntity(Entity aEntity) {
        boolean wasRemoved = false;
        if (entities.contains(aEntity)) {
            entities.remove(aEntity);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(type.getName());
        if (entities.isEmpty()){
            ret.append(" is empty\n");
            return ret.toString();
        } else {
            List<Entity> players = getEntities().stream().filter((e)-> e instanceof Player).collect(Collectors.toList());
            ret.append(": ");

            for (Entity player : players) {
                ret.append(player.getType().getName());
            }

        }

        ret.append("\n");
        return ret.toString();
    }

    public boolean isEmpty() {
        return getEntities().stream().filter((e) -> e instanceof Player).count() == 0;
    }
}