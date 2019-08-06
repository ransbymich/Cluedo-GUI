public class Suggest extends Turn {

    private Type weapon;
    private Type player;

    public Suggest(Type weapon, Type player) {
        this.weapon = weapon;
        this.player = player;
    }

    @Override
    boolean execute(Board board) {
        Player player = board.getCurrentPlayer();
        return checkAssumptions(board, player);
    }

    private boolean checkAssumptions(Board board, Player player){
        return true;
    }



}
