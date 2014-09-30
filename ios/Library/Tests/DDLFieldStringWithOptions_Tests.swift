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


class DDLFieldStringWithOptions_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

	func test_Parse_ShouldExtractValues() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDLFieldStringWithOptions)

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual(DDLField.DataType.DDLString, stringField.dataType)
		XCTAssertEqual(DDLField.Editor.Select, stringField.editorType)
		XCTAssertEqual("A_Select", stringField.name)
		XCTAssertEqual("A Select", stringField.label)
		XCTAssertTrue(stringField.multiple)
		XCTAssertFalse(stringField.readOnly)
		XCTAssertTrue(stringField.repeatable)
		XCTAssertTrue(stringField.required)
		XCTAssertTrue(stringField.showLabel)
	}

	func test_Parse_ShouldExtractOptions() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertEqual(3, stringField.options.count)

		var option = stringField.options[0]

		XCTAssertEqual("option_1", option.name)
		XCTAssertEqual("value 1", option.value)
		XCTAssertEqual("Option 1", option.label)

		option = stringField.options[1]

		XCTAssertEqual("option_2", option.name)
		XCTAssertEqual("value 2", option.value)
		XCTAssertEqual("Option 2", option.label)

		option = stringField.options[2]

		XCTAssertEqual("option_3", option.name)
		XCTAssertEqual("value 3", option.value)
		XCTAssertEqual("Option 3", option.label)
	}

	func test_Parse_ShouldExtractPredefinedOptions() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertTrue(stringField.predefinedValue is [DDLFieldStringWithOptions.Option])
		let predefinedOptions = stringField.predefinedValue as [DDLFieldStringWithOptions.Option]

		//FIXME only support one predefined value
		XCTAssertEqual(1, predefinedOptions.count)

		var predefinedOption = predefinedOptions[0]

		XCTAssertEqual("option_1", predefinedOption.name)
		XCTAssertEqual("value 1", predefinedOption.value)
		XCTAssertEqual("Option 1", predefinedOption.label)
	}

	func test_CurrentValue_ShouldBeTheSameAsPredefinedValue_WhenTheParsingIsDone() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		XCTAssertTrue(stringField.predefinedValue is [DDLFieldStringWithOptions.Option])
		let predefinedOptions = stringField.predefinedValue as [DDLFieldStringWithOptions.Option]

		XCTAssertTrue(stringField.currentValue is [DDLFieldStringWithOptions.Option])
		let currentOptions = stringField.currentValue as [DDLFieldStringWithOptions.Option]

		XCTAssertEqual(currentOptions.count, predefinedOptions.count)

		for (index,option) in enumerate(currentOptions) {
			let predefinedOption = predefinedOptions[index]

			XCTAssertEqual(option.label, predefinedOption.label)
			XCTAssertEqual(option.name, predefinedOption.name)
			XCTAssertEqual(option.value, predefinedOption.value)
		}
	}

	func test_CurrentValue_ShouldBeChanged_AfterChangedToExistingOptionLabel() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = "Option 3"

		XCTAssertTrue(stringField.currentValue is [DDLFieldStringWithOptions.Option])
		let currentOptions = stringField.currentValue as [DDLFieldStringWithOptions.Option]

		XCTAssertEqual(1, currentOptions.count)

		XCTAssertEqual("Option 3", currentOptions.first!.label)
		XCTAssertEqual("option_3", currentOptions.first!.name)
		XCTAssertEqual("value 3", currentOptions.first!.value)
	}

	func test_CurrentValue_ShouldBeEmpty_AfterChangedToNonExistingOptionLabel() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = "this is not a valid option label"

		XCTAssertTrue(stringField.currentValue is [DDLFieldStringWithOptions.Option])
		XCTAssertTrue((stringField.currentValue as [DDLFieldStringWithOptions.Option]).isEmpty)
	}

	func test_CurrenStringValue_ShouldContainTheArrayOfValues() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldContainEmptyArray_WhenCurrentValueWasSetToEmptyString() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportOptionLabel_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportOptionValue_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = "value 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportOptionValue_WhenSettingAnArrayOfValues() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = "[\"value 3\"]"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportOptionValue_WhenSettingAnArrayOfUnquotedValues() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = "[value 3]"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportNil_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = nil

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	func test_CurrenStringValue_ShouldSupportNonExistingString_WhenSettingTheStringValue() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValueAsString = "this is neither a value nor a label"

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	func test_CurrenOptionLabel_ShouldContainTheLabelOfSelectedOption() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = "Option 3"

		XCTAssertEqual("Option 3", stringField.currentOptionLabel)
	}

	func test_CurrenOptionLabel_ShouldContainEmptyString_WhenNoOptionSelected() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertEqual("", stringField.currentOptionLabel)
	}


	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertFalse(stringField.validate())
	}

	func test_Validate_ShouldPass_WhenRequiredValueIsNotEmptyString() {
		let fields = DDLXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as DDLFieldStringWithOptions

		stringField.currentValue = "Option 3"

		XCTAssertTrue(stringField.validate())
	}


	private let selectWithPredefinedValuesXSD =
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
