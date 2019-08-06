/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import com.sun.jdi.InvalidTypeException;

import java.security.InvalidParameterException;

// line 137 "model.ump"
// line 221 "model.ump"
public class RoomTile extends Tile {

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomTile Attributes
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomTile(Position aPosition, Room aRoom) {
    super(aPosition);
    room = aRoom;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoom(Room aRoom) {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public Room getRoom() {
    return room;
  }

  public void delete() {
    super.delete();
  }


  public String toString() {
    switch (room.getType()) {
      case BALL_ROOM:
        return "BR";
      case KITCHEN:
        return "KT";
      case DINING_ROOM:
        return "DR";
      case LOUNGE:
        return "LG";
      case HALL:
        return "HL";
      case CONSERVATORY:
        return "CT";
      case BILLARD_ROOM:
        return "BL";
      case STUDY:
        return "ST";
      case LIBRARY:
        return "LB";
      default:
        return "er";
    }

  }
}