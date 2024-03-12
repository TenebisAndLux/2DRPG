package org.sphinx.util;

import java.lang.management.ManagementFactory;
import java.util.List;

public class Debug {
    private static boolean isDebugging = false;
    private static boolean isTesting = false;

    static {
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (String arg : inputArguments) {
            if (arg.contains("jdwp") || arg.contains("debug")) {
                isDebugging = true;
                break;
            }
        }
    }

    public static void log(String text) {
        if (isDebugging) {
            System.out.println(text);
        }
    }

    public static void log(String text, String... strings) {
        if (isDebugging) {
            StringBuilder stringBuilder = new StringBuilder(text);
            for (String str : strings)
                stringBuilder.append(str);
            System.out.println(stringBuilder);
        }
    }

    public static void testLog(String text) {
        if (isTesting) {
            System.out.println(text);
        }
    }

    public static void err(String text) {
        if (isDebugging) {
            System.err.println(text);
        }
    }

    public static void err(String text, Exception e) {
        if (isDebugging) {
            System.err.println("\n" + text + " : " + e + "\t");
            e.printStackTrace();
        }
    }

    public static void setDebugMod(boolean sign) {
        isDebugging = sign;
    }

    public static boolean isIsDebugging() {
        return isDebugging;
    }

    public static void setTestDebugMod(boolean sign) {
        isTesting = sign;
    }
}
