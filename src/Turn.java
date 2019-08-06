public abstract class Turn {

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

    abstract boolean execute(Board board);
}
