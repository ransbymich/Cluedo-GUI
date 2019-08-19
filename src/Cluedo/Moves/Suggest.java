package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.EmptyTile;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.Tile;
import Cluedo.Util.InputUtil;

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
     *
     * @param board The board (provided from Cluedo.Text.Cluedo class)
     * @return returns whether or not the play is VALID, may be incorrect and still return true
     */
    @Override
    public boolean execute(Board board) {

        //check assumptions
        if (!checkAssumptions(board)) {
            System.out.println("You cannot make a suggestion without being in a room!");
            return false;
        }

        Position pPos = board.getCurrentPlayer().getPosition();

        Type room = ((DoorTile) board.getBoard()[pPos.getY()][pPos.getX()]).getRoom().getType();
        Set<Type> suggestions = new HashSet<>(Arrays.asList(weapon, accused, room));

        //if a single player has any of the three solutions return false
        List<Player> players = Type.getTypes(Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());


        players.remove(board.getCurrentPlayer());

//        System.out.println("In play: " + players.toString());
        players.remove(null);

        //If the player is not accusing themselves, move the accused to the position of the current player
        if (board.getCurrentPlayer() != board.getPlayer(accused)) {
            Player player = board.getPlayer(accused);
            Position pos = board.getCurrentPlayer().getPosition();

            //Set the tile we want to move to
            Tile newTile = board.getBoard()[pos.getY()][pos.getX()];
            Position oldPosition = player.getPosition();

            if (newTile instanceof EmptyTile) {
                EmptyTile emptyTile = (EmptyTile) newTile;

                if (emptyTile.getPlayer() != null) return false;

                emptyTile.setPlayer(player);

                player.setPosition(pos);

            } else if (newTile instanceof DoorTile) {
                Room newRoom = ((DoorTile) newTile).getRoom();
                newRoom.addEntity(player);
                System.out.printf("%s enters %s.\n", player.getName(), room.getName());
                player.setPosition(pos);
            }


            Tile oldTile = board.getBoard()[oldPosition.getY()][oldPosition.getX()];

            //remove the player from the old location
            if (oldTile instanceof EmptyTile) {
                EmptyTile emptyTile = (EmptyTile) oldTile;
                emptyTile.setPlayer(null);

            } else if (oldTile instanceof DoorTile) {
                Room oldRoom = ((DoorTile) oldTile).getRoom();
                oldRoom.removeEntity(player);
                System.out.printf("%s enters %s.\n", player.getName(), room.getName());
            }

        }


        //check if other players can refute using cards in their hand
        boolean wasRefuted = false;
        for (Player refutingPlayer : players) {
            Set<Type> hand = new HashSet<>(refutingPlayer.getHand());
            hand.retainAll(suggestions);
//            System.out.println(hand.size() + " refutable cards from " + refutingPlayer.getType().getName());


            //if someone refuted it, do nothing, end turn
            //say who refuted it, if anyone
            if (hand.size() > 1) {
                //ask which card a player would like to refute with
                while (true) {
                    Type next = InputUtil.askType(new ArrayList<>(hand), board);
                    if (next != null) {
                        System.out.println(refutingPlayer.getName() + " refutes with " + next.getName());
                        wasRefuted = true;
                        break;
                    }
                }
                //automatically refute if there is only one card
            } else if (hand.size() == 1) {
                wasRefuted = true;
                System.out.println(refutingPlayer.getName() + " refutes with " + hand.iterator().next().getName());
                break;
            }
        }

        //is no player refutes, ask if they would like to accuse
        if (!wasRefuted) {
            String input = InputUtil.requireString("Would you like to make an accusation? [y/n]", "y|n");
            if (input.equals("y")) {
                while (true) {
                    if (board.processTurn(new Accuse(weapon, accused))) {
                        break;
                    }
                }
            }
        }

        //still returns true if the play is valid, but incorrect
        return true;

        //suggest solution


    }

    /**
     * Quick helper to ensure that the assumptions for a player and the board are valid
     *
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
