public class Move extends Turn {

    private Position pos;
    private int diceRoll;

    public Move(Position pos, int dieRoll) {
        this.pos = pos;
        this.diceRoll = dieRoll;
    }

    @Override
    boolean execute(Board board) {
        Player cPlayer = board.getCurrentPlayer();

        return clearOldTile(board, cPlayer) && checkAssumptions(board, cPlayer) && setNewTile(board, cPlayer);
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

        if(newTile instanceof EmptyTile){
            EmptyTile emptyTile = (EmptyTile)newTile;

            if(emptyTile.getPlayer() != null) return false;

            emptyTile.setPlayer(player);

            player.setPosition(pos);
            return true;
        }else if(newTile instanceof RoomTile){
            Room room = ((RoomTile)newTile).getRoom();
            room.addEntity(player);
            System.out.printf("%s enters %s.\n", player.getType(), room.getType());
            player.setPosition(pos);

            String input = InputUtil.requireString("Would you like to make a suggestion? [y/n]", "y|n");
            if(input.equals("y")){
                while(true){
                    if(board.processTurn(new Suggest(InputUtil.askType(Type.SubType.WEAPON, board), InputUtil.askType(Type.SubType.PLAYER, board)))){
                        break;
                    }
                }
            }
            return true;
        }

        throw new InvalidInputException();
    }

    /**
     * Completes the logic for clearing the old tile a player once was on. If the player was in a room then we remove them from the room,
     * otherwise we just clear the EmptyTile.
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
        }else if(oldTile instanceof RoomTile){
                Room room = ((RoomTile)oldTile).getRoom();
                room.removeEntity(player);
            System.out.printf("%s enters %s.\n", player.getType(), room.getType());

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
        if(pos.distTo(player.getPosition()) > diceRoll){
            System.out.println("You can't move that far!");
            return false;
        }

        if(!PathfindingUtil.findPath(board, player.getPosition(), pos)){
            System.out.println("Invalid path.");
            return false;
        }

        return true;
    }
}

