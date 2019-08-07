/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import com.sun.jdi.InvalidTypeException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomTile extends Tile {

    private Room room;


    public RoomTile(Position aPosition, Room aRoom) {
        super(aPosition);
        room = aRoom;
    }

    public boolean hasPlayers() {
        List entities = room.getEntities().stream().filter((e) -> e instanceof Player).collect(Collectors.toList());
        return entities.size() > 0;
    }

    /**
     * Gets the room this tile represents
     * @return The room this tile represents
     */
    public Room getRoom() {
        return room;
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