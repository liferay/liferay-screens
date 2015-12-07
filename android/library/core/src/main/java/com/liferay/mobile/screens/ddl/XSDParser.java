/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.util.LiferayLocale;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

/**
 * @author Jose Manuel Navarro
 */
public class XSDParser {

	public List<Field> parse(String xml, Locale locale) throws SAXException {
		if (xml == null || xml.isEmpty()) {
			throw new IllegalArgumentException("Xml cannot be empty");
		}

		List<Field> result = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(xml)));

			result = processDocument(document, locale);
		}
		catch (ParserConfigurationException | IOException e) {
			//TODO this shouldn't happen
		}

		return result;
	}

	protected List<Field> processDocument(Document document, Locale locale) {
		List<Field> result = new ArrayList<>();

		Element root = document.getDocumentElement();

		String defaultLocaleValue = root.getAttribute("default-locale");
		if (defaultLocaleValue == null) {
			return null;
		}

		Locale defaultLocale;
		int separator = defaultLocaleValue.indexOf('_');
		if (separator == -1) {
			defaultLocale = new Locale(defaultLocaleValue);
		}
		else {
			String language = defaultLocaleValue.substring(0, separator);
			String country = defaultLocaleValue.substring(separator + 1);
			defaultLocale = new Locale(language, country);
		}

		NodeList dynamicElementList = root.getElementsByTagName("dynamic-element");

		int len = dynamicElementList.getLength();
		for (int i = 0; i < len; ++i) {
			Element dynamicElement = (Element) dynamicElementList.item(i);
			if (dynamicElement.getParentNode() == root) {
				Field formField = createFormField(dynamicElement, locale, defaultLocale);

				if (formField != null) {
					result.add(formField);
				}
			}
		}

		return result;
	}

	protected Field createFormField(Element dynamicElement, Locale locale, Locale defaultLocale) {
		Field.DataType dataType = Field.DataType.valueOf(dynamicElement);

		Map<String, Object> attributes = getAttributes(dynamicElement);

		Map<String, Object> localizedMetadata =
			processLocalizedMetadata(dynamicElement, locale, defaultLocale);

		Map<String, Object> mergedMap = new HashMap<>();

		mergedMap.putAll(attributes);
		mergedMap.putAll(localizedMetadata);

		return dataType.createField(mergedMap, locale, defaultLocale);
	}

	protected Map<String, Object> processLocalizedMetadata(
		Element dynamicElement, Locale locale, Locale defaultLocale) {

		Map<String, Object> result = new HashMap<>();

		Element localizedMetadata = findMetadataElement(dynamicElement, locale, defaultLocale);
		if (localizedMetadata != null) {
			addLocalizedElement(localizedMetadata, "label", result);
			addLocalizedElement(localizedMetadata, "predefinedValue", result);
			addLocalizedElement(localizedMetadata, "tip", result);
		}

		List<Map<String, String>> options = findOptions(dynamicElement, locale, defaultLocale);
		if (!options.isEmpty()) {
			result.put("options", options);
		}

		return result;
	}

	protected List<Map<String, String>> findOptions(
		Element dynamicElement, Locale locale, Locale defaultLocale) {

		List<Element> options = getChildren(dynamicElement, "dynamic-element", "type", "option");

		List<Map<String, String>> result = new ArrayList<>(options.size());

		for (Element optionDynamicElement : options) {
			Map<String, String> optionMap = new HashMap<>();

			optionMap.put("name", optionDynamicElement.getAttribute("name"));
			optionMap.put("value", optionDynamicElement.getAttribute("value"));

			Element localizedLabelMetadata = findMetadataElement(optionDynamicElement, locale, defaultLocale);

			Element foundLabelElement = getChild(localizedLabelMetadata, "entry", "name", "label");
			if (foundLabelElement != null) {
				optionMap.put("label", foundLabelElement.getFirstChild().getNodeValue());
			}
			else {
				// use value as fallback
				optionMap.put("label", optionDynamicElement.getAttribute("value"));
			}

			result.add(optionMap);
		}

		return result;
	}

	protected void addLocalizedElement(
		Element localizedMetadata, String elementName, Map<String, Object> result) {

		Element foundElement = getChild(localizedMetadata, "entry", "name", elementName);
		if (foundElement != null) {
			Node contentNode = foundElement.getFirstChild();
			if (contentNode != null) {
				result.put(elementName, contentNode.getNodeValue());
			}
		}
	}

	protected Element findMetadataElement(
		Element dynamicElement, Locale locale, Locale defaultLocale) {

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

		NodeList metadataList = dynamicElement.getElementsByTagName("meta-data");
		int metadataLen = (metadataList == null) ? 0 : metadataList.getLength();

		if (metadataLen == 0) {
			return null;
		}

		Element resultElement = null;

		Element metadataElement = getChild(
			dynamicElement, "meta-data", "locale", locale.toString());

		if (metadataElement != null) {
			// cases 'a1' and 'b1'
			resultElement = metadataElement;
		}


		if (resultElement == null) {

			String supportedLocale = LiferayLocale.getSupportedLocale(locale.getDisplayLanguage());
			// Pre-final fallback (a2, a3, b2): find any metadata with the portal supported languages
			for (int i = 0; resultElement == null && i < metadataLen; ++i) {
				Element childElement = (Element) metadataList.item(i);
				String childLocale = childElement.getAttribute("locale");
				if (childLocale != null && supportedLocale.equals(childLocale)
					&& dynamicElement.equals(childElement.getParentNode())) {
					resultElement = childElement;
				}
			}
		}

		if (resultElement == null) {
			// Final fallback (a4, b3): find default metadata
			resultElement = getChild(
				dynamicElement, "meta-data", "locale", defaultLocale.toString());
		}

		return resultElement;
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
		int len = (childList == null) ? 0 : childList.getLength();

		List<Element> result = new ArrayList<>(len);

		for (int i = 0; i < len; ++i) {
			Element childElement = (Element) childList.item(i);

			if (attrValue.equals(childElement.getAttribute(attrName)) &&
				childElement.getParentNode() == element) {
				result.add(childElement);
			}
		}

		return result;
	}

}
