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


class DDLElementDate_Tests: XCTestCase {

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

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementDate)

		let dateElement = elements![0] as DDLElementDate

		XCTAssertEqual(DDLElementDataType.DDLDate, dateElement.dataType)
		XCTAssertEqual(DDLElementEditor.Date, dateElement.editorType)
		XCTAssertEqual("A_Date", dateElement.name)
		XCTAssertEqual("A Date", dateElement.label)
		XCTAssertEqual("The tip", dateElement.tip)
		XCTAssertFalse(dateElement.readOnly)
		XCTAssertTrue(dateElement.repeatable)
		XCTAssertFalse(dateElement.required)
		XCTAssertTrue(dateElement.showLabel)

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		XCTAssertTrue(dateElement.predefinedValue != nil)
		XCTAssertTrue(dateElement.predefinedValue is NSDate)
		XCTAssertEqual("31/12/2001", dateFormatter.stringFromDate(dateElement.predefinedValue as NSDate))
		XCTAssertEqual(dateElement.currentValue as NSDate, dateElement.predefinedValue as NSDate)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml = requiredDateElement
		let elements = parser.parse()
		let dateElement = elements![0] as DDLElementDate

		XCTAssertTrue(dateElement.currentValue == nil)

		XCTAssertFalse(dateElement.validate())
	}

	func test_CurrentStringValue_ShouldReturnEpochTimeInMilliseconds() {
		parser.xml = requiredDateElement
		let elements = parser.parse()
		let dateElement = elements![0] as DDLElementDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateElement.currentValue = dateFormatter.dateFromString("19/06/2004")

		// converted with http://www.epochconverter.com/
		XCTAssertEqual("1087596000000", dateElement.currentStringValue!)
	}

	func test_CurrentStringValue_ShouldSupportOneDigitMonth_WhenSettingTheStringValue() {
		parser.xml = requiredDateElement
		let elements = parser.parse()
		let dateElement = elements![0] as DDLElementDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateElement.currentStringValue = "6/19/2004"

		XCTAssertEqual("19/06/2004", dateFormatter.stringFromDate(dateElement.currentValue as NSDate))
	}

	func test_CurrentStringValue_ShouldSupportFourDigitsYear_WhenSettingTheStringValue() {
		parser.xml = requiredDateElement
		let elements = parser.parse()
		let dateElement = elements![0] as DDLElementDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateElement.currentStringValue = "6/19/04"

		XCTAssertEqual("19/06/2004", dateFormatter.stringFromDate(dateElement.currentValue as NSDate))
	}


	func test_CurrentDateLabel_ShouldReturnClientSideFormat() {
		parser.xml = requiredDateElement
		let elements = parser.parse()
		let dateElement = elements![0] as DDLElementDate

		let dateFormatter = NSDateFormatter()
		dateFormatter.dateFormat = "dd/MM/yyyy"

		dateElement.currentValue = dateFormatter.dateFromString("19/06/2004")

		XCTAssertEqual("Jun 19, 2004", dateElement.currentDateLabel!)
	}



	private let requiredDateElement =
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
