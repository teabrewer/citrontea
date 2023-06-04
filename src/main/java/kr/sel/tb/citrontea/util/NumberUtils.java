package kr.sel.tb.citrontea.util;

public class NumberUtils {

    public static int random(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
