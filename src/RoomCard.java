/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


// line 43 "model.ump"
// line 155 "model.ump"
public class RoomCard implements Card {

    //------------------------
    // ENUMERATIONS
    //------------------------


    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //RoomCard Attributes
    private Type type;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCard(Type aType) {
        type = aType;
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

    @Override
    public Type getType() {
        return type;
    }

    public void delete() {
    }


    public String toString() {
        return  type.getName();
//        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
//                "  " + "type" + "=" + (getType() != null ? !getType().equals(this) ? getType().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}