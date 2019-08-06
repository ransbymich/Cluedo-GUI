import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtil {

    public static final String COORDINATE_REGEX = "[0-9]+,[A-Za-z]";

    public static int requireInt(String string, int min, int max){
        System.out.println(string);
        Scanner scan = new Scanner(System.in);
        while(true){
            String token = scan.next();
            try{
                int value = Integer.parseInt(token);
                if(value >= min && value <= max){
                    return value;
                }else{
                    System.out.println("Inputted number is not within the range of [" + min + "," + max + "]");
                }
            }catch (Exception ignored){
                System.out.println("You must input a number!");
            }
        }
    }

    public static String requireString(String string, String regex){
        System.out.println(string);

        Scanner scan = new Scanner(System.in);

        while(true){
            String token = scan.nextLine();

            if(Pattern.matches(regex, token)){
                return token;
            }else{
                System.out.println("Input does not match an option: " + regex);
            }
        }
    }
}
