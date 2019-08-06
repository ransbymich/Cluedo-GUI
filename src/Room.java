/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;
import java.util.stream.Collectors;

// line 83 "model.ump"
// line 176 "model.ump"
public class Room {

    //------------------------
    // ENUMERATIONS
    //------------------------

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Room Attributes
    private Type type;

    //Room Associations
    private List<Entity> entities;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(Type aType) {
        type = aType;
        entities = new ArrayList<Entity>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setType(Type aType) {
        boolean wasSet = false;
        type = aType;
        wasSet = true;
        return wasSet;
    }

    public Type getType() {
        return type;
    }

    /* Code from template association_GetMany */
    public Entity getEntity(int index) {
        Entity aEntity = entities.get(index);
        return aEntity;
    }

    public List<Entity> getEntities() {
        List<Entity> newEntities = Collections.unmodifiableList(entities);
        return newEntities;
    }

    public int numberOfEntities() {
        int number = entities.size();
        return number;
    }

    public boolean hasEntities() {
        boolean has = entities.size() > 0;
        return has;
    }

    public int indexOfEntity(Entity aEntity) {
        int index = entities.indexOf(aEntity);
        return index;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfEntities() {
        return 0;
    }

    /* Code from template association_AddUnidirectionalMany */
    public boolean addEntity(Entity aEntity) {
        boolean wasAdded = false;
        if (entities.contains(aEntity)) {
            return false;
        }
        entities.add(aEntity);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeEntity(Entity aEntity) {
        boolean wasRemoved = false;
        if (entities.contains(aEntity)) {
            entities.remove(aEntity);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addEntityAt(Entity aEntity, int index) {
        boolean wasAdded = false;
        if (addEntity(aEntity)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfEntities()) {
                index = numberOfEntities() - 1;
            }
            entities.remove(aEntity);
            entities.add(index, aEntity);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveEntityAt(Entity aEntity, int index) {
        boolean wasAdded = false;
        if (entities.contains(aEntity)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfEntities()) {
                index = numberOfEntities() - 1;
            }
            entities.remove(aEntity);
            entities.add(index, aEntity);
            wasAdded = true;
        } else {
            wasAdded = addEntityAt(aEntity, index);
        }
        return wasAdded;
    }

    public void delete() {
        entities.clear();
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