package com.testfairy.sniff_her.utility;

import android.util.Patterns;

import java.util.List;

public class StringUtil {

    public static void assertNotEmpty(String ...strs) {
        for (String str : strs) {
            if (str == null) throw new NullPointerException("Assertion failed. Given string is null.");
            if (str.isEmpty()) throw new IllegalArgumentException("Assertion failed. Given string is empty.");
        }
    }

    public static boolean allNotEmpty(String ...strs) {
        for (String str : strs) {
            if (str == null) return false;
            if (str.isEmpty()) return false;
        }

        return true;
    }

    public static void assertIsUrl(String ...strs) {
        for (String str : strs) {
            if (str == null) throw new NullPointerException("Assertion failed. Given string is null.");
            if (!Patterns.WEB_URL.matcher(str).matches()) throw new IllegalArgumentException("Assertion failed. Given string is not a url. String: " + str);
        }
    }

    static public String join(List<String> list, String conjunction)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list)
        {
            if (first)
                first = false;
            else
                sb.append(conjunction);
            sb.append(item);
        }
        return sb.toString();
    }

}
