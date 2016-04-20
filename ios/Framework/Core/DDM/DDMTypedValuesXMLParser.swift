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


public class DDMTypedValuesXMLParser {

	public var createdStructure: DDMStructure?

	//MARK: Public methods
	
	public func parse(xml: String, structure: DDMStructure?) -> Int {
		let data = xml.dataUsingEncoding(NSUTF8StringEncoding)

		guard let document = try? SMXMLDocument(data: data) else {
			return -1
		}

		return processDocument(document,
		   locale: structure?.locale
				?? NSLocale(localeIdentifier: NSLocale.currentLocaleString),
		   structure: structure)
	}


	//MARK: Private methods

	private func processDocument(document: SMXMLDocument, locale: NSLocale, structure: DDMStructure?) -> Int {
		var count = 0
		var createdFields = [DDMField]()

		if let elements = document.childrenNamed("dynamic-element") {
			let defaultLocale = NSLocale(
				localeIdentifier:document.attributeNamed("default-locale") ?? "en_US")

			for element in (elements as! [SMXMLElement]) {
				if let fieldName = element.attributeNamed("name"),
						value = getFieldValue(element, locale: locale, defaultLocale: defaultLocale) {

					if let field = structure?.fieldBy(name: fieldName) {
						field.currentValueAsString = value
					}
					else {
						// this field isn't present in the structure: create it
						if let field = createFormField(element,
								locale: locale,
								defaultLocale: defaultLocale) {
							field.currentValueAsString = value
							createdFields.append(field)
						}
					}

					count += 1
				}
			}
		}

		if createdFields.count > 0 {
			createdStructure = DDMStructure(
				fields: createdFields,
				locale: locale,
				attributes: [:])
		}

		return count
	}

	private func createFormField(xmlElement: SMXMLElement,
			locale: NSLocale,
	        defaultLocale: NSLocale) -> DDMField? {

		let editor = DDMField.Editor.from(
			attributes: xmlElement.attributes as! [String:AnyObject])

		return editor.defaultDataType.createField(
			attributes: xmlElement.attributes as! [String:AnyObject],
			locale: locale,
			version: LiferayServerContext.serverVersion)
	}


	private func getFieldValue(xmlElement: SMXMLElement,
			locale: NSLocale,
			defaultLocale: NSLocale) -> String? {

		let localizedContent = findContentElement(xmlElement,
				locale: locale,
				defaultLocale: defaultLocale)

		return localizedContent?.value
	}

	private func findContentElement(dynamicElement: SMXMLElement,
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

		guard let contentElements = dynamicElement.childrenNamed("dynamic-content") as? [SMXMLElement] else {
			return nil
		}

		let currentLanguageCode = locale.objectForKey(NSLocaleLanguageCode) as! String
		let currentCountryCode = locale.objectForKey(NSLocaleCountryCode) as? String

		var resultElement: SMXMLElement?

		if let metadataElement = findElementWithAttribute("language-id",
				value: locale.localeIdentifier, elements: contentElements) {
			// cases 'a1' and 'b1'

			resultElement = metadataElement
		}
		else {
			if currentCountryCode != nil {
				if let metadataElement = findElementWithAttribute("language-id",
						value: currentLanguageCode,
						elements: contentElements) {
					// case 'a2'

					resultElement = metadataElement
				}
			}
		}

		if resultElement == nil {
			// Pre-final fallback (a3, b2): find any metadata starting with language

			resultElement = contentElements.filter {
				if let contentLocale = $0.attributes["language-id"]?.description {
					return contentLocale.hasPrefix(currentLanguageCode + "_")
				}

				return false
			}.first
		}

		if resultElement == nil {
			// Final fallback (a4, b3): find default metadata

			resultElement = findElementWithAttribute("language-id",
				value: defaultLocale.localeIdentifier, elements: contentElements)
		}

		return resultElement
	}

	private func childrenWithAttribute(attribute:String, value:String, parent:SMXMLElement) ->
			[SMXMLElement] {

		var result: [SMXMLElement] = []

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
		return elements.filter {
			let attrValue = $0.attributeNamed(attribute)
			return (attrValue != nil && attrValue == value)
		}.first
	}

}
