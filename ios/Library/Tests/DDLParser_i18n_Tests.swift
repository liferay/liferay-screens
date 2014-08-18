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
import XCTest

class DDLParser_i18n_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	//MARK: Checking header fields

	func test_ParseHeader_ShouldReadAvailableLocales() {
		parser.xml = "<root available-locales=\"en_US, es_ES\" default-locale=\"es_ES\"> </root>"

		parser.parse()

		XCTAssertNotNil(parser.availableLocales)
		XCTAssertEqual(2, parser.availableLocales!.count)
		XCTAssertEqual("en_US", parser.availableLocales![0].localeIdentifier!)
		XCTAssertEqual("es_ES", parser.availableLocales![1].localeIdentifier!)
	}

	func test_ParseHeader_ShouldReadDefaultLocale() {
		parser.xml = "<root available-locales=\"en_US, es_ES\" default-locale=\"es_ES\"> </root>"

		parser.parse()

		XCTAssertNotNil(parser.defaultLocale)
		XCTAssertEqual("es_ES", parser.defaultLocale!.localeIdentifier!)
	}

	func test_ParseElement_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es_ES")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("Un Booleano para 'es_ES'", elements![0].label)
	}

	func test_ParseOption_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es_ES")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringElement.options[0].label)
	}

	//MARK: Checking locale match providing language and country locales

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es_MX")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("Un Booleano neutro para 'es'", elements![0].label)
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es_MX")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("Primera etiqueta en 'es'", stringElement.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "fr_FR")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("Un Booleano para 'es_ES'", elements![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "fr_FR")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringElement.options[0].label)
	}


	//MARK: Checking locale match providing neutral language locale

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("Un Booleano neutro para 'es'", elements![0].label)
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "es")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("Primera etiqueta en 'es'", stringElement.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "fr")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("Un Booleano para 'es_ES'", elements![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "fr")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringElement.options[0].label)
	}

	func test_ParseElement_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "en")
		parser.xml = booleanElementWithTranslations

		let elements = parser.parse()

		XCTAssertEqual("A Boolean for 'en_US'", elements![0].label)
	}

	func test_ParseOption_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		parser.currentLocale = NSLocale(localeIdentifier: "en")
		parser.xml = selectWithTranslatedOptions

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual("First label in 'en_US'", stringElement.options[0].label)
	}



	private let booleanElementWithTranslations =
		"<root available-locales=\"es_ES, es_AR, es, en_US, en_AU\" default-locale=\"es_ES\"> " +
			"<dynamic-element dataType=\"boolean\" " +
					"name=\"Un_booleano\" " +
					"readOnly=\"false\" " +
					"repeatable=\"false\" " +
					"required=\"false\" " +
					"showLabel=\"true\" " +
					"type=\"checkbox\" > " +
				"<meta-data locale=\"en_US\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[A Boolean for 'en_US']]>" +
					"</entry> " +
				"</meta-data> " +
				"<meta-data locale=\"en_AU\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[An austral Boolean for 'en_AU']]>" +
					"</entry> " +
				"</meta-data> " +
				"<meta-data locale=\"es\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[Un Booleano neutro para 'es']]>" +
					"</entry> " +
				"</meta-data> " +
				"<meta-data locale=\"es_ES\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[Un Booleano para 'es_ES']]>" +
					"</entry> " +
				"</meta-data> " +
				"<meta-data locale=\"es_AR\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[Un boludo Booleano para 'es_AR', ché]]>" +
					"</entry> " +
				"</meta-data> " +
		"</dynamic-element></root>"

	private let selectWithTranslatedOptions =
		"<root available-locales=\"en_US, es_ES, es_AR\" default-locale=\"es_ES\"> " +
			"<dynamic-element dataType=\"string\" " +
					"indexType=\"keyword\" " +
					"multiple=\"true\" " +
					"name=\"A_Select\" " +
					"type=\"text\" " +
					"width=\"small\"> " +
				"<meta-data locale=\"en_US\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[A Select]]>" +
					"</entry> " +
				"</meta-data> " +
				"<dynamic-element name=\"option_1\" type=\"option\" value=\"value 1\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[First label in 'en_US']]>" +
						"</entry> " +
					"</meta-data> " +
					"<meta-data locale=\"en_AU\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[First label in 'en_AU']]>" +
						"</entry> " +
					"</meta-data> " +
					"<meta-data locale=\"es_ES\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[Primera etiqueta en 'es_ES']]>" +
						"</entry> " +
					"</meta-data> " +
					"<meta-data locale=\"es_AR\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[Primera etiqueta en 'es_AR']]>" +
						"</entry> " +
					"</meta-data> " +
					"<meta-data locale=\"es\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[Primera etiqueta en 'es']]>" +
						"</entry> " +
					"</meta-data> " +
				"</dynamic-element> " +
			"</dynamic-element> </root>"

}
