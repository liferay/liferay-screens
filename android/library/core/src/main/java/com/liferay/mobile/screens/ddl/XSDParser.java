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

import com.liferay.mobile.screens.ddl.field.Field;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
			throw new IllegalArgumentException("Xml cannot be null or empty");
		}

		List<Field> result = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(new StringReader(xml)));

			result = processDocument(document, locale);
		}
		catch (ParserConfigurationException e) {
			//TODO this shouldn't happen
		}
		catch (IOException e) {
			//TODO this shouldn't happen
		}

		return result;
	}

	protected List<Field> processDocument(Document document, Locale locale) {
		List<Field> result = new ArrayList<Field>();

		Element root = document.getDocumentElement();

		NodeList dynamicElementList = root.getElementsByTagName("dynamic-element");

		int len = dynamicElementList.getLength();
		for (int i = 0; i < len; ++i) {
			Element dynamicElement = (Element) dynamicElementList.item(i);

			Field formField = createFormField(dynamicElement, locale);

			if (formField != null) {
				result.add(formField);
			}
		}

		return result;
	}

	protected Field createFormField(Element dynamicElement, Locale locale) {
		Field.DataType dataType = Field.DataType.valueOf(dynamicElement);

		Map<String, String> localizedMetadata =
			processLocalizedMetadata(dynamicElement, locale);

		Map<String, String> attributes = getAttributes(dynamicElement);

		Map<String, String> mergedMap = new HashMap<String, String>();

		mergedMap.putAll(localizedMetadata);
		mergedMap.putAll(attributes);

		return dataType.createField(mergedMap, locale);
	}

	protected Map<String,String> processLocalizedMetadata(Element dynamicElement, Locale locale) {
		Map<String, String> result = new HashMap<String, String>();

		Element localizedMetadata = findMetadataElement(dynamicElement, locale);
		if (localizedMetadata != null) {
			addLocalizedElement(localizedMetadata, "label", result);
			addLocalizedElement(localizedMetadata, "predefinedValue", result);
			addLocalizedElement(localizedMetadata, "tip", result);
		}

		return result;
	}

	protected void addLocalizedElement(
		Element localizedMetadata, String elementName, Map<String, String> result) {

		Element foundElement = getChild(localizedMetadata, "entry", "name", elementName);
		if (foundElement != null) {
			result.put(elementName, foundElement.getFirstChild().getNodeValue());
		}
	}

	protected Element findMetadataElement(Element dynamicElement, Locale locale) {
		NodeList metadataList = dynamicElement.getElementsByTagName("meta-data");
		//TODO locale matching
		return (Element) metadataList.item(0);
	}

	protected Map<String, String> getAttributes(Element element) {
		NamedNodeMap nodeMap = element.getAttributes();
		int len = (nodeMap == null) ? 0 : nodeMap.getLength();

		Map<String, String> result = new HashMap<String, String>(len);

		for (int i = 0; i < len; ++i) {
			Attr n = (Attr) nodeMap.item(i);
			result.put(n.getName(), n.getValue());
		}

		return result;
	}

	protected Element getChild(Element element, String tagName, String attrName, String attrValue) {
		NodeList childList = element.getElementsByTagName(tagName);

		int len = (childList == null) ? 0 : childList.getLength();
		for (int i = 0; i < len; ++i) {
			Element childElement = (Element) childList.item(i);

			if (attrValue.equals(childElement.getAttribute(attrName))) {
				return childElement;
			}
		}

		return null;
	}

}
