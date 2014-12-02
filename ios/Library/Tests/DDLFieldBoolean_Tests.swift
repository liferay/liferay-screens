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

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")


	//MARK: ConvertFromString

	func test_ConvertFromString_ShouldReturnNil_WhenNilStringIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		XCTAssertNil(boolField.convert(fromString: nil))
	}

	func test_ConvertFromString_ShouldReturnBool_WhenTrueStringIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		let convertedValue:AnyObject? = boolField.convert(fromString: "true")

		XCTAssertNotNil(convertedValue)
		XCTAssertTrue(convertedValue is Bool)
		XCTAssertTrue(convertedValue as Bool)
	}

	func test_ConvertFromString_ShouldReturnBool_WhenFalseStringIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		let convertedValue:AnyObject? = boolField.convert(fromString: "false")

		XCTAssertNotNil(convertedValue)
		XCTAssertTrue(convertedValue is Bool)
		XCTAssertFalse(convertedValue as Bool)
	}


	//MARK: ConvertFromCurrentValue

	func test_ConvertFromCurrentValue_ShouldReturnNil_WhenNilIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		XCTAssertNil(boolField.convert(fromCurrentValue: nil))
	}

	func test_ConvertFromCurrentValue_ShouldReturnTrueString_WhenTrueIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		let convertedValue = boolField.convert(fromCurrentValue: true)

		XCTAssertNotNil(convertedValue)
		XCTAssertEqual("true", convertedValue!)
	}

	func test_ConvertFromCurrentValue_ShouldReturnFalseString_WhenFalseIsSupplied() {
		let boolField = DDLFieldBoolean(attributes: [:])

		let convertedValue = boolField.convert(fromCurrentValue: false)

		XCTAssertNotNil(convertedValue)
		XCTAssertEqual("false", convertedValue!)
	}


	//MARK: Parse

	func test_Parse_ShouldExtractValues() {
		let xsd =
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
						"<entry name=\"label\"><![CDATA[A Boolean]]></entry> " +
						"<entry name=\"predefinedValue\"><![CDATA[true]]></entry> " +
						"<entry name=\"tip\"><![CDATA[The tip]]></entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldBoolean)

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertEqual(DDLField.DataType.DDLBoolean, booleanField.dataType)
		XCTAssertEqual(DDLField.Editor.Checkbox, booleanField.editorType)
		XCTAssertEqual("A_Boolean", booleanField.name)
		XCTAssertEqual("A Boolean", booleanField.label)
		XCTAssertEqual("The tip", booleanField.tip)
		XCTAssertNotNil(booleanField.predefinedValue)
		XCTAssertTrue(booleanField.predefinedValue is Bool)
		XCTAssertTrue(booleanField.predefinedValue as Bool)
		XCTAssertFalse(booleanField.readOnly)
		XCTAssertTrue(booleanField.repeatable)
		XCTAssertFalse(booleanField.required)
		XCTAssertTrue(booleanField.showLabel)
	}


	//MARK: Validate

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let xsd =
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

		let fields = DDLXSDParser().parse(xsd, locale: spanishLocale)

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertTrue(booleanField.currentValue == nil)

		XCTAssertFalse(booleanField.validate())
	}

}
