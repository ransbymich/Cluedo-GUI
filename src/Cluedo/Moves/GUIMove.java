package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.Exceptions.InvalidInputException;
import Cluedo.GUI.ConsolePanel;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Tiles.EmptyTile;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.RoomTile;
import Cluedo.Tiles.Tile;
import Cluedo.Util.PathfindingUtil;

import java.util.List;

public class GUIMove extends Turn {
    private Position pos;
    private ConsolePanel cp;
    private int diceRoll;

    public GUIMove(Position pos, int dieRoll, ConsolePanel cp) {
        this.pos = pos;
        this.diceRoll = dieRoll;
        this.cp = cp;
    }

    /**
     * Executes the move turn
     * @param board The board in it's current state
     * @return      Whether or not it was successful
     */
    @Override
    public boolean execute(Board board) {
        if(board.getState() != State.MOVING){
            cp.println("You can not move right now!");
            return false;
        }

        Player cPlayer = board.getCurrentPlayer();

        return checkAssumptions(board, cPlayer) && clearOldTile(board, cPlayer) && setNewTile(board, cPlayer);
    }

    /**
     * Completes the set new tile logic, by either putting the player inside of a room or moving them to the correct empty tile.
     * If the player is entering a room, we also ask whether or not they would like to make a suggestion or not.
     * @param board     The board in it's current state
     * @param player    The player making the move
     * @return          Whether or not the move was successful or not
     */
    private boolean setNewTile(Board board, Player player){
        Tile newTile = board.getBoard()[pos.getY()][pos.getX()];
        Position cPos = board.getCurrentPlayer().getPosition();
        Tile oldTile = board.getBoard()[cPos.getY()][cPos.getX()];

        //If we're exiting a room
        if (oldTile instanceof RoomTile && newTile instanceof EmptyTile) {
            EmptyTile emptyTile = (EmptyTile)newTile;

            if(emptyTile.getPlayer() != null) return false;

            emptyTile.setPlayer(player);

            player.setPosition(pos);

            cp.println("Turn complete.");
            board.setState(State.END_TURN);
            return true;
        //If we're moving to an empty tile in the hallway
        }else if(newTile instanceof EmptyTile){
            EmptyTile emptyTile = (EmptyTile)newTile;

            if(emptyTile.getPlayer() != null) return false;

            emptyTile.setPlayer(player);

            player.setPosition(pos);

            cp.println("Turn complete.");
            board.setState(State.END_TURN);
            return true;
        //If we're entering a room
        }else if(newTile instanceof DoorTile){
            Room room = ((DoorTile)newTile).getRoom();
            room.addEntity(player);
            cp.println(player.getName() + " enters " + room.getType().getName() + ".");
            cp.println("You may make a suggestion.");
            board.setState(State.SUGGEST);
            return true;
        }

        throw new InvalidInputException();
    }

    /**
     * Completes the logic for clearing the old tile a player once was on. If the player was in a room then we remove them from the room,
     * otherwise we just clear the Cluedo.Tiles.EmptyTile.
     * @param board     The board in it's current state
     * @param player    The player making the move
     * @return          Whether or not it was successful or not
     */
    private boolean clearOldTile(Board board, Player player){
        Position oldPosition = player.getPosition();

        Tile oldTile = board.getBoard()[oldPosition.getY()][oldPosition.getX()];

        if(oldTile instanceof EmptyTile){
            EmptyTile emptyTile = (EmptyTile)oldTile;
            emptyTile.setPlayer(null);
            return true;
        }else if(oldTile instanceof DoorTile){
            Room room = ((DoorTile)oldTile).getRoom();
            room.removeEntity(player);
            cp.println(player.getName() + " leaves " + room.getType().getName());
            return true;
        } else if (oldTile instanceof RoomTile){
            Room room = ((RoomTile)oldTile).getRoom();
            room.removeEntity(player);
            cp.println(player.getName() + " leaves " + room.getType().getName());
            return true;
        }

        throw new InvalidInputException();

    }

    /**
     * Checks the assumptions for a move
     * @param board     The board in it's current state
     * @param player    The player making a move
     * @return          Whether or not the assumptions are met or not
     */
    private boolean checkAssumptions(Board board, Player player){
        Tile newTile = board.getBoard()[pos.getY()][pos.getX()];
        Position cPos = board.getCurrentPlayer().getPosition();
        Tile oldTile = board.getBoard()[cPos.getY()][cPos.getX()];

        //If we're exiting a room
        if (oldTile instanceof RoomTile && newTile instanceof EmptyTile) {
            Room oldRoom = ((RoomTile) oldTile).getRoom();

            //Get the positions of all exits
            List<Position> exitPositions = oldRoom.getDoorPositions();

            //If even a SINGLE doorway can make it to the target, we can make it(exiting through that door)
            boolean canMakeIt = false;
            for (Position exitPosition : exitPositions) {
                if (exitPosition.distTo(newTile.getPosition()) <= diceRoll){
                    canMakeIt = true;
                }
            }
            if (!canMakeIt){
                return false;
            }

        }else {
            if(pos.distTo(player.getPosition()) > diceRoll){
                cp.println("You can not move that far!");
                return false;
            }
        }
        Tile tile = board.getBoard()[pos.getY()][pos.getX()];
        if(tile instanceof EmptyTile && ((EmptyTile)tile).getPlayer() != null){
            cp.println("You can not move on top of another player!");
            return false;
        }

        if(!PathfindingUtil.findPath(board, player.getPosition(), pos)){
            cp.println("No path found.");
            return false;
        }

        return true;
    }
}
