/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 126 "model.ump"
// line 211 "model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private Position position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(Position aPosition)
  {
    position = aPosition;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPosition(Position aPosition){
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public Position getPosition() {
    return position;
  }

  public boolean isPlayer(){
    Tile item = this;
    if(this instanceof EmptyTile){
      if (((EmptyTile) item).getPlayer() != null){
        return true;
      }
    }
    return false;
  }

  public void delete(){}


//  public String toString() {
//    return "not being cool";
//  }
}