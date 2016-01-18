package com.liferay.mobile.screens.util;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class LocaleChanged {

	public LocaleChanged(Locale newLocale, Locale oldLocale) {
		_newLocale = newLocale;
		_oldLocale = oldLocale;
	}

	public Locale getNewLocale() {
		return _newLocale;
	}

	public Locale getOldLocale() {
		return _oldLocale;
	}

	private final Locale _newLocale;
	private final Locale _oldLocale;
}
