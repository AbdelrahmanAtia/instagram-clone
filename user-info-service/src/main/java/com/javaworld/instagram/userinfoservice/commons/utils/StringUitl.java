package com.javaworld.instagram.userinfoservice.commons.utils;

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
