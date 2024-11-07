package com.javaworld.instagram.userinfoservice.commons.utils;

public class StringUitl {

	public static boolean isEmptyOrNull(String str) {

		if (str == null) {
			return true;
		} else if (str.trim().equals("")) {
			return true;
		}

		return false;
	}

}
