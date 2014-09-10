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

class DDLElementStringWithOptions_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Parse_ShouldExtractValues() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementStringWithOptions)

		let stringElement = elements![0] as DDLElementStringWithOptions

		XCTAssertEqual(DDLElementDataType.DDLString, stringElement.dataType)
		XCTAssertEqual(DDLElementEditor.Select, stringElement.editorType)
		XCTAssertEqual("A_Select", stringElement.name)
		XCTAssertEqual("A Select", stringElement.label)
		XCTAssertTrue(stringElement.multiple)
		XCTAssertFalse(stringElement.readOnly)
		XCTAssertTrue(stringElement.repeatable)
		XCTAssertTrue(stringElement.required)
		XCTAssertTrue(stringElement.showLabel)
	}

	func test_Parse_ShouldExtractOptions() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		XCTAssertEqual(3, stringElement.options.count)

		var option = stringElement.options[0]

		XCTAssertEqual("option_1", option.name)
		XCTAssertEqual("value 1", option.value)
		XCTAssertEqual("Option 1", option.label)

		option = stringElement.options[1]

		XCTAssertEqual("option_2", option.name)
		XCTAssertEqual("value 2", option.value)
		XCTAssertEqual("Option 2", option.label)

		option = stringElement.options[2]

		XCTAssertEqual("option_3", option.name)
		XCTAssertEqual("value 3", option.value)
		XCTAssertEqual("Option 3", option.label)
	}

	func test_Parse_ShouldExtractPredefinedOptions() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		XCTAssertTrue(stringElement.predefinedValue is [DDLStringOption])
		let predefinedOptions = stringElement.predefinedValue as [DDLStringOption]

		//FIXME only support one predefined value
		XCTAssertEqual(1, predefinedOptions.count)

		var predefinedOption = predefinedOptions[0]

		XCTAssertEqual("option_1", predefinedOption.name)
		XCTAssertEqual("value 1", predefinedOption.value)
		XCTAssertEqual("Option 1", predefinedOption.label)
	}

	func test_CurrentValue_ShouldBeTheSameAsPredefinedValue_WhenTheParsingIsDone() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		XCTAssertTrue(stringElement.predefinedValue is [DDLStringOption])
		let predefinedOptions = stringElement.predefinedValue as [DDLStringOption]

		XCTAssertTrue(stringElement.currentValue is [DDLStringOption])
		let currentOptions = stringElement.currentValue as [DDLStringOption]

		XCTAssertEqual(currentOptions.count, predefinedOptions.count)

		for (index,option) in enumerate(currentOptions) {
			let predefinedOption = predefinedOptions[index]

			XCTAssertEqual(option.label, predefinedOption.label)
			XCTAssertEqual(option.name, predefinedOption.name)
			XCTAssertEqual(option.value, predefinedOption.value)
		}
	}

	func test_CurrentValue_ShouldBeChanged_AfterChangedToExistingOptionLabel() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = "Option 3"

		XCTAssertTrue(stringElement.currentValue is [DDLStringOption])
		let currentOptions = stringElement.currentValue as [DDLStringOption]

		XCTAssertEqual(1, currentOptions.count)

		XCTAssertEqual("Option 3", currentOptions.first!.label)
		XCTAssertEqual("option_3", currentOptions.first!.name)
		XCTAssertEqual("value 3", currentOptions.first!.value)
	}

	func test_CurrentValue_ShouldBeNil_AfterChangedToNonExistingOptionLabel() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = "this is not a valid option label"

		XCTAssertTrue(stringElement.currentValue == nil)
	}

	func test_CurrenStringValue_ShouldContainTheArrayOfValues() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringElement.currentStringValue!)
	}

	func test_CurrenStringValue_ShouldContainEmptyArray_WhenCurrentValueWasSetToEmptyString() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = nil

		XCTAssertEqual("[]", stringElement.currentStringValue!)
	}

	func test_CurrenStringValue_ShouldSupportOptionLabel_WhenSettingTheStringValue() {
		parser.xml = selectWithPredefinedValues
		let elements = parser.parse()
		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentStringValue = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringElement.currentStringValue!)
	}

	func test_CurrenStringValue_ShouldSupportOptionValue_WhenSettingTheStringValue() {
		parser.xml = selectWithPredefinedValues
		let elements = parser.parse()
		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentStringValue = "value 3"

		XCTAssertEqual("[\"value 3\"]", stringElement.currentStringValue!)
	}

	func test_CurrenStringValue_ShouldSupportNil_WhenSettingTheStringValue() {
		parser.xml = selectWithPredefinedValues
		let elements = parser.parse()
		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentStringValue = nil

		XCTAssertEqual("[]", stringElement.currentStringValue!)
	}

	func test_CurrenStringValue_ShouldSupportNonExistingString_WhenSettingTheStringValue() {
		parser.xml = selectWithPredefinedValues
		let elements = parser.parse()
		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentStringValue = "this is neither a value nor a label"

		XCTAssertEqual("[]", stringElement.currentStringValue!)
	}

	func test_CurrenOptionLabel_ShouldContainTheLabelOfSelectedOption() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = "Option 3"

		XCTAssertEqual("Option 3", stringElement.currentOptionLabel)
	}

	func test_CurrenOptionLabel_ShouldContainEmptyString_WhenNoOptionSelected() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = nil

		XCTAssertEqual("", stringElement.currentOptionLabel)
	}


	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = nil

		XCTAssertFalse(stringElement.validate())
	}

	func test_Validate_ShouldPass_WhenRequiredValueIsNotEmptyString() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementStringWithOptions

		stringElement.currentValue = "Option 3"

		XCTAssertTrue(stringElement.validate())
	}


	private let selectWithPredefinedValues =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"string\" " +
					"indexType=\"keyword\" " +
					"multiple=\"true\" " +
					"name=\"A_Select\" " +
					"repeatable=\"true\" " +
					"required=\"true\" " +
					"showLabel=\"true\" " +
					"type=\"select\" " +
					"width=\"small\"> " +
				"<meta-data locale=\"en_US\"> " +
					"<entry name=\"label\"><![CDATA[A Select]]></entry> " +
					"<entry name=\"predefinedValue\">" +
						"<![CDATA[[\"value 1\",\"value 2\"]]]>" +
					"</entry>" +
				"</meta-data> " +
				"<dynamic-element name=\"option_1\" type=\"option\" value=\"value 1\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[Option 1]]></entry> " +
					"</meta-data> " +
				"</dynamic-element> " +
				"<dynamic-element name=\"option_2\" type=\"option\" value=\"value 2\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[Option 2]]></entry> " +
					"</meta-data>" +
				"</dynamic-element> " +
				"<dynamic-element name=\"option_3\" type=\"option\" value=\"value 3\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[Option 3]]></entry> " +
					"</meta-data>" +
				"</dynamic-element> " +
			"</dynamic-element> </root>"


}
