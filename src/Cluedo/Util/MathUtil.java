package Cluedo.Util;

public class MathUtil {

    /**
     * Randomly generates an integer between the given range
     * @param min   The minimum value the number can be
     * @param max   The maximum value the number can be
     * @return      The random number generated
     */
    public static int randomInt(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }
}
