package com.liferay.mobile.screens.ddl;

import androidx.annotation.Nullable;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Javier Gamarra
 */
public abstract class AbstractXMLParser {

    protected Document getDocument(String content) throws ParserConfigurationException, SAXException, IOException {

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Xml cannot be empty");
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new InputSource(new StringReader(content)));
    }

    protected Map<String, Object> getAttributes(Element element) {
        NamedNodeMap nodeMap = element.getAttributes();
        int len = (nodeMap == null) ? 0 : nodeMap.getLength();

        Map<String, Object> result = new HashMap<>(len);

        for (int i = 0; i < len; ++i) {
            Attr n = (Attr) nodeMap.item(i);
            result.put(n.getName(), n.getValue());
        }

        return result;
    }

    protected Element getChild(Element element, String tagName, String attrName, String attrValue) {
        List<Element> elements = getChildren(element, tagName, attrName, attrValue);
        return elements.isEmpty() ? null : elements.get(0);
    }

    protected List<Element> getChildren(Element element, String tagName, String attrName, String attrValue) {
        NodeList childList = element.getElementsByTagName(tagName);
        int len = childList.getLength();

        List<Element> result = new ArrayList<>(len);

        for (int i = 0; i < len; ++i) {
            Element childElement = (Element) childList.item(i);

            if (attrValue.equals(childElement.getAttribute(attrName)) && childElement.getParentNode() == element) {
                result.add(childElement);
            }
        }

        return result;
    }

    @Nullable
    protected Element getLocaleFallback(Element dynamicElement, Locale locale, Locale defaultLocale, String tagName,
        String attrName) {

        // Locale matching fallback mechanism: it's designed in such a way to return
        // the most suitable locale among the available ones. It minimizes the default
        // locale fallback. It supports input both with one component (language) and
        // two components (language and country).
        //
        // Examples for locale = "es_ES"
        // 	a1. Matches elements with "es_ES" (full match)
        //  a2. Matches elements with "es"
        //  a3. Matches elements for any country: "es_ES", "es_AR"...
        //  a4. Matches elements for default locale

        // Examples for locale = "es"
        // 	b1. Matches elements with "es" (full match)
        //  b2. Matches elements for any country: "es_ES", "es_AR"...
        //  b3. Matches elements for default locale

        NodeList metadataList = dynamicElement.getElementsByTagName(tagName);
        int metadataLen = metadataList.getLength();

        if (metadataLen == 0) {
            return null;
        }

        Element resultElement = null;

        Element metadataElement = getChild(dynamicElement, tagName, attrName, locale.toString());

        if (metadataElement != null) {
            // cases 'a1' and 'b1'
            resultElement = metadataElement;
        }

        if (resultElement == null) {

            String supportedLocale = LiferayLocale.getSupportedLocaleWithNoDefault(locale.getLanguage());
            // Pre-final fallback (a2, a3, b2): find any metadata with the portal supported languages

            if (supportedLocale != null) {
                for (int i = 0; resultElement == null && i < metadataLen; ++i) {
                    Element childElement = (Element) metadataList.item(i);
                    String childLocale = childElement.getAttribute(attrName);
                    if (supportedLocale.equals(childLocale) && dynamicElement.equals(childElement.getParentNode())) {
                        resultElement = childElement;
                    }
                }
            }
        }

        if (resultElement == null) {
            // Final fallback (a4, b3): find default metadata
            resultElement = getChild(dynamicElement, tagName, attrName, defaultLocale.toString());
        }

        return resultElement;
    }

    protected String getLocaleFallbackFromString(String content, Locale locale, String tagName, String attrName) {
        try {
            Document document = getDocument(content);
            Element localeFallback =
                getLocaleFallback(document.getDocumentElement(), locale, LiferayLocale.getDefaultLocale(), tagName,
                    attrName);
            return localeFallback == null ? "" : localeFallback.getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LiferayLogger.e("Error parsing value");
            return null;
        }
    }

    protected String getChildElementAndFallbackToLocale(String content, Locale locale, String elementValue) {
        try {
            Document document = getDocument(content);
            Element element = getChildElementAndFallbackToLocale(document.getDocumentElement(), locale, elementValue);
            return element == null ? null : element.getTextContent();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LiferayLogger.e("Error parsing content", e);
        }
        return null;
    }

    @Nullable
    protected Locale getDefaultDocumentLocale(Element root) {
        String defaultLocaleValue = root.getAttribute("default-locale");
        if (defaultLocaleValue.isEmpty()) {
            return null;
        }

        Locale defaultLocale;
        int separator = defaultLocaleValue.indexOf('_');
        if (separator == -1) {
            defaultLocale = new Locale(defaultLocaleValue);
        } else {
            String language = defaultLocaleValue.substring(0, separator);
            String country = defaultLocaleValue.substring(separator + 1);
            defaultLocale = new Locale(language, country);
        }
        return defaultLocale;
    }

    private Element getChildElementAndFallbackToLocale(Element root, Locale locale, String elementValue) {
        Locale defaultLocale = LiferayLocale.getDefaultLocale();
        List<Element> elements = getChildren(root, "dynamic-element", "name", elementValue);

        return elements.isEmpty() ? null
            : getLocaleFallback(elements.get(0), locale, defaultLocale, "dynamic-content", "language-id");
    }
}
