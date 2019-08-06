public class Move extends Turn {

    private Position pos;

    public Move(Position pos) {
        this.pos = pos;
    }

    @Override
    boolean execute(Board board) {
        int diceRoll = Die.roll();
        System.out.println("You rolled a " + diceRoll);
        Player cPlayer = board.getCurrentPlayer();

//        System.out.printf("Attempt to move %s from %s to %s\n", cPlayer, cPlayer.getPosition(), pos);

        if(pos.distTo(cPlayer.getPosition()) > diceRoll){
            System.out.println("Invalid die roll.");
            return false;
        }

        if(!PathfindingUtil.findPath(board, cPlayer.getPosition(), pos)){
            System.out.println("Invalid path");
            return false;
        }

        if((board.getBoard()[pos.getY()][pos.getX()] instanceof EmptyTile)){
            EmptyTile emptyTile = (EmptyTile) board.getBoard()[pos.getY()][pos.getX()];
            if(emptyTile.getPlayer() != null){
                System.out.println("Target tile not empty!");
                return false;
            }

            emptyTile.setPlayer(cPlayer);
            ((EmptyTile)(board.getBoard()[cPlayer.getPosition().getY()][cPlayer.getPosition().getX()])).setPlayer(null);
            cPlayer.setPosition(pos);
            return true;
        }else if((board.getBoard()[pos.getY()][pos.getX()] instanceof RoomTile)){
            Room room = ((RoomTile) board.getBoard()[pos.getY()][pos.getX()]).getRoom();
            room.addEntity(cPlayer);

            ((EmptyTile)(board.getBoard()[cPlayer.getPosition().getY()][cPlayer.getPosition().getX()])).setPlayer(null);
            return true;
        }

        System.out.println("Invalid target tile");
        return false;
    }
}

