package Cluedo.Moves;

import Cluedo.Board;

public abstract class Turn {

    /**
     * All of the different types of turns you can do
     */
    public enum TurnType{
        MOVE("move"),
        SUGGESTION("suggestion"),
        ACCUSATION("accusation");

        public static final String REGEX = "move|suggest|accuse";

        private String string;

        TurnType(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }

    /**
     * Executes the turn
     * @param board The board in it's current state
     * @return      Whether or not the turn was successful or not
     */
    public abstract boolean execute(Board board);
}
