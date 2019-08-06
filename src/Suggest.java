import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Suggest extends Turn {
    private Type weapon;
    private Type accused;

    public Suggest(Type weapon, Type player) {
        this.weapon = weapon;
        this.accused = player;
    }

    @Override
    boolean execute(Board board) {
        List<Card> suggestions = new ArrayList<>();

        Position pPos = board.getCurrentPlayer().getPosition();


        Player player = board.getCurrentPlayer();

        Type room = ((RoomTile)board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();

        //check assumptions
        if (!checkAssumptions(board, player)) return false;

        //if a single player has any of the three solutions return false
        List<Player> players = Type.getTypes(Type.SubType.PLAYER).stream().map(board::getPlayer).collect(Collectors.toList());

        System.out.println(players);
        for (Player refutingPlayer : players) {
            for (Card card : refutingPlayer.getHand()) {

            }
        }


        //still returns true if the play is valid, but incorrect
        return true;

        //suggest solution
        //say who refuted it, if anyone
        //if someone refuted it, do nothing, finish turn
        //is noone did, ask if they would like to accuse

    }

    private boolean checkAssumptions(Board board, Player player){
        //player accusing must be in room
        return player.getIsInPlay();
    }
}
