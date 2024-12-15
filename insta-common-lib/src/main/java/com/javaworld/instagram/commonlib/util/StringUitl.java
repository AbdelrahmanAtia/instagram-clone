package com.javaworld.instagram.commonlib.util;
public class StringUitl {

	public static boolean isBlankOrNull(String str) {

		if (str == null) {
			return true;
		} else if (str.isBlank()) {
			return true;
		}

		return false;
	}

}
