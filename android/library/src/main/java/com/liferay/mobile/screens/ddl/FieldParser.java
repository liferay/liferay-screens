package com.liferay.mobile.screens.ddl;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class FieldParser extends AbstractXMLParser {

    public String parseStaticContent(String content, Locale locale) {
        return getLocaleFallbackFromString(content, locale, "static-content", "language-id");
    }

    public String parseTitle(String content, Locale locale) {
        return getLocaleFallbackFromString(content, locale, "Title", "language-id");
    }

    public String parseField(String content, Locale locale, String name) {
        return getChildElementAndFallbackToLocale(content, locale, name);
    }
}
