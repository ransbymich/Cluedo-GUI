import java.util.*;
import java.util.stream.Collectors;

public class Suggest extends Turn {
    private Type weapon;
    private Type accused;

    public Suggest(Type weapon, Type accused) {
        this.weapon = weapon;
        this.accused = accused;
    }

    @Override
    boolean execute(Board board) {
        Position pPos = board.getCurrentPlayer().getPosition();
        Player player = board.getCurrentPlayer();

        Type room = ((RoomTile) board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();
        Set<Type> suggestions = new HashSet<>(Arrays.asList(weapon, accused, room));

        //check assumptions
        if (!checkAssumptions(board, player)) return false;

        //if a single player has any of the three solutions return false
        List<Player> players = Type.getTypes(Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());

        players.remove(board.getCurrentPlayer());

//        System.out.println("In play: " + players.toString());
        players.remove(null);

        for (Player refutingPlayer : players) {
            Set<Type> hand = refutingPlayer.getHand().stream().map(Card::getType).collect(Collectors.toSet());
            hand.retainAll(suggestions);
            System.out.println(hand.size() + " refutable cards from " + refutingPlayer.getType().getName());

            if (hand.size() > 1){
                while(true){
                    Type next = InputUtil.askType(new ArrayList<>(hand), board);
                    if (next != null){
                        System.out.println(refutingPlayer.getName() + " refutes with " + next.getName());
                        break;
                    }
                }
            } else if (hand.size() == 1){
                System.out.println(refutingPlayer.getName() + " refutes with " + hand.iterator().next().getName());
            }
        }


        //still returns true if the play is valid, but incorrect
        return true;

        //suggest solution
        //say who refuted it, if anyone
        //if someone refuted it, do nothing, finish turn
        //is noone did, ask if they would like to accuse

    }

    private boolean checkAssumptions(Board board, Player player) {
        //player accusing must be in room
        //must be their turn
        return player.getIsInPlay() && player.inRoom(board) && player == board.getCurrentPlayer();
    }
}
