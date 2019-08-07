import java.util.*;
import java.util.stream.Collectors;

public class Suggest extends Turn {
    private Type weapon;
    private Type accused;

    public Suggest(Type weapon, Type accused) {
        this.weapon = weapon;
        this.accused = accused;
    }


    /**
     * Actually makes the move on the board
     * @param board The board (provided from Cluedo class)
     * @return returns whether or not the play is VALID, may be incorrect and still return true
     */
    @Override
    boolean execute(Board board) {

        //check assumptions
        if (!checkAssumptions(board)){           System.out.println("You cannot make a suggestion without being in a room!");
            return false;
        }

        Position pPos = board.getCurrentPlayer().getPosition();

        Type room = ((RoomTile) board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();
        Set<Type> suggestions = new HashSet<>(Arrays.asList(weapon, accused, room));

        //if a single player has any of the three solutions return false
        List<Player> players = Type.getTypes(Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());


        players.remove(board.getCurrentPlayer());

//        System.out.println("In play: " + players.toString());
        players.remove(null);

        //If the player is not accusing themselves, move the accused to the position of the current player
        if (board.getCurrentPlayer() != board.getPlayer(accused)){
            Player player = board.getPlayer(accused);
            Position pos = board.getCurrentPlayer().position;

            //Set the new tile
            Tile newTile = board.getBoard()[pos.getY()][pos.getX()];

            if(newTile instanceof EmptyTile){
                EmptyTile emptyTile = (EmptyTile)newTile;

                if(emptyTile.getPlayer() != null) return false;

                emptyTile.setPlayer(player);

                player.setPosition(pos);
                return true;
            }else if(newTile instanceof RoomTile){
                Room newRoom = ((RoomTile)newTile).getRoom();
                newRoom.addEntity(player);
                System.out.printf("%s enters %s.\n", player.getType(), room.getType());
                player.setPosition(pos);

                return true;
            }

            Position oldPosition = player.getPosition();

            Tile oldTile = board.getBoard()[oldPosition.getY()][oldPosition.getX()];

            if(oldTile instanceof EmptyTile){
                EmptyTile emptyTile = (EmptyTile)oldTile;
                emptyTile.setPlayer(null);
                return true;
            }else if(oldTile instanceof RoomTile){
                Room oldRoom = ((RoomTile)oldTile).getRoom();
                oldRoom.removeEntity(player);
                System.out.printf("%s enters %s.\n", player.getName(), room.getName());

                return true;
            }

            throw new InvalidInputException();
        }

        boolean wasRefuted = false;
        for (Player refutingPlayer : players) {
            Set<Type> hand = new HashSet<>(refutingPlayer.getHand());
            hand.retainAll(suggestions);
//            System.out.println(hand.size() + " refutable cards from " + refutingPlayer.getType().getName());

            if (hand.size() > 1){
                while(true){
                    Type next = InputUtil.askType(new ArrayList<>(hand), board);
                    if (next != null){
                        System.out.println(refutingPlayer.getName() + " refutes with " + next.getName());
                        wasRefuted = true;
                        break;
                    }
                }
            } else if (hand.size() == 1){
                wasRefuted = true;
                System.out.println(refutingPlayer.getName() + " refutes with " + hand.iterator().next().getName());
                break;
            }
        }

        if(!wasRefuted){
            String input = InputUtil.requireString("Would you like to make an accusation? [y/n]", "y|n");
            if(input.equals("y")){
                while(true){
                    if(board.processTurn(new Accuse(weapon, accused))){
                        break;
                    }
                }
            }
        }
        //PLAYERS ARE MOVED IF THEY ARE ACCUSED

        //still returns true if the play is valid, but incorrect
        return true;

        //suggest solution
        //say who refuted it, if anyone
        //if someone refuted it, do nothing, finish turn
        //is noone did, ask if they would like to accuse

    }

    /**
     * Quick helper to ensure that the assumptions for a player and the board are valid
     * @param board the game board
     * @return true if the assumptions are met
     */
    private boolean checkAssumptions(Board board) {
        Player player = board.getCurrentPlayer();

        //player accusing must be in room
        //must be their turn
        return player.getIsInPlay() && player.inRoom(board) && player == board.getCurrentPlayer();
    }
}
