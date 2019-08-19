package Cluedo.Tiles;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static Cluedo.GUI.CluedoCanvas.xOffset;
import static Cluedo.GUI.CluedoCanvas.yOffset;

public class DoorTile extends Tile {
    private Room room;

    /**
     * Default constructor
     * @param aPosition where the tile is located
     * @param aRoom the type of room it is
     */
    public DoorTile(Position aPosition, Room aRoom) {
        super(aPosition);
        room = aRoom;
        room.addEntryTile(this);
    }


    //drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
    @Override
    public void render(Graphics g) {
        Position pos = getPosition();

//        Image myImage = GUI.ASSETS.get(room.getType());

        Image myImage = GUI.ASSETS.get(Type.REVOLVER);

        int size = CluedoCanvas.TILE_SIZE;

        g.drawImage(myImage,
                (pos.getX() * size) + xOffset,
                (pos.getY() * size) + yOffset,
                size, size, null);
    }

    /**
     * @return true if there are players in this room
     */
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

    /**
     * prints the correct two character combo for the given type of room
     * @return
     */
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
            case BILLIARD_ROOM:
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