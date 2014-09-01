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

	public var xml:String?
	public var locale:NSLocale

	private(set) var defaultLocale:NSLocale?
	private(set) var availableLocales:[NSLocale]?

	public init(locale:NSLocale) {
		self.locale = locale
	}

	public func parse() -> [DDLElement]? {
		var result:[DDLElement]? = nil

		let xmlString = self.xml as NSString?

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

	private func createFormElement(xmlElement:SMXMLElement) -> DDLElement? {
		var result:DDLElement?

		let dataType = DDLElementDataType.from(xmlElement:xmlElement)

		let localized = processLocalizedMetadata(xmlElement)

		return dataType.createElement(attributes:xmlElement.attributes as [String:String], localized:localized)
	}

	private func processLocalizedMetadata(dynamicElement:SMXMLElement) -> [String:String] {
		var result:[String:String] = [:]

		func addElement(elementName:String, #metadata:SMXMLElement) {
			if let element = metadata.childWithAttribute("name", value: elementName) {
				result[elementName] = element.value
			}
		}

		if let metadataElement = findMetadataElementForLocale(dynamicElement) {
			addElement("label", metadata:metadataElement)
			addElement("predefinedValue", metadata:metadataElement)
			addElement("tip", metadata:metadataElement)
		}

		return result
	}

	private func findMetadataElementForLocale(dynamicElement:SMXMLElement) -> SMXMLElement? {

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

		let currentLanguageCode = self.locale.objectForKey(NSLocaleLanguageCode) as String
		let currentCountryCode = self.locale.objectForKey(NSLocaleCountryCode) as? String

		var resultElement:SMXMLElement?

		if let metadataElement = dynamicElement.childWithAttribute("locale", value: locale.localeIdentifier) {
			// cases 'a1' and 'b1'

			resultElement = metadataElement
		}
		else {
			if currentCountryCode != nil {
				if let metadataElement = dynamicElement.childWithAttribute("locale", value: currentLanguageCode) {
					// case 'a2'

					resultElement = metadataElement
				}
			}
		}

		if resultElement == nil {
			// Pre-final fallback (a3, b2): find any metadata starting with language

			let foundMetadataElements =
				metadataElements!.filter({ (metadataElement:SMXMLElement) -> Bool in
					if let metadataLocale = metadataElement.attributes["locale"]?.description {
						return metadataLocale.hasPrefix(currentLanguageCode + "_")
					}

					return false
				})

			resultElement = foundMetadataElements.first

		}

		if resultElement == nil {
			// Final fallback (a4, b3): find default metadata

			resultElement = dynamicElement.childWithAttribute("locale", value: defaultLocale!.localeIdentifier)
		}

		return resultElement
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

