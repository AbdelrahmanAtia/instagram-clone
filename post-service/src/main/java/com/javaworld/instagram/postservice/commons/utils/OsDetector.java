package com.javaworld.instagram.postservice.commons.utils;
public class OsDetector {

    final public static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("windows");
    }

}