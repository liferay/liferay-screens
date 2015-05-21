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


class DDLFieldDate_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldDate)

		let dateField = fields![0] as! DDLFieldDate

		XCTAssertEqual(DDLField.DataType.DDLDate, dateField.dataType)
		XCTAssertEqual(DDLField.Editor.Date, dateField.editorType)
		XCTAssertEqual("A_Date", dateField.name)
		XCTAssertEqual("A Date", dateField.label)
		XCTAssertEqual("The tip", dateField.tip)
		XCTAssertFalse(dateField.readOnly)
		XCTAssertTrue(dateField.repeatable)
		XCTAssertFalse(dateField.required)
		XCTAssertTrue(dateField.showLabel)

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		XCTAssertTrue(dateField.predefinedValue != nil)
		XCTAssertTrue(dateField.predefinedValue is NSDate)
		XCTAssertEqual(
				"31/12/2001",
				dateFormatter.stringFromDate(dateField.predefinedValue as! NSDate))
		XCTAssertEqual(
				dateField.currentValue as! NSDate,
				dateField.predefinedValue as! NSDate)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		XCTAssertTrue(dateField.currentValue == nil)

		XCTAssertFalse(dateField.validate())
	}

	func test_currentValueAsString_ShouldReturnEpochTimeInMilliseconds() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"
		dateFormatter.timeZone = NSTimeZone(abbreviation: "GMT")

		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		// converted with http://www.epochconverter.com/
		XCTAssertEqual("1087603200000", dateField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldSupportOneDigitMonth_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValueAsString = "6/19/2004"

		XCTAssertEqual(
				"19/06/2004",
				dateFormatter.stringFromDate(dateField.currentValue as! NSDate))
	}

	func test_currentValueAsString_ShouldSupportFourDigitsYear_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValueAsString = "6/19/2004"

		XCTAssertEqual(
				"19/06/2004",
				dateFormatter.stringFromDate(dateField.currentValue as! NSDate))
	}


	//MARK: CurrentValueAsLabel

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenEnglishLocaleIsUsed() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentLocale = NSLocale(localeIdentifier: "en_US")
		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("June 19, 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenSpanishLocaleIsUsed() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("19 de junio de 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldBeTheValidDate_WhenSetTheLabelDate() {
		let fields = DDLXSDParser().parse(requiredDateFieldXSD, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		dateField.currentValueAsLabel = "19 de junio de 2004"

		let date = dateField.currentValue as! NSDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		XCTAssertEqual("19/06/2004", dateFormatter.stringFromDate(date))
	}

	private let requiredDateFieldXSD =
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
