package com.liferay.mobile.screens.util;

/**
 * @author Javier Gamarra
 */
public class LiferayLocale {

	public static String getSupportedLocale(String locale) {
		switch (locale) {
			case "ca":
			case "es":
				return locale + "_ES";
			case "zh":
				return locale + "_CN";
			case "fi":
				return locale + "_FI";
			case "fr":
				return locale + "_FR";
			case "de":
				return locale + "_DE";
			case "iw":
			case "he":
				return "iw_IL";
			case "hu":
				return locale + "_HU";
			case "ja":
				return locale + "_JP";
			case "pt":
				return locale + "_BR";
			default:
				return "en_US";
		}
	}
}
