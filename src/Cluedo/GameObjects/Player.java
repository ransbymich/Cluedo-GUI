package Cluedo.GameObjects;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.Board;
import Cluedo.GUI.CluedoCanvas;
import Cluedo.Helpers.Position;
import Cluedo.Tiles.DoorTile;
import Cluedo.Helpers.Type;

import java.awt.*;
import java.util.*;
import java.util.List;

import static Cluedo.GUI.CluedoCanvas.X_OFFSET;
import static Cluedo.GUI.CluedoCanvas.Y_OFFSET;

public class Player extends Entity {

    private boolean isInPlay = true;

    private List<Type> hand;

    public Player(Type aType) {
        super(aType);
        hand = new ArrayList<Type>();
    }

    public void clearHand(){
        hand.clear();
    }

    /**
     * Sets whether or not the player is in player or not
     * @param aIsInPlay The value to set it to
     */
    public boolean setIsInPlay(boolean aIsInPlay) {
        isInPlay = aIsInPlay;
        return true;
    }

    /**
     * Gets whether or not the player is in play or not.
     * @return  Whether or not the player is in play or not
     */
    public boolean getIsInPlay() {
        return isInPlay;
    }

    /**
     * Gets the hand of the player
     * @return  The hand of the player
     */
    public List<Type> getHand() {
        return Collections.unmodifiableList(hand);
    }

    /**
     * Checks whether or not the player is in a room or not
     * @param board The board in it's current state
     * @return  Whether or not the player is in a room.
     */
    public boolean inRoom(Board board){
        //Is the player on a Cluedo.Tiles.RoomTile?
        return board.getBoard()[position.getY()][position.getX()] instanceof DoorTile;
    }

    /**
     * Adds a card to the players card
     * @param aHand The card to add
     */
    public boolean addHand(Type aHand) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            return false;
        }
        hand.add(aHand);
        wasAdded = true;
        return wasAdded;
    }

    /**
     * Get the type of the player
     * @return  The type of the player
     */
    public Type getType() {
        return type;
    }

    public String getName(){return getType().getName();}

    public String toString() {
        return super.toString() + "[" + "isInPlay" + ":" + getIsInPlay() + "]";
    }

    @Override
    public void render(Graphics g) {
        Position pos = getPosition();
        int size = CluedoCanvas.TILE_SIZE;

        g.setColor(this.getType().getColor());
        g.fillOval((pos.getX() * size) + X_OFFSET,
                (pos.getY() * size) + Y_OFFSET,
                size, size);
    }
}