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


class DDLFieldNumber_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

	func test_Parse_ShouldExtractValues_WhenFieldIsInteger() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLInteger, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenFieldIsNumber() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLNumber, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenFieldIsDouble() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLDouble, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSDecimalNumber)
		XCTAssertEqualWithAccuracy(16.05,
			(numberField.predefinedValue as NSDecimalNumber).floatValue, 0.001)
	}

	func test_CurrentValue_ShouldTruncateDecimal_WhenOriginalNumberIsInteger() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValue = 1.1

		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(1), numberField.currentValue as NSInteger)
	}

	func test_currentValueAsString_ShouldBeValid_WhenNumberIsInteger() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValue = 99

		XCTAssertEqual("99", numberField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldBeValid_WhenNumberIsDecimal() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValue = 16.0599

		XCTAssertEqual("16.06", numberField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldBeValid_WhenNumberIsDecimalAndContentIsInteger() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValue = 16

		XCTAssertEqual("16.00", numberField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldBeChanged_WhenNumberIsInteger() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValueAsString = "99"

		XCTAssertEqual("99", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(99), numberField.currentValue as NSInteger)
	}

	func test_currentValueAsString_ShouldBeChanged_WhenNumberIsIntegerAndValueIsDecimal() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValueAsString = "99.88"

		XCTAssertEqual("100", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(100), numberField.currentValue as NSInteger)
	}

	func test_currentValueAsString_ShouldBeChanged_WhenNumberIsDecimal() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)
		let numberField = fields![0] as DDLFieldNumber

		numberField.currentValueAsString = "99.98"

		XCTAssertEqual("99.98", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSDecimalNumber)
		XCTAssertEqualWithAccuracy(99.98,
			(numberField.currentValue as NSDecimalNumber).floatValue, 0.001)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		let numberField = fields![0] as DDLFieldNumber

		XCTAssertTrue(numberField.currentValue == nil)
		XCTAssertFalse(numberField.validate())
	}

}
