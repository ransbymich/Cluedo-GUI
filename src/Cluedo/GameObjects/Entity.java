package Cluedo.GameObjects;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;

import java.awt.*;

// line 89 "model.ump"
// line 182 "model.ump"
public abstract class Entity {

    protected Type type;

    protected Position position;

    public Entity(Type aType) {
        type = aType;
    }

    public boolean setType(Type aType) {
        boolean wasSet = false;
        type = aType;
        wasSet = true;
        return wasSet;
    }

    /**
     * Gets the type of the entity
     * @return Gets the type of entity
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the position of the entity
     * @return The position of the entity
     */
    public String getName(){return this.getType().getName();}

    /* Code from template association_GetOne */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity
     * @param aNewPosition  The position to set
     * @return              Whether or not the position was set or not
     */
    public boolean setPosition(Position aNewPosition) {
        boolean wasSet = false;
        if (aNewPosition != null) {
            position = aNewPosition;
            wasSet = true;
        }
        return wasSet;
    }

    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "type" + "=" + (getType() != null ? !getType().equals(this) ? getType().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "position = " + (getPosition() != null ? Integer.toHexString(System.identityHashCode(getPosition())) : "null");
    }

    public abstract void render(Graphics g);
}