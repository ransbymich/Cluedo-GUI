package Cluedo.Helpers;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Types for any card within the game, also note the presence of subtypes, which make filtering easier
 */
public enum Type{
    CANDLE_STICK(SubType.WEAPON, "Candle Stick", null),
    KNIFE(SubType.WEAPON, "Knife", null),
    LEAD_PIPE(SubType.WEAPON, "Lead Pipe", null),
    REVOLVER(SubType.WEAPON, "Revolver", null),
    ROPE(SubType.WEAPON, "Rope", null),
    WRENCH(SubType.WEAPON, "Wrench", null),

    BALL_ROOM(SubType.ROOM, "Ball Room", null),
    KITCHEN(SubType.ROOM, "Kitchen", null),
    DINING_ROOM(SubType.ROOM, "Dining Room", null),
    LOUNGE(SubType.ROOM, "Lounge", null),
    HALL(SubType.ROOM, "Hall", null),
    STUDY(SubType.ROOM, "Study", null),
    LIBRARY(SubType.ROOM, "Library", null),
    BILLIARD_ROOM(SubType.ROOM, "Billiard Room", null),
    CONSERVATORY(SubType.ROOM, "Conservatory", null),

    MISS_SCARLETT(SubType.PLAYER, "Miss Scarlett", Color.RED),
    COL_MUSTARD(SubType.PLAYER, "Col. Mustard", Color.YELLOW),
    MR_WHITE(SubType.PLAYER, "Mr White", Color.WHITE),
    DR_GREEN(SubType.PLAYER, "Dr Green", Color.GREEN),
    MRS_PEACOCK(SubType.PLAYER, "Mrs Peacock", Color.BLUE),
    PROF_PLUM(SubType.PLAYER, "Prof. Plum", new Color(128, 0, 128));

    public enum SubType{
        WEAPON(6),
        ROOM(9),
        PLAYER(6);

        private int nCards;

        SubType(int nCards){
            this.nCards = nCards;
        }

        public int getnCards() {
            return nCards;
        }
    }

    private SubType type;
    private String name;
    private Color color;

    Type(SubType type, String name, Color color){
        this.type = type;
        this.name = name;
        this.color = color;
    }

    public String getName(){
      return name;
    }
    public SubType getSubType() {
        return type;
    }
    public Color getColor(){return color;}

    /**
     * Gets all of the types within a provided subtype
     * @param type  The subtype
     * @return      The list of types
     */
    public static List<Type> getTypes(SubType type){
        return Arrays.stream(Type.values()).filter((t)->t.getSubType() == type).collect(Collectors.toList());
    }

    public static List<Type> getTypes(){
        return Arrays.asList(Type.values());
    }
}