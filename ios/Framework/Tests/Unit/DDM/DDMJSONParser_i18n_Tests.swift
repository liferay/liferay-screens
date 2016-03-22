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


class DDMJSONParser_i18n_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")


	//MARK: Checking full perfect match

	func test_ParseElement_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: spanishLocale)

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindFullMatch_WhenExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}

	//MARK: Checking locale match providing language and country locales

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "es_MX"))

		XCTAssertEqual("Un Booleano neutro para 'es'", fields![0].label)
	}

	func test_ParseElement_ShouldFindAnyLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "en_GB"))

		XCTAssertTrue(
			(fields![0].label == "A Boolean for 'en_US'") ||
			(fields![0].label == "An austral Boolean for 'en_AU'"))
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: NSLocale(localeIdentifier: "es_MX"))

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "fr_FR"))

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingCompleteLocaleIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: NSLocale(localeIdentifier: "fr_FR"))

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}


	//MARK: Checking locale match providing neutral language locale

	func test_ParseElement_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "es"))

		XCTAssertEqual("Un Booleano neutro para 'es'", fields![0].label)
	}

	func test_ParseOption_ShouldFindNeutralLanguageMatch_WhenExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: NSLocale(localeIdentifier: "es"))

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "fr"))

		XCTAssertEqual("Un Booleano para 'es_ES'", fields![0].label)
	}

	func test_ParseOption_ShouldFindDefault_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: NSLocale(localeIdentifier: "fr"))

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertEqual("Primera etiqueta en 'es_ES'", stringField.options[0].label)
	}

	func test_ParseElement_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(booleanFieldWithTranslationsJSON, locale: NSLocale(localeIdentifier: "en"))

		// Both en_US and en_AU are correct

		XCTAssertTrue(
			(fields![0].label == "A Boolean for 'en_US'") ||
			(fields![0].label == "An austral Boolean for 'en_AU'"))
	}

	func test_ParseOption_ShouldFindAnyLanguageMatch_WhenNoExistingNeutralLanguageIsProvided() {
		let fields = DDMJSONParser().parse(selectWithTranslatedOptionsJSON, locale: NSLocale(localeIdentifier: "en"))

		let stringField = fields![0] as! DDLFieldStringWithOptions

		XCTAssertTrue(
			(stringField.options[0].label == "First label in 'en_US'") ||
			(stringField.options[0].label == "First label in 'en_AU'"))
	}



	private let booleanFieldWithTranslationsJSON =
		"{\"availableLanguageIds\": [\"es_ES\", \"es_AR\", \"es\", \"en_US\", \"en_AU\"]," +
		"\"defaultLanguageId\": \"es_ES\"," +
		"\"fields\": [{" +
		"\"label\": {" +
			"\"en_US\": \"A Boolean for 'en_US'\"," +
			"\"en_AU\": \"An austral Boolean for 'en_AU'\"," +
			"\"es\": \"Un Booleano neutro para 'es'\"," +
			"\"es_ES\": \"Un Booleano para 'es_ES'\"," +
			"\"es_AR\": \"Un boludo Booleano para 'es_AR', ché\"}," +
		"\"tip\": {\"en_US\": \"The tip\"}," +
		"\"dataType\": \"boolean\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"Un_booleano\"," +
		"\"readOnly\": false," +
		"\"repeatable\": false," +
		"\"required\": false," +
		"\"showLabel\": true," +
		"\"width\": \"small\"," +
		"\"type\": \"checkbox\"}]}"

	private let selectWithTranslatedOptionsJSON =
		"{\"availableLanguageIds\": [\"en_US\", \"es_ES\", \"es_AR\"], " +
		"\"defaultLanguageId\": \"es_ES\", " +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Select\"}, " +
		"\"dataType\": \"string\", " +
		"\"indexType\": \"keyword\", " +
		"\"localizable\": true, " +
		"\"name\": \"A_Document\", " +
		"\"readOnly\": false, " +
		"\"repeatable\": true, " +
		"\"required\": false, " +
		"\"showLabel\": true, " +
		"\"multiple\": true, " +
		"\"options\": [" +
			"{\"label\": {" +
				"\"en_US\": \"First label in 'en_US'\", " +
				"\"en_AU\": \"First label in 'en_AU'\", " +
				"\"es\": \"Primera etiqueta en 'es'\", " +
				"\"es_ES\": \"Primera etiqueta en 'es_ES'\", " +
				"\"es_AR\": \"Primera etiqueta en 'es_AR'\"}, " +
			"\"value\": \"value 1\"}]," +
		"\"type\": \"select\"}]}"

}
