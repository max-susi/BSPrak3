package mensa;


import java.util.Random;

public class MensaUtil {

//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_GREEN = "\u001B[32m";

    private static Random random = new Random();

    public static int getZeitBezahlen() {
        return random.nextInt(500) * 2 + 1000;
    }

    public static int getZeitEssen() {
        return random.nextInt(500) * 10 + 1000;
    }
}
