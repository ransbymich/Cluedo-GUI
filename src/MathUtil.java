public class MathUtil {

    public static int constrain(int value, int max, int min){
        return value > max ? max : value < min ? min : value;
    }

    public static double constrain(double value, double max, double min){
        return value > max ? max : value < min ? min : value;
    }

    public static int randomInt(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }
}
