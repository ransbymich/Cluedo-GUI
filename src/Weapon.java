/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


// line 104 "model.ump"
// line 194 "model.ump"
public class Weapon extends Entity {

    //------------------------
    // ENUMERATIONS
    //------------------------


    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Weapon(Type aType, Position aPosition) {
        super(aType);
    }

    //------------------------
    // INTERFACE
    //------------------------


    // line 107 "model.ump"
    public Type getType() {
        return type;
    }

}