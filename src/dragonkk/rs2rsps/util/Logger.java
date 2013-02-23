package dragonkk.rs2rsps.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public synchronized static String getDate() {
        return dateFormat.format(new Date());
    }

    public static synchronized void log(Object clas, Object message) {
        System.out.println("[" + getDate() + "] [" + clas.getClass().getSimpleName() + "] " + message);
    }

    public static synchronized void lognoln(Object clas, Object message) {
        System.out.print("[" + getDate() + "] [" + clas.getClass().getSimpleName() + "] " + message);
    }

    public static void log(String classnam, String message) {
        System.out.println("[" + getDate() + "] [" + classnam + "] " + message);
    }

    public static void lognoln(String classnam, String message) {
        System.out.print("[" + getDate() + "] [" + classnam + "] " + message);
    }

    public static void logErrors(String string, String string2) {

    }

}