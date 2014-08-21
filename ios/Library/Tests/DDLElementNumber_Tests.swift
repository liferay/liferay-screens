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

class DDLElementNumber_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Parse_ShouldExtractValues_WhenElementIsInteger() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"integer\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-integer\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\"> " +
							"<![CDATA[16]]> " +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements![0] is DDLElementNumber)
		let numberElement = elements![0] as DDLElementNumber

		XCTAssertEqual(DDLElementDataType.DDLInteger, numberElement.dataType)
		XCTAssertEqual(DDLElementEditor.Number, numberElement.editorType)
		XCTAssertTrue(numberElement.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberElement.predefinedValue as NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenElementIsNumber() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"number\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-number\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\"> " +
							"<![CDATA[16]]> " +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements![0] is DDLElementNumber)
		let numberElement = elements![0] as DDLElementNumber

		XCTAssertEqual(DDLElementDataType.DDLNumber, numberElement.dataType)
		XCTAssertEqual(DDLElementEditor.Number, numberElement.editorType)
		XCTAssertTrue(numberElement.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberElement.predefinedValue as NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenElementIsDouble() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"double\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-decimal\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\"> " +
							"<![CDATA[16.05]]> " +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements![0] is DDLElementNumber)
		let numberElement = elements![0] as DDLElementNumber

		XCTAssertEqual(DDLElementDataType.DDLDouble, numberElement.dataType)
		XCTAssertEqual(DDLElementEditor.Number, numberElement.editorType)
		XCTAssertTrue(numberElement.predefinedValue is NSDecimalNumber)
		XCTAssertEqualWithAccuracy(16.05,
			(numberElement.predefinedValue as NSDecimalNumber).floatValue, 0.001)
	}

	func test_CurrentValue_ShouldTruncateDecimal_WhenOriginalNumberIsInteger() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"integer\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-integer\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()
		let numberElement = elements![0] as DDLElementNumber

		numberElement.currentValue = 1.1

		XCTAssertTrue(numberElement.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(1), numberElement.currentValue as NSInteger)
	}

	func test_CurrentStringValue_ShouldBeValid_WhenNumberIsInteger() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"integer\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-integer\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()
		let numberElement = elements![0] as DDLElementNumber

		numberElement.currentValue = 99

		XCTAssertEqual("99", numberElement.currentStringValue!)
	}

	func test_CurrentStringValue_ShouldBeValid_WhenNumberIsDecimal() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"double\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-decimal\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()
		let numberElement = elements![0] as DDLElementNumber

		numberElement.currentValue = 16.0599

		XCTAssertEqual("16.06", numberElement.currentStringValue!)
	}

	func test_CurrentStringValue_ShouldBeValid_WhenNumberIsDecimalAndContentIsInteger() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"double\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-decimal\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()
		let numberElement = elements![0] as DDLElementNumber

		numberElement.currentValue = 16

		XCTAssertEqual("16.00", numberElement.currentStringValue!)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"double\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Number\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"true\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-decimal\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Number]]>" +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()

		let numberElement = elements![0] as DDLElementNumber

		XCTAssertTrue(numberElement.currentValue == nil)
		XCTAssertFalse(numberElement.validate())
	}

}
