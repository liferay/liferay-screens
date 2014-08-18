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

public class DDLParser {

	var xml:String?

	public var currentLocale:NSLocale

	private(set) var defaultLocale:NSLocale?
	private(set) var availableLocales:[NSLocale]?

	public init(locale:NSLocale) {
		currentLocale = locale
	}

	public func parse() -> [DDLElement]? {
		var result:[DDLElement]? = nil

		let xmlString = xml as NSString?

		if let xmlValue = xmlString {
			let data = xmlValue.dataUsingEncoding(NSUTF8StringEncoding)

			var outError: NSError?

			if let document = SMXMLDocument.documentWithData(data, error: &outError) {
				result = processDocument(document)
			}
		}

		return result
	}

	private func processDocument(document:SMXMLDocument) -> [DDLElement]? {
		availableLocales = processAvailableLocales(document)
		defaultLocale = NSLocale(localeIdentifier:document.root?.attributeNamed("default-locale")?)

		var result:[DDLElement]?

		if let elements = document.root?.childrenNamed("dynamic-element") {
			result = []

			for element in elements as [SMXMLElement] {
				if let formElement = createFormElement(element) {
					result!.append(formElement)
				}
			}
		}

		return result
	}

	private func createFormElement(element:SMXMLElement) -> DDLElement? {
		var result:DDLElement?

		let dataType = DDLElementDataType.from(xmlElement:element)

		var localizedMetadata = processLocalizedMetadata(element)

		return dataType.createElement(attributes:element.attributes as [String:String], localized:localizedMetadata)
	}

	private func processLocalizedMetadata(dynamicElement:SMXMLElement) -> [String:AnyObject] {
		var result:[String:AnyObject] = [:]

		func addElement(elementName:String, #metadata:SMXMLElement, inout toDict resultDict:[String:AnyObject]) {
			if let element = metadata.childWithAttribute("name", value: elementName) {
				resultDict[elementName] = element.value
			}
		}

		func findOptions() -> [[String:AnyObject]]? {
			var options:[[String:AnyObject]] = []

			let optionElements = childrenWithAttribute("type", value: "option", parent: dynamicElement)
			
			for optionElement in optionElements {
				var option:[String:AnyObject] = [:]

				option["name"] = optionElement.attributeNamed("name")
				option["value"] = optionElement.attributeNamed("value")

				if let localizedMetadata = findLocalizedMetadata(optionElement) {
					addElement("label", metadata: localizedMetadata, toDict:&option)
				}

				options.append(option)
			}

			return options.count == 0 ? nil : options
		}

		if let localizedMetadata = findLocalizedMetadata(dynamicElement) {
			addElement("label", metadata:localizedMetadata, toDict:&result)
			addElement("predefinedValue", metadata:localizedMetadata, toDict:&result)
			addElement("tip", metadata:localizedMetadata, toDict:&result)
		}

		if let options = findOptions() {
			result["options"] = options
		}

		return result
	}

	private func findLocalizedMetadata(dynamicElement:SMXMLElement) -> SMXMLElement? {

		// Locale matching fallback mechanism: it's designed in such a way to return
		// the most suitable locale among the available ones. It minimizes the default 
		// locale fallback. It supports input both with one component (language) and
		// two components (language and country).
		//
		// Examples for currentLocale = "es_ES"
		// 	a1. Matches elements with "es_ES" (full match)
		//  a2. Matches elements with "es"
		//  a3. Matches elements for any country: "es_ES", "es_AR"...
		//  a4. Matches elements for default locale

		// Examples for currentLocale = "es"
		// 	b1. Matches elements with "es" (full match)
		//  b2. Matches elements for any country: "es_ES", "es_AR"...
		//  b3. Matches elements for default locale

		let metadatas = dynamicElement.childrenNamed("meta-data") as? [SMXMLElement]
		if metadatas == nil {
			return nil
		}

		let currentLanguageCode = currentLocale.objectForKey(NSLocaleLanguageCode) as String
		let currentCountryCode = currentLocale.objectForKey(NSLocaleCountryCode) as? String

		var foundMetadata:SMXMLElement? = nil

		if let metadata = findElementWithAttribute("locale", value:currentLocale.localeIdentifier, elements:metadatas!) {
			// cases 'a1' and 'b1'

			foundMetadata = metadata
		}
		else {
			if currentCountryCode != nil {
				if let metadata = findElementWithAttribute("locale", value:currentLanguageCode, elements:metadatas!) {
					// case 'a2'

					foundMetadata = metadata
				}
			}
		}

		if foundMetadata == nil {
			// Pre-final fallback (a3, b2): find any metadata starting with language

			let found = metadatas!.filter(
				{ (metadataElement:SMXMLElement) -> Bool in
					if let metadataLocale = metadataElement.attributes["locale"]?.description {
						return metadataLocale.hasPrefix(currentLanguageCode + "_")
					}

					return false
				})

			foundMetadata = found.first
		}

		if foundMetadata == nil {
			// Final fallback (a4, b3): find default metadata

			foundMetadata = findElementWithAttribute("locale",
				value:defaultLocale!.localeIdentifier, elements:metadatas!)
		}

		return foundMetadata
	}

	private func childrenWithAttribute(attribute:String, value:String, parent:SMXMLElement) -> [SMXMLElement] {
		var result:[SMXMLElement] = []

		for element in parent.children {
			let child = element as SMXMLElement
			let attrValue = child.attributeNamed(attribute)
			if attrValue != nil && attrValue == value {
				result.append(child)
			}
		}

		return result
	}

	private func findElementWithAttribute(attribute:String, value:String, elements:[SMXMLElement]) -> SMXMLElement? {

		for element in elements {
			let attrValue = element.attributeNamed(attribute)
			if attrValue != nil && attrValue == value {
				return element
			}
		}

		return nil
	}

	private func processAvailableLocales(document:SMXMLDocument) -> [NSLocale] {
		var result:[NSLocale] = []

		if let availableLocales = document.root?.attributeNamed("available-locales") {
			let locales = availableLocales.componentsSeparatedByString(",")

			for locale in locales {
				let localeIdentifier = locale.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceCharacterSet())
				result.append(NSLocale(localeIdentifier:localeIdentifier))
			}
		}

		return result
	}

}

