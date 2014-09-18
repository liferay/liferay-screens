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

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Parse_ShouldExtractValues() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"string\" " +
						"indexType=\"keyword\" " +
						"name=\"A_Text\" " +
						"readOnly=\"false\" " +
						"repeatable=\"true\" " +
						"required=\"false\" " +
						"showLabel=\"true\" " +
						"type=\"text\" " +
						"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Text]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\">" +
							"<![CDATA[predefined text]]>" +
						"</entry> " +
						"<entry name=\"tip\">" +
							"<![CDATA[The tip]]>" +
						"</entry> " +
					"</meta-data> " +
				"</dynamic-element> </root>"

		let fields = parser.parse()

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldString)

		let stringField = fields![0] as DDLFieldString

		XCTAssertEqual(DDLFieldDataType.DDLString, stringField.dataType)
		XCTAssertEqual(DDLFieldEditor.Text, stringField.editorType)
		XCTAssertEqual("A_Text", stringField.name)
		XCTAssertEqual("A Text", stringField.label)
		XCTAssertEqual("The tip", stringField.tip)
		XCTAssertTrue(stringField.predefinedValue is String)
		XCTAssertEqual("predefined text", stringField.predefinedValue as String)
		XCTAssertFalse(stringField.readOnly)
		XCTAssertTrue(stringField.repeatable)
		XCTAssertFalse(stringField.required)
		XCTAssertTrue(stringField.showLabel)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		parser.xml = requiredText

		let fields = parser.parse()

		let stringField = fields![0] as DDLFieldString

		stringField.currentValue = ""

		XCTAssertFalse(stringField.validate())
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		parser.xml = requiredText

		let fields = parser.parse()

		let stringField = fields![0] as DDLFieldString

		stringField.currentValue = "  "

		XCTAssertFalse(stringField.validate())
	}

	private let requiredText =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"string\" " +
						"name=\"A_Text\" " +
						"readOnly=\"false\" " +
						"repeatable=\"true\" " +
						"required=\"true\" " +
						"showLabel=\"true\" " +
						"type=\"checkbox\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Text]]>" +
						"</entry> " +
					"</meta-data> " +
				"</dynamic-element> </root>"

}
