package Cluedo.GameObjects;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.GUI;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.RoomTile;
import Cluedo.Tiles.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static Cluedo.GUI.CluedoCanvas.xOffset;
import static Cluedo.GUI.CluedoCanvas.yOffset;

public class Room {

    private Type type;

    private List<Entity> entities;
    private List<RoomTile> internalTiles;
    private List<DoorTile> doorTiles;
    private List<Position> allocatedPositions;


    public Room(Type aType) {
        type = aType;
        entities = new ArrayList<>();
        internalTiles = new ArrayList<>();
        doorTiles = new ArrayList<>();
        allocatedPositions = new ArrayList<>();
    }

    public void addInternalTile(RoomTile tile){
        internalTiles.add(tile);
//        System.out.println(getName() + " internalTiles: " + internalTiles.size());
    }

    public void addEntryTile(DoorTile tile){
        doorTiles.add(tile);
//        System.out.println(getName() + " entryTiles: " + doorTiles.size());
    }

    public String getName(){
        return this.getType().getName();
    }

    /**
     * Sets the room type
     * @param aType The room type to set it to
     */
    public boolean setType(Type aType) {
        boolean wasSet = false;
        type = aType;
        wasSet = true;
        return wasSet;
    }


    /**
     * Render the items related to this room
     * @param g the graphics pane to draw on
     */
    public void render(Graphics g) {
        int size = CluedoCanvas.TILE_SIZE;

        for (DoorTile doorTile : doorTiles) {
            Position pos = doorTile.getPosition();
            Image img = GUI.ASSETS.get(type);

            g.drawImage(img,
                (pos.getX() * size) + xOffset,
                (pos.getY() * size) + yOffset,
                size, size, null);
        }

    }

    /**
     * Get the type of this room
     * @return The type of this room
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the collection of entities inside of the room
     * @return The entities in this room
     */
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /**
     * Gets the number of entities in this room
     * @return
     */
    public int numberOfEntities() {
        return entities.size();
    }

    /**
     * Checks whether or not the room has entities in it
     * @return Whether or not the room has entities in it or not
     */
    public boolean hasEntities() {
        return entities.size() > 0;
    }

    /**
     * Adds an entity to the room
     * @param entity   The entity to add to the room
     */
    public boolean addEntity(Entity entity) {
        boolean wasAdded = false;

        if (entities.contains(entity)) {
            return false;
        }

        //Entities are players only at this point
        entities.add(entity);
//        if(internalTiles.isEmpty()) return false;

        System.out.println("moving entity to place within room");
        if (entity instanceof Player){
            List<Position> tilesAvailable = internalTiles.stream().map(Tile::getPosition).collect(Collectors.toList());
//            System.out.println(tilesAvailable);

            tilesAvailable.removeAll(allocatedPositions);

            if (tilesAvailable.isEmpty()){
                System.out.println("NO TILES!");
                return false;
            }

            Collections.shuffle(tilesAvailable);
            //get a position from the internal tiles list
            Position pos = tilesAvailable.get(0);

            //move the player to that position
            entity.setPosition(pos);

            System.out.println(entity.getName() + " = " + entity.position);

            //add that position to the list of allocated positions
            allocatedPositions.add(pos);
        }


        wasAdded = true;
        return wasAdded;
    }


    /**
     * removes an entity from the room
     * @param entity   The entity to remove
     * @return
     */
    public boolean removeEntity(Entity entity) {
        boolean wasRemoved = false;
        if (entities.contains(entity)) {


            entities.remove(entity);
            if (entity instanceof Player){
                //remove the position from the list of allocated position, it is no longer allocated
                Position pos = entity.position;

                allocatedPositions.remove(pos);
            }

            wasRemoved = true;
        }
        return wasRemoved;
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
        return getEntities().stream().noneMatch((e) -> e instanceof Player);
    }
}