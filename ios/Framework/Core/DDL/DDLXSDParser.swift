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
import Foundation

#if LIFERAY_SCREENS_FRAMEWORK
	import SMXMLDocument
#endif


public class DDLXSDParser {

	//MARK: Public methods
	
	public func parse(xsd: String, locale: NSLocale) -> [DDLField]? {
		let result: [DDLField]?

		let xmlValue = xsd as NSString

		let data = xmlValue.dataUsingEncoding(NSUTF8StringEncoding)

		do {
			let document = try SMXMLDocument(data: data)
			result = processDocument(document, locale: locale)
		}
		catch {
			result = nil
		}

		return result
	}


	//MARK: Private methods

	private func processDocument(document:SMXMLDocument, locale: NSLocale) -> [DDLField]? {
		var result:[DDLField]?

		if let elements = document.childrenNamed("dynamic-element") {
			let defaultLocale = NSLocale(
				localeIdentifier:document.attributeNamed("default-locale") ?? "en_US")

			result = []

			for element in elements as! [SMXMLElement] {
				if let formField = createFormField(element,
						locale: locale,
						defaultLocale: defaultLocale) {

					result!.append(formField)
				}
			}
		}

		return result
	}

	private func createFormField(xmlElement:SMXMLElement,
			locale: NSLocale,
			defaultLocale: NSLocale)
			-> DDLField? {

		let dataType = DDLField.DataType.from(xmlElement:xmlElement)

		let localizedMetadata = processLocalizedMetadata(xmlElement,
				locale: locale,
				defaultLocale: defaultLocale)

		let mergedAttributes = mergeDictionaries(
				dict1: xmlElement.attributes as! [String:AnyObject],
				dict2: localizedMetadata)

		return dataType.createField(attributes: mergedAttributes, locale: locale)
	}

	private func mergeDictionaries(
			dict1 dict1:[String:AnyObject],
			dict2:[String:AnyObject])
			-> [String:AnyObject] {

		var result:[String:AnyObject] = [:]

		for (key1,value1) in dict1 {
			result.updateValue(value1, forKey: key1)
		}

		for (key2,value2) in dict2 {
			result.updateValue(value2, forKey: key2)
		}

		return result
	}

	private func processLocalizedMetadata(dynamicElement:SMXMLElement,
			locale: NSLocale,
			defaultLocale: NSLocale)
			-> [String:AnyObject] {

		var result:[String:AnyObject] = [:]

		func addElement(
				name elementName: String,
				metadata: SMXMLElement,
				inout result: [String:AnyObject]) {

			if let element = metadata.childWithAttribute("name", value: elementName) {
				result[elementName] = element.value
			}
		}

		func findOptions(
				dynamicElement dynamicElement:SMXMLElement,
				locale: NSLocale,
				defaultLocale: NSLocale)
				-> [[String:AnyObject]]? {

			var options:[[String:AnyObject]] = []

			let optionElements = childrenWithAttribute("type",
					value: "option",
					parent: dynamicElement)
			
			for optionElement in optionElements {
				var option:[String:AnyObject] = [:]

				option["name"] = optionElement.attributeNamed("name")
				option["value"] = optionElement.attributeNamed("value")

				if let localizedMetadata = findMetadataElement(optionElement,
						locale: locale, defaultLocale: defaultLocale) {
					if let element = localizedMetadata.childWithAttribute("name", value: "label") {
						option["label"] = element.value
					}
				}

				options.append(option)
			}

			return options.count == 0 ? nil : options
		}

		if let localizedMetadata = findMetadataElement(dynamicElement,
				locale: locale,
				defaultLocale: defaultLocale) {

			addElement(name: "label", metadata: localizedMetadata, result: &result)
			addElement(name: "predefinedValue", metadata: localizedMetadata, result: &result)
			addElement(name: "tip", metadata: localizedMetadata, result: &result)
		}

		if let options = findOptions(
				dynamicElement: dynamicElement,
				locale: locale,
				defaultLocale: defaultLocale) {

			result["options"] = options
		}

		return result
	}

	private func findMetadataElement(dynamicElement:SMXMLElement,
			locale: NSLocale, defaultLocale: NSLocale)
			-> SMXMLElement? {

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

		let metadataElements = dynamicElement.childrenNamed("meta-data") as? [SMXMLElement]

		if metadataElements == nil {
			return nil
		}

		let currentLanguageCode = locale.objectForKey(NSLocaleLanguageCode) as! String
		let currentCountryCode = locale.objectForKey(NSLocaleCountryCode) as? String

		var resultElement:SMXMLElement?

		if let metadataElement = findElementWithAttribute("locale",
				value:locale.localeIdentifier, elements:metadataElements!) {
			// cases 'a1' and 'b1'

			resultElement = metadataElement
		}
		else {
			if currentCountryCode != nil {
				if let metadataElement = findElementWithAttribute("locale",
						value:currentLanguageCode,
						elements:metadataElements!) {
					// case 'a2'

					resultElement = metadataElement
				}
			}
		}

		if resultElement == nil {
			// Pre-final fallback (a3, b2): find any metadata starting with language

			let foundMetadataElements = metadataElements!.filter(
				{ (metadataElement:SMXMLElement) -> Bool in
					if let metadataLocale = metadataElement.attributes["locale"]?.description {
						return metadataLocale.hasPrefix(currentLanguageCode + "_")
					}

					return false
				})

			resultElement = foundMetadataElements.first
		}

		if resultElement == nil {
			// Final fallback (a4, b3): find default metadata

			resultElement = findElementWithAttribute("locale",
				value:defaultLocale.localeIdentifier, elements:metadataElements!)
		}

		return resultElement
	}

	private func childrenWithAttribute(attribute:String, value:String, parent:SMXMLElement) ->
			[SMXMLElement] {

		var result:[SMXMLElement] = []

		for element in parent.children {
			let child = element as! SMXMLElement
			let attrValue = child.attributeNamed(attribute)
			if attrValue != nil && attrValue == value {
				result.append(child)
			}
		}

		return result
	}

	private func findElementWithAttribute(attribute:String, value:String, elements:[SMXMLElement])
			-> SMXMLElement? {

		for element in elements {
			let attrValue = element.attributeNamed(attribute)
			if attrValue != nil && attrValue == value {
				return element
			}
		}

		return nil
	}

}
