package Cluedo.GameObjects;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.Exceptions.OutOfTilesException;
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

import static Cluedo.GUI.CluedoCanvas.X_OFFSET;
import static Cluedo.GUI.CluedoCanvas.Y_OFFSET;

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

    /**
     * Adds a tile that isn't a door, but is part of the room itself
     * @param tile the internal tile to be added
     */
    public void addInternalTile(RoomTile tile){
        internalTiles.add(tile);
    }

    /**
     * Checks whether a given player is in the room
     * @param player the player that we're querying
     * @return if the player is in the room
     */
    public boolean hasPlayer(Player player){
        return entities.contains(player);
    }

    /**
     * Adds a doorway tile
     * @param tile the tile to be added
     */
    public void addDoorTile(DoorTile tile){
        doorTiles.add(tile);
    }

    /**
     * @return the name of this room
     */
    public String getName(){
        return this.getType().getName();
    }

    /**
     * @return the doors for this room
     */
    public List<Position> getDoorPositions(){
        return doorTiles.stream().map(Tile::getPosition).collect(Collectors.toList());
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
     * Draw the doorways and players that are inside this room
     * @param g the graphics pane to draw on
     */
    public void render(Graphics g) {
        int size = CluedoCanvas.TILE_SIZE;

        for (DoorTile doorTile : doorTiles) {
            Position pos = doorTile.getPosition();
            Image img = GUI.ASSETS.get(type);

            g.drawImage(img,
                (pos.getX() * size) + X_OFFSET,
                (pos.getY() * size) + Y_OFFSET,
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
        if (entities.contains(entity)) {
            return false;
        }

        //Entities are players only at this point
        entities.add(entity);

        //Logic for allocating the player a position
        if (entity instanceof Player){
            List<Position> tilesAvailable = internalTiles.stream().map(Tile::getPosition).collect(Collectors.toList());

            tilesAvailable.removeAll(allocatedPositions);

            if (tilesAvailable.isEmpty()){
                throw new OutOfTilesException();
            }
            //Shuffle the available tiles, so the allocations are random
            Collections.shuffle(tilesAvailable);

            //get a position from the internal tiles list
            Position pos = tilesAvailable.get(0);

            //move the player to that position
            entity.setPosition(pos);


            //add that position to the list of allocated positions
            allocatedPositions.add(pos);
        }

        return true;
    }


    /**
     * removes an entity from the room
     * @param entity   The entity to remove
     * @return true if the removal was successful
     */
    public boolean removeEntity(Entity entity) {
        boolean wasRemoved = false;
        if (entities.contains(entity)) {
            //remove the entity from the room
            entities.remove(entity);

            //also remove the position of the player from the list of allocations
            if (entity instanceof Player){
                allocatedPositions.remove(entity.position);
            }

            wasRemoved = true;
        }
        return wasRemoved;
    }

    /**
     * @return string explaining the state of this room
     */
    public String toString() {
        StringBuilder ret = new StringBuilder();
        //append the name of the room
        ret.append(type.getName());
        //if it's empty, append that
        if (entities.isEmpty()){
            ret.append(" is empty\n");
            return ret.toString();
        } else {
            //if it has players, append all of their names
            List<Entity> players = getEntities().stream().filter((e)-> e instanceof Player).collect(Collectors.toList());
            ret.append(": ");

            for (Entity player : players) {
                ret.append(player.getType().getName());
            }
        }

        //return whatever was built
        ret.append("\n");
        return ret.toString();
    }

    /**
     * @return whether the room contains entities that are players or not
     */
    public boolean isEmpty() {
        return getEntities().stream().noneMatch((e) -> e instanceof Player);
    }
}