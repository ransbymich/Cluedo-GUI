import java.util.List;
import java.util.stream.Collectors;

public class Accuse extends Turn {

    private Type weapon;
    private Type player;
    private Type room;

    public Accuse(Type weapon, Type player) {
        this.weapon = weapon;
        this.player = player;
    }

    @Override
    boolean execute(Board board) {
        Player player = board.getCurrentPlayer();

        if(!checkAssumptions(board, player)) return false;

        Position pPos = player.getPosition();

        room = ((RoomTile)(board.getBoard()[pPos.getY()][pPos.getX()])).getRoom().getType();
        System.out.printf("%s accuses %s of murdering Mr Black with a %s in the %s!\n", player.getType().getName(), this.player.getName(), weapon.getName(), room.getName());

        return true;
    }

    private boolean checkAssumptions(Board board, Player player){
        Position playerPosition = player.getPosition();

        if(!(board.getBoard()[playerPosition.getY()][playerPosition.getX()] instanceof RoomTile)){
            System.out.println("Player not in a room!");
            return false;
        }

        if(!player.getIsInPlay()){
            System.out.println("Player has already made an accusation!");
            return false;
        }

        return true;
    }
}
