package com.testfairy.sniff_her.utility;

public class NumberUtil {

    public static void assertPositiveInteger(int number) {
        if (number <= 0) throw new IllegalArgumentException("Assertion failed, non positive number found. Integer: " + number);
    }

}
