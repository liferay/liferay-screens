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


class DDLFieldString_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

	func test_Parse_ShouldExtractValues() {
		let json =
			"{\"availableLanguageIds\": [\"en_US\"]," +
			"\"defaultLanguageId\": \"en_US\"," +
			"\"fields\": [{" +
			"\"label\": {\"en_US\": \"A Text\"}," +
			"\"predefinedValue\": {\"en_US\": \"predefined text\"}," +
			"\"tip\": {\"en_US\": \"The tip\"}," +
			"\"dataType\": \"string\"," +
			"\"indexType\": \"keyword\"," +
			"\"localizable\": true," +
			"\"name\": \"A_Text\"," +
			"\"readOnly\": false," +
			"\"repeatable\": true," +
			"\"required\": false," +
			"\"showLabel\": true," +
			"\"width\": \"small\"," +
			"\"type\": \"text\"}]}"

		let fields = DDLJSONParser().parse(json, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldString)

		let stringField = fields![0] as! DDLFieldString

		XCTAssertEqual(DDLField.DataType.DDLString, stringField.dataType)
		XCTAssertEqual(DDLField.Editor.Text, stringField.editorType)
		XCTAssertEqual("A_Text", stringField.name)
		XCTAssertEqual("A Text", stringField.label)
		XCTAssertEqual("The tip", stringField.tip)
		XCTAssertTrue(stringField.predefinedValue is String)
		XCTAssertEqual("predefined text", stringField.predefinedValue as? String)
		XCTAssertFalse(stringField.readOnly)
		XCTAssertTrue(stringField.repeatable)
		XCTAssertFalse(stringField.required)
		XCTAssertTrue(stringField.showLabel)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		let fields = DDLJSONParser().parse(requiredTextJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDLFieldString

		stringField.currentValue = ""

		XCTAssertFalse(stringField.validate())
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		let fields = DDLJSONParser().parse(requiredTextJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDLFieldString

		stringField.currentValue = "  "

		XCTAssertFalse(stringField.validate())
	}

	private let requiredTextJSON =
		"{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Text\"}," +
		"\"dataType\": \"string\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Text\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"width\": \"small\"," +
		"\"type\": \"text\"}]}"

}
