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

class DDMFieldNumber_Tests: XCTestCase {

	fileprivate let spanishLocale = Locale(identifier: "es_ES")

	// MARK: parse

	func test_XSDParse_ShouldExtractValues_WhenFieldIsInteger() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmInteger, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	func test_XSDParse_ShouldExtractValues_WhenFieldIsNumber() {
		let fields = DDMXSDParser().parse(numberXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmNumber, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	func test_XSDParse_ShouldExtractValues_WhenFieldIsDouble() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmDouble, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSDecimalNumber)
		XCTAssertEqual(Float(16.05),
			(numberField.predefinedValue as! NSDecimalNumber).floatValue, accuracy: 0.001)
	}

	func test_XSDParse_ShouldExtractPredefinedValueValues_WhenFieldIsInteger() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	func test_JSONParse_ShouldExtractValues_WhenFieldIsInteger() {
		let fields = DDMJSONParser().parse(integerJSON, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmInteger, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	func test_JSONParse_ShouldExtractValues_WhenFieldIsNumber() {
		let fields = DDMJSONParser().parse(numberJSON, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmNumber, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	func test_JSONParse_ShouldExtractValues_WhenFieldIsDouble() {
		let fields = DDMJSONParser().parse(decimalJSON, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldNumber)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertEqual(DDMField.DataType.ddmDouble, numberField.dataType)
		XCTAssertEqual(DDMField.Editor.number, numberField.editorType)
		XCTAssertTrue(numberField.predefinedValue is NSDecimalNumber)
		XCTAssertEqual(Float(16.05),
			(numberField.predefinedValue as! NSDecimalNumber).floatValue, accuracy: 0.001)
	}

	func test_JSONParse_ShouldExtractPredefinedValueValues_WhenFieldIsInteger() {
		let fields = DDMJSONParser().parse(integerJSON, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertTrue(numberField.predefinedValue is NSInteger)
		XCTAssertEqual(NSInteger(16), numberField.predefinedValue as? NSInteger)
	}

	// MARK: currentValue

	func test_CurrentValue_ShouldTruncateDecimal_WhenOriginalNumberIsInteger() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValue = 1.1
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(1), numberField.currentValue as? NSInteger)
	}

	// MARK: currentValueAsString

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsInteger() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValue = 99

		XCTAssertEqual("99", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsDecimal() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValue = 16.0599

		XCTAssertEqual("16.06", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeValid_WhenNumberIsDecimalAndContentIsInteger() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValue = 16

		XCTAssertEqual("16.00", numberField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsInteger() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValueAsString = "99"

		XCTAssertEqual("99", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(99), numberField.currentValue as? NSInteger)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsIntegerAndValueIsDecimal() {
		let fields = DDMXSDParser().parse(integerXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValueAsString = "99.88"

		XCTAssertEqual("100", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSInteger)
		XCTAssertEqual(NSInteger(100), numberField.currentValue as? NSInteger)
	}

	func test_CurrentValueAsString_ShouldBeChanged_WhenNumberIsDecimal() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValueAsString = "99.98"

		// If this asset fails, check the simulator language settings
		// It must be in en_US locale
		XCTAssertEqual("99.98", numberField.currentValueAsString!)
		XCTAssertTrue(numberField.currentValue is NSDecimalNumber)
		XCTAssertEqual(Float(99.98),
			(numberField.currentValue as! NSDecimalNumber).floatValue, accuracy: 0.001)
	}

	// MARK: currentValueAsLabel

	func test_CurrentValueAsLabel_ShouldBeLocalizedToSpanish_WhenNumberIsDecimal() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValue = 16.0599

		XCTAssertEqual("16,06", numberField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldBeLocalizedToEnglish_WhenNumberIsDecimal() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentLocale = Locale(identifier: "en_US")
		numberField.currentValue = 16.0599

		XCTAssertEqual("16.06", numberField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldBeTheRightValue_WhenSetTheLabelValue() {
		let fields = DDMXSDParser().parse(decimalXSD, locale: spanishLocale)
		let numberField = fields![0] as! DDMFieldNumber

		numberField.currentValueAsLabel = "16,069"

		XCTAssertNotNil(numberField.currentValue)
		XCTAssertEqual(Float(16.07),
			(numberField.currentValue! as! NSNumber).floatValue, accuracy: 0.001)
	}

	// MARK: validate

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

		let fields = DDMXSDParser().parse(xsd, locale: spanishLocale)

		let numberField = fields![0] as! DDMFieldNumber

		XCTAssertTrue(numberField.currentValue == nil)
		XCTAssertFalse(numberField.validate())
	}

	fileprivate let decimalXSD =
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

	fileprivate let integerXSD =
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

	fileprivate let numberXSD =
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

	fileprivate let decimalJSON = "{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Number\"}," +
		"\"predefinedValue\": {\"en_US\": \"16.05\"}," +
		"\"dataType\": \"double\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Number\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"fieldNamespace\": \"ddm\"," +
		"\"type\": \"ddm-decimal\"}]}"

	fileprivate let integerJSON = "{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Number\"}," +
		"\"predefinedValue\": {\"en_US\": \"16\"}," +
		"\"dataType\": \"integer\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Number\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": false," +
		"\"showLabel\": true," +
		"\"fieldNamespace\": \"ddm\"," +
		"\"type\": \"ddm-integer\"}]}"

	fileprivate let numberJSON = "{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Number\"}," +
		"\"predefinedValue\": {\"en_US\": \"16\"}," +
		"\"dataType\": \"number\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Number\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": false," +
		"\"showLabel\": true," +
		"\"fieldNamespace\": \"ddm\"," +
		"\"type\": \"ddm-number\"}]}"

}
