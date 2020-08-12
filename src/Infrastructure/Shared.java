package Infrastructure;

public class Shared {
 
    public static int randomWithRange(int min, int max)
    {
        int range = Math.abs(max - min);
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }
    public static double randomWithRange2(int min, int max)
    {
        int range = Math.abs(max - min);
        return (Math.random() * range) + (min <= max ? min : max);
    }
}
