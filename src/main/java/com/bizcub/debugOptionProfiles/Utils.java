package com.bizcub.debugOptionProfiles;

public class Utils {

    public static String getTranslationKey(String key) {
        if (key.contains("key='")) {
            key = key.substring(key.indexOf("key='")+5);
            return key.substring(0, key.indexOf("'"));
        }
        return key;
    }
}
