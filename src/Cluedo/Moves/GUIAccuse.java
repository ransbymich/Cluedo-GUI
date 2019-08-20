package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GUI.ConsolePanel;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.RoomTile;

/**
 * This class is pretty much the same as Accuse, but is fitted for use in the GUI
 */
public class GUIAccuse extends Turn {

    private Type weapon;
    private Type player;
    private Type room;
    private GUI gui;

    private ConsolePanel cp;

    public GUIAccuse(Type weapon, Type player, GUI gui) {
        this.weapon = weapon;
        this.player = player;
        this.gui = gui;
        this.cp = gui.getConsole();
    }

    /**
     * Executes the accuse turn
     * @param board The board in it's current state
     * @return      Whether or not it was successful
     */
    @Override
    public boolean execute(Board board) {
        Player player = board.getCurrentPlayer();

        //Check the assumptions
        if(!checkAssumptions(board, player)) return false;

        Position pPos = player.getPosition();

        //Get the players room
        room = ((RoomTile)(board.getBoard()[pPos.getY()][pPos.getX()])).getRoom().getType();

        //WEAPON, PLAYER, ROOM
        Type[] solution = board.getSolution();

        //Check the solution, if it's correct then complete the game, otherwise say differently
        if(weapon == solution[0] && this.player == solution[1] && room == solution[2]){
            cp.println(player.getName() + "'s accusation was correct! " + player.getName() + " wins!");
            board.completeGame();
            board.setState(State.GAME_FINISHED);
        }else{
            player.setIsInPlay(false);
            cp.println(player.getName() + "'s accusation was incorrect. They can no longer make any more suggestions or accusations.");
        }

        return true;
    }

    /**
     * Checks the assumptions of an accusation
     * @param board     The board in it's current state
     * @param player    The current player making the accusation
     * @return          Whether or not all assumptions are met
     */
    private boolean checkAssumptions(Board board, Player player){
        Position playerPosition = player.getPosition();

        if(board.getState() != State.ACCUSING){
            cp.println("Unable to make accusation right now.");
            return false;
        }

        if(!(board.getBoard()[playerPosition.getY()][playerPosition.getX()] instanceof RoomTile)){
            cp.println("Player not in a room!");
            return false;
        }

        if(!player.getIsInPlay()){
            cp.println("Player has already made an accusation!");
            return false;
        }

        return true;
    }
}
