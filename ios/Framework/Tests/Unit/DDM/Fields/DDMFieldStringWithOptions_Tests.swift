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

class DDMFieldStringWithOptions_Tests: XCTestCase {

	fileprivate let spanishLocale = Locale(identifier: "es_ES")

	// MARK: Parse

	func test_XSDParse_ShouldExtractValues() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDMFieldStringWithOptions)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertEqual(DDMField.DataType.ddmString, stringField.dataType)
		XCTAssertEqual(DDMField.Editor.select, stringField.editorType)
		XCTAssertEqual("A_Select", stringField.name)
		XCTAssertEqual("A Select", stringField.label)
		XCTAssertTrue(stringField.multiple)
		XCTAssertFalse(stringField.readOnly)
		XCTAssertTrue(stringField.repeatable)
		XCTAssertTrue(stringField.required)
		XCTAssertTrue(stringField.showLabel)
	}

	func test_XSDParse_ShouldExtractOptions() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

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

	func test_XSDParse_ShouldExtractPredefinedOptions() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertTrue(stringField.predefinedValue is [DDMFieldStringWithOptions.Option])
		let predefinedOptions = stringField.predefinedValue as! [DDMFieldStringWithOptions.Option]

		//FIXME only support one predefined value
		XCTAssertEqual(2, predefinedOptions.count)

		let firstPredefinedOption = predefinedOptions[0]
		let secondPredefinedOption = predefinedOptions[1]

		XCTAssertEqual("option_1", firstPredefinedOption.name)
		XCTAssertEqual("value 1", firstPredefinedOption.value)
		XCTAssertEqual("Option 1", firstPredefinedOption.label)

		XCTAssertEqual("option_2", secondPredefinedOption.name)
		XCTAssertEqual("value 2", secondPredefinedOption.value)
		XCTAssertEqual("Option 2", secondPredefinedOption.label)
	}

	func test_JSONParse_ShouldExtractValues() {
		let fields = DDMJSONParser().parse(selectWithPredefinedValuesJSON, locale: spanishLocale)

		XCTAssertTrue(fields != nil)
		XCTAssertEqual(1, fields!.count)
		XCTAssertTrue(fields![0] is DDMFieldStringWithOptions)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertEqual(DDMField.DataType.ddmString, stringField.dataType)
		XCTAssertEqual(DDMField.Editor.select, stringField.editorType)
		XCTAssertEqual("A_Select", stringField.name)
		XCTAssertEqual("A Select", stringField.label)
		XCTAssertTrue(stringField.multiple)
		XCTAssertFalse(stringField.readOnly)
		XCTAssertTrue(stringField.repeatable)
		XCTAssertTrue(stringField.required)
		XCTAssertTrue(stringField.showLabel)
	}

	func test_JSONParse_ShouldExtractOptions() {
		let fields = DDMJSONParser().parse(selectWithPredefinedValuesJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertEqual(3, stringField.options.count)

		var option = stringField.options[0]

		XCTAssertEqual("value 1", option.value)
		XCTAssertEqual("Option 1", option.label)

		option = stringField.options[1]

		XCTAssertEqual("value 2", option.value)
		XCTAssertEqual("Option 2", option.label)

		option = stringField.options[2]

		XCTAssertEqual("value 3", option.value)
		XCTAssertEqual("Option 3", option.label)
	}

	func test_JSONParse_ShouldExtractPredefinedOptions() {
		let fields = DDMJSONParser().parse(selectWithPredefinedValuesJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertTrue(stringField.predefinedValue is [DDMFieldStringWithOptions.Option])
		let predefinedOptions = stringField.predefinedValue as! [DDMFieldStringWithOptions.Option]

		XCTAssertEqual(2, predefinedOptions.count)

		XCTAssertEqual("value 1", predefinedOptions[0].value)
		XCTAssertEqual("Option 1", predefinedOptions[0].label)

		XCTAssertEqual("value 2", predefinedOptions[1].value)
		XCTAssertEqual("Option 2", predefinedOptions[1].label)
	}

	// MARK: CurrentValue

	func test_CurrentValue_ShouldBeTheSameAsPredefinedValue_WhenTheParsingIsDone() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertTrue(stringField.predefinedValue is [DDMFieldStringWithOptions.Option])
		let predefinedOptions = stringField.predefinedValue as! [DDMFieldStringWithOptions.Option]

		XCTAssertTrue(stringField.currentValue is [DDMFieldStringWithOptions.Option])
		let currentOptions = stringField.currentValue as! [DDMFieldStringWithOptions.Option]

		XCTAssertEqual(currentOptions.count, predefinedOptions.count)

		for (index, option) in currentOptions.enumerated() {
			let predefinedOption = predefinedOptions[index]

			XCTAssertEqual(option.label, predefinedOption.label)
			XCTAssertEqual(option.name, predefinedOption.name)
			XCTAssertEqual(option.value, predefinedOption.value)
		}
	}

	func test_CurrentValue_ShouldBeChanged_AfterChangedToExistingOptionLabel() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3" as AnyObject?

		XCTAssertTrue(stringField.currentValue is [DDMFieldStringWithOptions.Option])
		let currentOptions = stringField.currentValue as! [DDMFieldStringWithOptions.Option]

		XCTAssertEqual(1, currentOptions.count)

		XCTAssertEqual("Option 3", currentOptions.first!.label)
		XCTAssertEqual("option_3", currentOptions.first!.name)
		XCTAssertEqual("value 3", currentOptions.first!.value)
	}

	func test_CurrentValue_ShouldBeEmpty_AfterChangedToNonExistingOptionLabel() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "this is not a valid option label" as AnyObject?

		XCTAssertTrue(stringField.currentValue is [DDMFieldStringWithOptions.Option])
		XCTAssertTrue((stringField.currentValue as! [DDMFieldStringWithOptions.Option]).isEmpty)
	}

	// MARK: CurrentValueAsString

	func test_CurrentValueAsString_ShouldContainTheArrayOfValues() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3" as AnyObject?

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldContainEmptyArray_WhenCurrentValueWasSetToEmptyString() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportOptionLabel_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportOptionValue_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = "value 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportOptionValue_WhenSettingAnArrayOfValues() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = "[\"value 3\"]"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportOptionValue_WhenSettingAnArrayOfUnquotedValues() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = "[value 3]"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportNil_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = nil

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	func test_CurrentValueAsString_ShouldSupportNonExistingString_WhenSettingTheStringValue() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)
		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsString = "this is neither a value nor a label"

		XCTAssertEqual("[]", stringField.currentValueAsString!)
	}

	// MARK: CurrentValueAsLabel

	func test_CurrentValueAsLabel_ShouldContainTheLabelOfSelectedOption() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3" as AnyObject?

		XCTAssertEqual("Option 3", stringField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldContainEmptyString_WhenNoOptionSelected() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertEqual("", stringField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldStoreTheOption_WhenSetLabelValue() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValueAsLabel = "Option 3"

		XCTAssertEqual("[\"value 3\"]", stringField.currentValueAsString!)
	}

	// MARK: Validate

	func test_Validate_ShouldFail_WhenRequiredValueIsEmptyString() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = nil

		XCTAssertFalse(stringField.validate())
	}

	func test_Validate_ShouldPass_WhenRequiredValueIsNotEmptyString() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3" as AnyObject?

		XCTAssertTrue(stringField.validate())
	}

	func test_ShouldDetectMultiple_WhenSelectHasMultipleProperty() {
		let fields = DDMJSONParser().parse(selectWithPredefinedValuesJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		XCTAssertTrue(stringField.multiple)
	}

	func test_ShouldParseMultipleOptions_SeparatedByComma() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3,Option 1" as AnyObject?

		XCTAssertTrue(stringField.currentValue is [DDMFieldStringWithOptions.Option])
		let currentOptions = stringField.currentValue as! [DDMFieldStringWithOptions.Option]

		XCTAssertEqual(2, currentOptions.count)

		XCTAssertEqual("Option 3", currentOptions.first!.label)
		XCTAssertEqual("option_3", currentOptions.first!.name)
		XCTAssertEqual("value 3", currentOptions.first!.value)

		XCTAssertEqual("Option 1", currentOptions.last!.label)
		XCTAssertEqual("option_1", currentOptions.last!.name)
		XCTAssertEqual("value 1", currentOptions.last!.value)
	}

	func test_ShouldRenderMultipleOptions() {
		let fields = DDMXSDParser().parse(selectWithPredefinedValuesXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldStringWithOptions

		stringField.currentValue = "Option 3, Option 1" as AnyObject?

		XCTAssertEqual("[\"value 3\", \"value 1\"]", stringField.currentValueAsString)
	}

	fileprivate let selectWithPredefinedValuesXSD =
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

	fileprivate let selectWithPredefinedValuesJSON =
	"{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Select\"}," +
		"\"predefinedValue\": {\"en_US\": [\"value 1\", \"value 2\"]}," +
		"\"dataType\": \"string\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Select\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"multiple\": true," +
		"\"options\": [" +
			"{\"label\": {\"en_US\": \"Option 1\"}," +
				"\"value\": \"value 1\"}," +
			"{\"label\": {\"en_US\": \"Option 2\"}," +
				"\"value\": \"value 2\"}," +
			"{\"label\": {\"en_US\": \"Option 3\"}," +
				"\"value\": \"value 3\"}" +
		"]," +
		"\"type\": \"select\"}]}"

}
