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


	//MARK: parse

	func test_Parse_ShouldExtractValues_WhenFieldIsInteger() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as! DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLInteger, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as! NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenFieldIsNumber() {
		let fields = DDLXSDParser().parse(numberXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as! DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLNumber, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as! NSInteger)
	}

	func test_Parse_ShouldExtractValues_WhenFieldIsDouble() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDLFieldNumber)
		let numberField = fields![0] as! DDLFieldNumber

		XCTAssertEqual(DDLField.DataType.DDLDouble, numberField.dataType)
		XCTAssertEqual(DDLField.Editor.Number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSDecimalNumber)
		XCTAssertEqualWithAccuracy(Float(16.05),
			(numberField.predefinedValue as! NSDecimalNumber).floatValue, accuracy: 0.001)
	}


	func test_Parse_ShouldExtractPredefinedValueValues_WhenFieldIsInteger() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as! NSInteger)
	}


	//MARK: currentValue

	func test_CurrentValue_ShouldTruncateDecimal_WhenOriginalNumberIsInteger() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValue = 1.1

		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(1), numberField.currentValue as! NSInteger)
	}


	//MARK: currentValueAsString

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsInteger() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValue = 99

		XCTAssertEqual("99", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsDecimal() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValue = 16.0599

		XCTAssertEqual("16.06", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsDecimalAndContentIsInteger() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValue = 16

		XCTAssertEqual("16.00", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsInteger() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValueAsString = "99"

		XCTAssertEqual("99", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(99), numberField.currentValue as! NSInteger)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsIntegerAndValueIsDecimal() {
		let fields = DDLXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValueAsString = "99.88"

		XCTAssertEqual("100", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(100), numberField.currentValue as! NSInteger)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsDecimal() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValueAsString = "99.98"

		// If this asset fails, check the simulator language settings
		// It must be in en_US locale
		XCTAssertEqual("99.98", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSDecimalNumber)
		XCTAssertEqualWithAccuracy(Float(99.98),
			(numberField.currentValue as! NSDecimalNumber).floatValue, accuracy: 0.001)
	}


	//MARK: currentValueAsLabel

	func test_CurrentValueAsLabel_ShouldBeLocalizedToSpanish_WhenNumberIsDecimal() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValue = 16.0599

		XCTAssertEqual("16,06", numberField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldBeLocalizedToEnglish_WhenNumberIsDecimal() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentLocale = NSLocale(localeIdentifier: "en_US")
		numberField.currentValue = 16.0599

		XCTAssertEqual("16.06", numberField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldBeTheRightValue_WhenSetTheLabelValue() {
		let fields = DDLXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDLFieldNumber

		numberField.currentValueAsLabel = "16,069"

		XCTAssertNotNil(numberField.currentValue)
		XCTAssertEqualWithAccuracy(Float(16.07),
			(numberField.currentValue! as! NSNumber).floatValue, accuracy: 0.001)
	}


	//MARK: validate

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

		let numberField = fields![0] as! DDLFieldNumber

		XCTAssertTrue(numberField.currentValue == nil)
		XCTAssertFalse(numberField.validate())
	}


	private let decimalXSD =
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
						"<entry name=\"predefinedValue\"> " +
							"<![CDATA[16.05]]> " +
						"</entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

	private let integerXSD =
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

	private let numberXSD =
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

}
