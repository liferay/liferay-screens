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
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Jose Manuel Navarro
 */
public class XSDParser extends AbstractXMLParser implements DDMStructureParser {

    public List<Field> parse(String xml, Locale locale) throws SAXException {
        try {
            Document document = getDocument(xml);
            return processDocument(document, locale);
        } catch (ParserConfigurationException | IOException e) {
            LiferayLogger.e("Error parsing form", e);
            return null;
        }
    }

    protected List<Field> processDocument(Document document, Locale locale) {
        List<Field> result = new ArrayList<>();

        Element root = document.getDocumentElement();

        Locale defaultLocale = getDefaultDocumentLocale(root);

        NodeList dynamicElementList = root.getElementsByTagName("dynamic-element");

        Field parentField = null;

        int len = dynamicElementList.getLength();
        for (int i = 0; i < len; ++i) {
            Element dynamicElement = (Element) dynamicElementList.item(i);
            Field childField = createFormField(dynamicElement, locale, defaultLocale);
            if (dynamicElement.getParentNode() == root) {
                if (childField != null) {
                    result.add(childField);
                }
                parentField = childField;
            } else {
                if (childField != null) {
                    parentField.getFields().add(childField);
                }
            }
        }

        return result;
    }

    protected Field createFormField(Element dynamicElement, Locale locale, Locale defaultLocale) {
        Field.DataType dataType = Field.DataType.valueOf(dynamicElement);

        Map<String, Object> attributes = getAttributes(dynamicElement);

        Map<String, Object> localizedMetadata = processLocalizedMetadata(dynamicElement, locale, defaultLocale);

        Map<String, Object> mergedMap = new HashMap<>();

        mergedMap.putAll(attributes);
        mergedMap.putAll(localizedMetadata);

        return dataType.createField(mergedMap, locale, defaultLocale);
    }

    protected Map<String, Object> processLocalizedMetadata(Element dynamicElement, Locale locale,
        Locale defaultLocale) {

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

    protected List<Map<String, String>> findOptions(Element dynamicElement, Locale locale, Locale defaultLocale) {

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
            } else {
                // use value as fallback
                optionMap.put("label", optionDynamicElement.getAttribute("value"));
            }

            result.add(optionMap);
        }

        return result;
    }

    protected void addLocalizedElement(Element localizedMetadata, String elementName, Map<String, Object> result) {

        Element foundElement = getChild(localizedMetadata, "entry", "name", elementName);
        if (foundElement != null) {
            Node contentNode = foundElement.getFirstChild();
            if (contentNode != null) {
                result.put(elementName, contentNode.getNodeValue());
            }
        }
    }

    protected Element findMetadataElement(Element dynamicElement, Locale locale, Locale defaultLocale) {

        return getLocaleFallback(dynamicElement, locale, defaultLocale, "meta-data", "locale");
    }
}
