package Cluedo.Moves;

import Cluedo.Board;
import Cluedo.GameObjects.Player;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Tiles.DoorTile;

public class Accuse extends Turn {

    private Type weapon;
    private Type player;
    private Type room;

    public Accuse(Type weapon, Type player) {
        this.weapon = weapon;
        this.player = player;
    }

    @Override
    public boolean execute(Board board) {
        Player player = board.getCurrentPlayer();

        if(!checkAssumptions(board, player)) return false;

        Position pPos = player.getPosition();

        room = ((DoorTile)(board.getBoard()[pPos.getY()][pPos.getX()])).getRoom().getType();
        System.out.printf(
                "%s accuses %s of murdering Mr Black with a %s in the %s!\n",
                player.getName(),
                this.player.getName(),
                weapon.getName(),
                room.getName()
        );

        //WEAPON, PLAYER, ROOM
        Type[] solution = board.getSolution();

        if(weapon == solution[0] && this.player == solution[1] && room == solution[2]){
            System.out.printf("%s's accusation was correct! %s wins!\n", player.getName(), player.getName());
            board.completeGame();
        }else{
            player.setIsInPlay(false);
            System.out.printf("%s's accusation was incorrect. They can no longer make any more suggestions or accusations.\n", player.getName());
        }

        return true;
    }

    /**
     * Checks all of the assumptions required for an accusation
     * @param board     The current board
     * @param player    The player making the accusation
     * @return          Whether or not the accusation is actually a valid move
     */
    private boolean checkAssumptions(Board board, Player player){
        Position playerPosition = player.getPosition();

        if(!(board.getBoard()[playerPosition.getY()][playerPosition.getX()] instanceof DoorTile)){
            System.out.println("Cluedo.GameObjects.Player not in a room!");
            return false;
        }

        if(!player.getIsInPlay()){
            System.out.println("Cluedo.GameObjects.Player has already made an accusation!");
            return false;
        }

        return true;
    }
}
