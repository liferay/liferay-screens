package com.liferay.mobile.screens.ddl;

import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Javier Gamarra
 */
public class StaticParser extends XMLParser {

	public String parse(String content, String tagName, Locale locale) {
		try {
			Document document = getDocument(content);

			String attrName = "language-id";

			Element localeFallback = getLocaleFallback(document.getDocumentElement(), locale, LiferayLocale.getDefaultLocale(), tagName, attrName);
			return localeFallback == null ? "" : localeFallback.getTextContent();

		}
		catch (ParserConfigurationException | SAXException | IOException e) {
			LiferayLogger.e("Error parsing value");
			return null;
		}
	}
}
