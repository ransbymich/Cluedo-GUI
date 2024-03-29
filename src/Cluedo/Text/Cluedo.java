package Cluedo.Text;

import Cluedo.Exceptions.InvalidInputException;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Moves.Accuse;
import Cluedo.Moves.Move;
import Cluedo.Moves.Suggest;
import Cluedo.Moves.Turn;
import Cluedo.Util.InputUtil;
import Cluedo.*;

public class Cluedo {

    private Board board;

    private boolean playing = false;

    public Cluedo() {
        board = new Board(InputUtil.requireInt("How many people are playing?: ", 3, 6));
        startGame();
    }

    /**
     * Starts the game of Cluedo.Text.Cluedo
     */
    private void startGame(){
        playing = true;
        while(playing){
            //Print out the board nicely
            System.out.println(board.toString());

            //Display whose turn it is
            System.out.println("Now playing " + board.getCurrentTurn().getName() + "'s turn.");

            //Printing out the players hand
            System.out.print("They have: \n");
            for (Type card : board.getCurrentPlayer().getHand()) {
                System.out.println("\t" + card.getName());
            }

            Turn.TurnType turnType = InputUtil.askTurnType();

            switch (turnType){
                case MOVE:
                    processMove();
                    break;
                case SUGGESTION:
                    processSuggestion();
                    break;
                case ACCUSATION:
                    processAccusation();
                    break;
                default:
                    throw new InvalidInputException();
            }

            board.completeTurn();
        }
    }

    /**
     * Processes a move turn
     */
    private void processMove(){
        int diceRoll = Die.roll();
        System.out.println("You roll a " + diceRoll + ".");
        while(true){
            Position movePosition = Position.positionFromString(InputUtil.requireString("Input a coordinate to move to: ", InputUtil.COORDINATE_REGEX));
            if(board.processTurn(new Move(movePosition, diceRoll))){
                break;
            }
        }
    }

    /**
     * Processes a suggestion turn
     */
    private void processSuggestion(){
        while(true){
            if(board.processTurn(new Suggest(InputUtil.askType(Type.SubType.WEAPON, board), InputUtil.askType(Type.SubType.PLAYER, board)))){
                break;
            }
        }
    }

    /**
     * Processes an accusation turn
     */
    private void processAccusation(){
        while(true){
            if(board.processTurn(new Accuse(InputUtil.askType(Type.SubType.WEAPON, board), InputUtil.askType(Type.SubType.PLAYER, board)))){
                break;
            }
        }
    }

    public static void main(String[] args){
        new Cluedo();
    }

}