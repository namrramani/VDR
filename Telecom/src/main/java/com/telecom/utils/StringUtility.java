package com.telecom.utils;

public class StringUtility {

	public static boolean isNullOrEmpty(String strValue) {
		if (strValue == null || strValue.equals("") || strValue.trim().length() == 0
				|| strValue.trim().equalsIgnoreCase("?")) {
			return true;
		} else {
			return false;
		}
	}
}
