package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GUI.CluedoCanvas;
import Cluedo.GUI.ConsolePanel;
import Cluedo.GUI.GUI;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.State;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.DoorTile;
import Cluedo.Tiles.EmptyTile;
import Cluedo.Tiles.RoomTile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GUISuggest extends Turn{

    private GUI gui;
    private ConsolePanel cp;
    private Type weapon;
    private Type accused;
    private Type room;

    public GUISuggest(GUI gui, Type weapon, Type accused){
        this.gui = gui;
        this.weapon = weapon;
        this.accused = accused;
        this.cp = gui.getConsole();
    }

    /**
     * Executes the suggest turn
     * @param board The board in it's current state
     * @return      Whether or not it was successful
     */
    @Override
    public boolean execute(Board board) {
        Player cPlayer = board.getCurrentPlayer();
        this.room = ((RoomTile)board.getBoard()[cPlayer.getPosition().getY()][cPlayer.getPosition().getX()]).getRoom().getType();

        if (board.getState() != State.SUGGESTING){
            gui.getConsole().println("You may not suggest right now!");
            return false;
        }

        if(!checkAssumptions(board, cPlayer)){
            cp.println("Assumptions for suggesting not met.");
            return false;
        }


        //Teleport the accused player to the room of accusal
        Room roomOfAccusal = null;
        for (Room room : board.getRooms().values()) {
            if (room.hasPlayer(cPlayer)){
                roomOfAccusal = room;
            }
        }
        if(roomOfAccusal != null){
            Player accusedPlayer = board.getPlayer(accused);

            EmptyTile tile = (EmptyTile) board.getBoard()[accusedPlayer.getPosition().getY()][accusedPlayer.getPosition().getX()];

            tile.setPlayer(null);

            roomOfAccusal.addEntity(accusedPlayer);
            cp.println(accusedPlayer.getName() + " enters " + room.getName() + ".");
            gui.repaint();
        }




        java.util.List<Player> players = Type.getTypes(Type.SubType.PLAYER).stream().filter(board::hasPlayer).map(board::getPlayer).collect(Collectors.toList());
        Set<Type> suggestion = new HashSet<>(Arrays.asList(room, accused, weapon));

        for(Player p : players){
            if(p.getType() == cPlayer.getType()) continue;

            Set<Type> hand = new HashSet<>(p.getHand());

            hand.retainAll(suggestion);

            java.util.List<Type> refutationCards = new ArrayList<>(hand);

            if(refutationCards.size() == 1){
                cp.println(p.getName() + " refutes the suggestion with " + refutationCards.get(0).getName() + " card!");
                board.setState(State.END_TURN);
                return true;
            }else if(refutationCards.size() > 1){
                board.setState(State.REFUTING);
                return true;
            }
        }
        board.setState(State.ACCUSE);
        return true;
    }


    private boolean checkAssumptions(Board b, Player cPlayer){
        return weapon != null || accused != null || room != null || !cPlayer.getIsInPlay();
    }
}
