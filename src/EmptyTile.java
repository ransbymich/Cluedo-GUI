/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import com.sun.jdi.InvalidTypeException;

// line 131 "model.ump"
// line 216 "model.ump"
public class EmptyTile extends Tile {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //EmptyTile Attributes
    private Player player;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public EmptyTile(Position aPosition, Player aPlayer) {
        super(aPosition);
        player = aPlayer;
        if(aPlayer != null){
            aPlayer.setPosition(aPosition);
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setPlayer(Player aPlayer) {
        boolean wasSet = false;
        player = aPlayer;
        wasSet = true;
        return wasSet;
    }

    public Player getPlayer() {
        return player;
    }

    public void delete() {
        super.delete();
    }

  public String toString() {
    if (player == null) return "__";

        switch (player.getType()) {
            case COL_MUSTARD:
                return "MU";
            case MRS_WHITE:
                return "WH";
            case MR_GREEN:
                return "GR";
            case MRS_PEACOCK:
                return "PC";
            case PROF_PLUM:
                return "PL";
            case MISS_SCARLETT:
                return "SC";
        }
        return "err";
    }
}