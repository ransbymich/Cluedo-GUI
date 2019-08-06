/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


// line 89 "model.ump"
// line 182 "model.ump"
public abstract class Entity {

    //------------------------
    // ENUMERATIONS
    //------------------------


    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Entity Attributes
    protected Type type;

    //Entity Associations
    protected Position position;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Entity(Type aType) {
        type = aType;
//        if (!setPosition(aPosition)) {
//            throw new RuntimeException("Unable to create Entity due to aPosition. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//        }
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

    /* Code from template association_GetOne */
    public Position getPosition() {
        return position;
    }

    /* Code from template association_SetUnidirectionalOne */
    public boolean setPosition(Position aNewPosition) {
        boolean wasSet = false;
        if (aNewPosition != null) {
            position = aNewPosition;
            wasSet = true;
        }
        return wasSet;
    }

    public void delete() {
        position = null;
    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "type" + "=" + (getType() != null ? !getType().equals(this) ? getType().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "position = " + (getPosition() != null ? Integer.toHexString(System.identityHashCode(getPosition())) : "null");
    }
}