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


class DDLFieldDate_v70_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

	func test_Parse_ShouldExtractValues() {
		let json = "{\"availableLanguageIds\": [\"en_US\"]," +
			"\"defaultLanguageId\": \"en_US\"," +
			"\"fields\": [{" +
			"\"label\": {\"en_US\": \"A Date\"}," +
			"\"predefinedValue\": {\"en_US\": \"2001-12-31\"}," +
			"\"tip\": {\"en_US\": \"The tip\"}," +
			"\"dataType\": \"date\"," +
			"\"fieldNamespace\": \"ddm\"," +
			"\"indexType\": \"keyword\"," +
			"\"name\": \"A_Date\"," +
			"\"readOnly\": false," +
			"\"repeatable\": true," +
			"\"required\": false," +
			"\"showLabel\": true," +
			"\"width\": \"small\"," +
			"\"type\": \"ddm-date\"}]}"

		let fields = DDLJSONParser().parse(json, locale: spanishLocale)

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
		XCTAssertNotNil(dateField.predefinedValue)
		XCTAssertTrue(dateField.predefinedValue is NSDate)

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		let dateValue = dateField.predefinedValue as! NSDate
		XCTAssertEqual(
				"31/12/2001",
				dateFormatter.stringFromDate(dateValue))
		XCTAssertEqual(
				dateField.currentValue as? NSDate,
				dateValue)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		XCTAssertTrue(dateField.currentValue == nil)

		XCTAssertFalse(dateField.validate())
	}

	func test_currentValueAsString_ShouldReturnServerSideFormattedDate() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"
		dateFormatter.timeZone = NSTimeZone(abbreviation: "GMT")

		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("2004-06-19", dateField.currentValueAsString!)
	}

	func test_currentValueAsString_ShouldSupportOneDigitMonth_WhenSettingTheStringValue() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValueAsString = "2004-6-19"

		XCTAssertEqual(
				"19/06/2004",
				dateFormatter.stringFromDate(dateField.currentValue as! NSDate))
	}

	//MARK: CurrentValueAsLabel

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenEnglishLocaleIsUsed() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentLocale = NSLocale(localeIdentifier: "en_US")
		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("June 19, 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldReturnLocalizedValue_WhenSpanishLocaleIsUsed() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateField.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("19 de junio de 2004", dateField.currentValueAsLabel!)
	}

	func test_currentValueAsLabel_ShouldBeTheValidDate_WhenSetTheLabelDate() {
		let fields = DDLJSONParser().parse(requiredDateFieldJSON, locale: spanishLocale)
		let dateField = fields![0] as! DDLFieldDate

		dateField.currentValueAsLabel = "19 de junio de 2004"

		let date = dateField.currentValue as! NSDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		XCTAssertEqual("19/06/2004", dateFormatter.stringFromDate(date))
	}

	let requiredDateFieldJSON = "{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Date\"}," +
		"\"dataType\": \"date\"," +
		"\"fieldNamespace\": \"ddm\"," +
		"\"indexType\": \"keyword\"," +
		"\"name\": \"A_Date\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"width\": \"small\"," +
		"\"type\": \"ddm-date\"}]}"

}
