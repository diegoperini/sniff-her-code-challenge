package com.testfairy.sniff_her.utility;

public class ObjectUtil {

    public static void assertNotNull(Object ...args) {
        for ( Object arg : args) {
            if (arg == null) throw new NullPointerException("Assertion failed, null object found.");
        }
    }

    public static boolean allNotNull(Object ...args) {
        for ( Object arg : args) {
            if (arg == null) return false;
        }

        return true;
    }
}
