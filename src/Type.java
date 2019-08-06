import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum Type{
    CANDLE_STICK(SubType.WEAPON, "Candle Stick"),
    DAGGER(SubType.WEAPON, "Dagger"),
    LEAD_PIPE(SubType.WEAPON, "Lead Pipe"),
    REVOLVER(SubType.WEAPON, "Revolver"),
    ROPE(SubType.WEAPON, "Rope"),
    SPANNER(SubType.WEAPON, "Spanner"),

    BALL_ROOM(SubType.ROOM, "Ball Room"),
    KITCHEN(SubType.ROOM, "Kitchen"),
    DINING_ROOM(SubType.ROOM, "Dining Room"),
    LOUNGE(SubType.ROOM, "Lounge"),
    HALL(SubType.ROOM, "Hall"),
    STUDY(SubType.ROOM, "Study"),
    LIBRARY(SubType.ROOM, "Library"),
    BILLARD_ROOM(SubType.ROOM, "Billiard Room"),
    CONSERVATORY(SubType.ROOM, "Conservatory"),

    MISS_SCARLETT(SubType.PLAYER, "Miss Scarlett"),
    COL_MUSTARD(SubType.PLAYER, "Col. Mustard"),
    MRS_WHITE(SubType.PLAYER, "Mrs White"),
    MR_GREEN(SubType.PLAYER, "Mrs Green"),
    MRS_PEACOCK(SubType.PLAYER, "Mrs Peacock"),
    PROF_PLUM(SubType.PLAYER, "Prof. Plum");

    enum SubType{
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

    Type(SubType type, String name){
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SubType getType() {
        return type;
    }

    public static List<Type> getTypes(SubType type){
        return Arrays.stream(Type.values()).filter((t)->t.getType() == type).collect(Collectors.toList());
    }
}