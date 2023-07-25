package com.example.health.ulti;

public class StringUtil {

    public static boolean isEmpty(Object str) {
        if (str == null) {
            return true;
        }
        if (str.toString().trim().length() == 0) {
            return true;
        }
        return false;
    }
}
