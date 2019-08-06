/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 37 "model.ump"
// line 150 "model.ump"
public class PlayerCard implements Card
{

  //------------------------
  // ENUMERATIONS
  //------------------------


  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerCard Attributes
  private Type type;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerCard(Type aType)
  {
    type = aType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(Type aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public Type getType()
  {
    return type;
  }

  public void delete()
  {}


  public String toString() {
    return type.getName();
  }
}