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


class DDLFieldBoolean_Tests: XCTestCase {

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
			"<dynamic-element dataType=\"boolean\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Boolean\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"checkbox\" " +
				"width=\"\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Boolean]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\"> " +
							"<![CDATA[true]]> " +
						"</entry> " +
						"<entry name=\"tip\">" +
							"<![CDATA[The tip]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let fields = parser.parse()

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldBoolean)

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertEqual(DDLField.DataType.DDLBoolean, booleanField.dataType)
		XCTAssertEqual(DDLField.Editor.Checkbox, booleanField.editorType)
		XCTAssertEqual("A_Boolean", booleanField.name)
		XCTAssertEqual("A Boolean", booleanField.label)
		XCTAssertEqual("The tip", booleanField.tip)
		XCTAssertTrue(booleanField.predefinedValue is Bool)
		XCTAssertTrue(booleanField.predefinedValue as Bool)
		XCTAssertFalse(booleanField.readOnly)
		XCTAssertTrue(booleanField.repeatable)
		XCTAssertFalse(booleanField.required)
		XCTAssertTrue(booleanField.showLabel)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"boolean\" " +
						"name=\"A_Boolean\" " +
						"readOnly=\"false\" " +
						"repeatable=\"true\" " +
						"required=\"true\" " +
						"showLabel=\"true\" " +
						"type=\"checkbox\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Boolean]]></entry> " +
					"</meta-data> " +
				"</dynamic-element> </root>"

		let fields = parser.parse()

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertTrue(booleanField.currentValue == nil)

		XCTAssertFalse(booleanField.validate())
	}

}
