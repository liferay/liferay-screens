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

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")


	//MARK: Checking full perfect match

	func test_ParseElement_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: spanishLocale)

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}

	//MARK: Checking locale match providing language and country locales

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "es_MX"))

		XCTAssertEqual("Un Booleano neutro para 'es'", fields![0].label)
	}

	func test_ParseElement_ShouldFindAnyLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "en_GB"))

		XCTAssertEqual("A Boolean for 'en_US'", fields![0].label)
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: NSLocale(localeIdentifier: "es_MX"))

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "fr_FR"))

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: NSLocale(localeIdentifier: "fr_FR"))

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}


	//MARK: Checking locale match providing neutral language locale

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "es"))

		XCTAssertEqual("Un Booleano neutro para 'es'", fields![0].label)
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: NSLocale(localeIdentifier: "es"))

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "fr"))

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: NSLocale(localeIdentifier: "fr"))

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(booleanFieldWithTranslationsXSD, locale: NSLocale(localeIdentifier: "en"))

		XCTAssertEqual("A Boolean for 'en_US'", fields![0].label)
	}

	func test_ParseOption_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDLXSDParser().parse(selectWithTranslatedOptionsXSD, locale: NSLocale(localeIdentifier: "en"))

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual("First label in 'en_US'", stringField.options[0].label)
	}



	private let booleanFieldWithTranslationsXSD =
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

	private let selectWithTranslatedOptionsXSD =
		"<root available-locales=\"en_US, es_ES, es_AR\" default-locale=\"es_ES\"> " +
			"<dynamic-element dataType=\"string\" " +
					"indexType=\"keyword\" " +
					"multiple=\"true\" " +
					"name=\"A_Select\" " +
					"type=\"select\" " +
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
