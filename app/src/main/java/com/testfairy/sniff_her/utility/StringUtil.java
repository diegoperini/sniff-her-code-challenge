package com.testfairy.sniff_her.utility;

import android.util.Patterns;

public class StringUtil {

    public static void assertNotEmpty(String ...strs) {
        for (String str : strs) {
            if (str == null) throw new NullPointerException("Assertion failed. Given string is null.");
            if (str.isEmpty()) throw new IllegalArgumentException("Assertion failed. Given string is empty.");
        }
    }

    public static void assertIsUrl(String ...strs) {
        for (String str : strs) {
            if (str == null) throw new NullPointerException("Assertion failed. Given string is null.");
            if (!Patterns.WEB_URL.matcher(str).matches()) throw new IllegalArgumentException("Assertion failed. Given string is not a url. String: " + str);
        }
    }

}
