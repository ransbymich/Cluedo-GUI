/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.List;
import java.util.Scanner;

// line 49 "model.ump"
// line 160 "model.ump"
public class Cluedo {


    //------------------------
    // ENUMERATIONS
    //------------------------

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Cluedo Associations
    private Board board;


    private boolean playing = false;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Cluedo() {
        Board board = new Board(InputUtil.requireInt("How many people are playing?: ", 3, 6));
        if (!setBoard(board)) {
            throw new RuntimeException("Unable to create Cluedo due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }

        startGame();
    }


    private void startGame(){
        playing = true;
        while(playing){
            //Print out the board nicely
            System.out.println(board.toString());

            //Display whose turn it is
            System.out.println("Now playing " + board.getCurrentTurn().getName() + "'s turn.");

            //Printing out the players hand
            System.out.print("They have: \n");
            for (Card card : board.getCurrentPlayer().getHand()) {
                System.out.println("\t" + card.toString());
            };

            Turn.TurnType turnType = askTurnType();

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
        }
    }

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

    private void processSuggestion(){
//        System.out.println(askType(Type.SubType.PLAYER));
        while(true){
            if(board.processTurn(new Suggest(askType(Type.SubType.WEAPON), askType(Type.SubType.PLAYER)))){
                break;
            }
        }


    }

    private void processAccusation(){


    }
    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetOne */
    public Board getBoard() {
        return board;
    }

    /* Code from template association_SetUnidirectionalOne */
    public boolean setBoard(Board aNewBoard) {
        boolean wasSet = false;
        if (aNewBoard != null) {
            board = aNewBoard;
            wasSet = true;
        }
        return wasSet;
    }

    public void delete() {
        board = null;
    }

    // line 54 "model.ump"
    public Type askType(Type.SubType subType) {
        String regex;
        StringBuilder builder = new StringBuilder();

        List<Type> types;
        if(subType == Type.SubType.PLAYER){
            types = board.getPlayers();
        }else{
            types = Type.getTypes(subType);
        }

        builder.append("^(");
        types.forEach((t)->{
            builder.append(t.getName().toLowerCase());

            builder.append("|");
        });
        builder.delete(builder.length() - 1, builder.length());
        builder.append(")$");

        regex = builder.toString();
        regex = regex.replaceAll("\\.","");

        String input = InputUtil.requireString("Enter the " + subType + " you would like to suggest: ", regex);

        for(Type t : types){
            String name = t.getName().toLowerCase().replaceAll("\\.", "");
            if(name.equals(input)){
                return t;
            }
        }

        throw new InvalidInputException();
    }

    public Turn.TurnType askTurnType(){
        String input = InputUtil.requireString("What kind of turn would you like to do?: ", Turn.TurnType.REGEX);

        switch (input){
            case "move":
                return Turn.TurnType.MOVE;
            case "suggest":
                return Turn.TurnType.SUGGESTION;
            case "accuse":
                return Turn.TurnType.ACCUSATION;
            default:
                throw new InvalidInputException();
        }
    }

    public static void main(String[] args){
        new Cluedo();
    }

}