package Cluedo.Util;

import Cluedo.Exceptions.InvalidInputException;
import Cluedo.Helpers.Type;
import Cluedo.Moves.Turn;
import Cluedo.*;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputUtil {

    public static final String COORDINATE_REGEX = "[0-9]+,[A-Za-z]";

    /**
     * Asks the user a question and then waits for an integer input, requires the integer input to be within the range
     *
     * @param string The string to output
     * @param min    The minimum value the number can be
     * @param max    The maximum value the number can be
     * @return The inputted value
     */
    public static int requireInt(String string, int min, int max) {
        System.out.println(string);
        Scanner scan = new Scanner(System.in);
        while (true) {
            String token = scan.next();
            try {
                int value = Integer.parseInt(token);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Inputted number is not within the range of [" + min + "," + max + "]");
                }
            } catch (Exception ignored) {
                System.out.println("You must input a number!");
            }
        }
    }

    /**
     * Requires the user to input a string which matches the provided regex, this is done after outputting the provided question
     *
     * @param string The question to output
     * @param regex  The regex string to match
     * @return The inputted string
     */
    public static String requireString(String string, String regex) {
        System.out.println(string);

        Scanner scan = new Scanner(System.in);

        while (true) {
            String token = scan.nextLine();

            if (Pattern.matches(regex, token)) {
                return token;
            } else {
                System.out.println("Input does not match an option: " + regex);
            }
        }
    }

    /**
     * Asks the user to input a type and then returns that type
     *
     * @param subType The subtype of type you want
     * @param board   The board in it's current state
     * @return The type
     */
    public static Type askType(Type.SubType subType, Board board) {
        String regex;
        StringBuilder builder = new StringBuilder();

        List<Type> types;
        if (subType == Type.SubType.PLAYER) {
            types = board.getPlayers();
        } else {
            types = Type.getTypes(subType);
        }

        //First we must generate a regex
        builder.append("^(");
        types.forEach((t) -> {
            builder.append(t.getName().toLowerCase());

            builder.append("|");
        });
        builder.delete(builder.length() - 1, builder.length());
        builder.append(")$");

        regex = builder.toString();
        regex = regex.replaceAll("\\.", "");

        String input = InputUtil.requireString("Enter the " + subType + " you would like to suggest: ", regex);

        //Now get the type
        for (Type t : types) {
            String name = t.getName().toLowerCase().replaceAll("\\.", "");
            if (name.equals(input)) {
                return t;
            }
        }

        throw new InvalidInputException();
    }

    /**
     * Asks the user for a turn type
     *
     * @return The turn type returned
     */
    public static Type askType(List<Type> types, Board board) {
        String regex;
        StringBuilder builder = new StringBuilder();

        builder.append("^(");
        types.forEach((t) -> {
            builder.append(t.getName().toLowerCase());

            builder.append("|");
        });
        builder.delete(builder.length() - 1, builder.length());
        builder.append(")$");

        regex = builder.toString();
        regex = regex.replaceAll("\\.", "");

        System.out.println("Options to refute with: " + regex);
        String input = InputUtil.requireString("Enter the card  you would like to refute with: ", regex);


        return getTypeFromString(types, input);
    }


    public static Type getTypeFromString(List<Type> types, String input){
        for (Type t : types) {
            String name = t.getName().toLowerCase().replaceAll("\\.", "");
            if (name.equals(input)) {
                return t;
            }
        }

        return null;
    }

    public static Turn.TurnType askTurnType() {
        String input = InputUtil.requireString("What kind of turn would you like to do?: ", Turn.TurnType.REGEX);

        switch (input) {
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
}
