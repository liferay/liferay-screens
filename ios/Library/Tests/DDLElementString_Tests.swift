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

class DDLElementString_Tests: XCTestCase {

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

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementString)

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual(DDLElementDataType.DDLString, stringElement.dataType)
		XCTAssertEqual(DDLElementEditor.Text, stringElement.editorType)
		XCTAssertEqual("A_Text", stringElement.name)
		XCTAssertEqual("A Text", stringElement.label)
		XCTAssertEqual("The tip", stringElement.tip)
		XCTAssertTrue(stringElement.predefinedValue is String)
		XCTAssertEqual("predefined text", stringElement.predefinedValue as String)
		XCTAssertFalse(stringElement.readOnly)
		XCTAssertTrue(stringElement.repeatable)
		XCTAssertFalse(stringElement.required)
		XCTAssertTrue(stringElement.showLabel)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		parser.xml = requiredText

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		stringElement.currentValue = ""

		XCTAssertFalse(stringElement.validate())
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		parser.xml = requiredText

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		stringElement.currentValue = "  "

		XCTAssertFalse(stringElement.validate())
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
