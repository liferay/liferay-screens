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

class DDMFieldDate_v62_Tests: XCTestCase {

	fileprivate let spanishLocale = Locale(identifier: "es_ES")

	func test_Parse_ShouldExtractValues() {
		let xsd =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"date\" " +
				"fieldNamespace=\"ddm\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Date\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-date\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Date]]></entry> " +
						"<entry name=\"predefinedValue\"><![CDATA[12/31/2001]]></entry> " +
						"<entry name=\"tip\"><![CDATA[The tip]]></entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let fields = DDMXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDMFieldDate)

		let dateField = fields![0] as! DDMFieldDate

		XCTAssertEqual(DDMField.DataType.ddmDate, dateField.dataType)
		XCTAssertEqual(DDMField.Editor.date, dateField.editorType)
		XCTAssertEqual("A_Date", dateField.name)
		XCTAssertEqual("A Date", dateField.label)
		XCTAssertEqual("The tip", dateField.tip)
		XCTAssertFalse(dateField.readOnly)
		XCTAssertTrue(dateField.repeatable)
		XCTAssertFalse(dateField.required)
		XCTAssertTrue(dateField.showLabel)
		XCTAssertNotNil(dateField.predefinedValue)
		XCTAssertTrue(dateField.predefinedValue is NSDate)

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		let dateValue = dateField.predefinedValue as! Date
		XCTAssertEqual(
				"31/12/2001",
				dateFormatter.string(from: dateValue))
		XCTAssertEqual(
				dateField.currentValue as? Date,
				dateValue)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		XCTAssertTrue(dateField.currentValue == nil)

		XCTAssertFalse(dateField.validate())
	}

	func test_currentValueAsString_ShouldReturnEpochTimeInMilliseconds() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"
		dateFormatter.timeZone = TimeZone(abbreviation: "GMT")

		dateField.currentValue = dateFormatter.date(from: "19/06/2004")

		// converted with http://www.epochconverter.com/
		XCTAssertEqual("1087603200000", dateField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldSupportOneDigitMonth_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValueAsString = "6/19/2004"

		XCTAssertEqual(
				"19/06/2004",
				dateFormatter.string(from: dateField.currentValue as! Date))
	}

	func test_currentValueAsString_ShouldSupportFourDigitsYear_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValueAsString = "6/19/2004"

		XCTAssertEqual(
				"19/06/2004",
				dateFormatter.string(from: dateField.currentValue as! Date))
	}

	// MARK: CurrentValueAsLabel

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenEnglishLocaleIsUsed() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentLocale = Locale(identifier: "en_US")
		dateField.currentValue = dateFormatter.date(from: "19/06/2004")

		XCTAssertEqual("June 19, 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenSpanishLocaleIsUsed() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValue = dateFormatter.date(from: "19/06/2004")

		XCTAssertEqual("19 de junio de 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldBeTheValidDate_WhenSetTheLabelDate() {
		let fields = DDMXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDMFieldDate

		dateField.currentValueAsLabel = "19 de junio de 2004"

		let date = dateField.currentValue as! Date

		let dateFormatter = DateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		XCTAssertEqual("19/06/2004", dateFormatter.string(from: date))
	}

	fileprivate let requiredDateFieldXSD =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"date\" " +
				"fieldNamespace=\"ddm\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Date\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"true\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-date\" " +
				"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Date]]></entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

}
