import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

enum Type{
    CANDLE_STICK(SubType.WEAPON, "Candle Stick"),
    DAGGER(SubType.WEAPON),
    LEAD_PIPE(SubType.WEAPON),
    REVOLVER(SubType.WEAPON),
    ROPE(SubType.WEAPON),
    SPANNER(SubType.WEAPON),

    BALL_ROOM(SubType.ROOM),
    KITCHEN(SubType.ROOM),
    DINING_ROOM(SubType.ROOM),
    LOUNGE(SubType.ROOM),
    HALL(SubType.ROOM),
    STUDY(SubType.ROOM),
    LIBRARY(SubType.ROOM),
    BILLARD_ROOM(SubType.ROOM),
    CONSERVATORY(SubType.ROOM),

    MISS_SCARLETT(SubType.PLAYER),
    COL_MUSTARD(SubType.PLAYER),
    MRS_WHITE(SubType.PLAYER),
    MR_GREEN(SubType.PLAYER),
    MRS_PEACOCK(SubType.PLAYER),
    PROF_PLUM(SubType.PLAYER);

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