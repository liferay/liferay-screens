package com.liferay.mobile.screens.util;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class LocaleChanged {

    private final Locale newLocale;
    private final Locale oldLocale;

    public LocaleChanged(Locale newLocale, Locale oldLocale) {
        this.newLocale = newLocale;
        this.oldLocale = oldLocale;
    }

    public Locale getNewLocale() {
        return newLocale;
    }

    public Locale getOldLocale() {
        return oldLocale;
    }
}
