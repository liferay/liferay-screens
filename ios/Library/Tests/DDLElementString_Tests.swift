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

class DDLElementString_Tests: XCTestCase {

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
				"<dynamic-element dataType=\"string\" " +
						"indexType=\"keyword\" " +
						"name=\"A_Text\" " +
						"readOnly=\"false\" " +
						"repeatable=\"true\" " +
						"required=\"false\" " +
						"showLabel=\"true\" " +
						"type=\"text\" " +
						"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Text]]>" +
						"</entry> " +
						"<entry name=\"predefinedValue\">" +
							"<![CDATA[predefined text]]>" +
						"</entry> " +
						"<entry name=\"tip\">" +
							"<![CDATA[The tip]]>" +
						"</entry> " +
					"</meta-data> " +
				"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementString)

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual(DDLElementDataType.DDLString, stringElement.dataType)
		XCTAssertEqual(DDLElementType.Text, stringElement.type)
		XCTAssertEqual("A_Text", stringElement.name)
		XCTAssertEqual("A Text", stringElement.label)
		XCTAssertEqual("The tip", stringElement.tip)
		XCTAssertTrue(stringElement.predefinedValue is String)
		XCTAssertEqual("predefined text", stringElement.predefinedValue as String)
		XCTAssertFalse(stringElement.readOnly)
		XCTAssertTrue(stringElement.repeatable)
		XCTAssertFalse(stringElement.required)
		XCTAssertTrue(stringElement.showLabel)
	}

	func test_Parse_ShouldExtractValues_WhenParsingSelectStringFields() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"string\" " +
					"indexType=\"keyword\" " +
					"multiple=\"true\" " +
					"name=\"A_Select\" " +
					"readOnly=\"false\" " +
					"repeatable=\"true\" " +
					"required=\"false\" " +
					"showLabel=\"true\" " +
					"type=\"text\" " +
					"width=\"small\"> " +
			"<meta-data locale=\"en_US\"> " +
				"<entry name=\"label\">" +
					"<![CDATA[A Select]]>" +
				"</entry> " +
				"<entry name=\"predefinedValue\">" +
					"<![CDATA[predefined select]]>" +
				"</entry> " +
				"<entry name=\"tip\">" +
					"<![CDATA[The tip]]>" +
				"</entry> " +
			"</meta-data> " +
		"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementString)

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual(DDLElementDataType.DDLString, stringElement.dataType)
		XCTAssertEqual(DDLElementType.Text, stringElement.type)
		XCTAssertEqual("A_Select", stringElement.name)
		XCTAssertEqual("A Select", stringElement.label)
		XCTAssertEqual("The tip", stringElement.tip)
		XCTAssertTrue(stringElement.predefinedValue is String)
		XCTAssertEqual("predefined select", stringElement.predefinedValue as String)
		XCTAssertTrue(stringElement.multiple)
		XCTAssertFalse(stringElement.readOnly)
		XCTAssertTrue(stringElement.repeatable)
		XCTAssertFalse(stringElement.required)
		XCTAssertTrue(stringElement.showLabel)
	}

	func test_Parse_ShouldExtractOptions_WhenParsingSelectStringFields() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"string\" " +
						"indexType=\"keyword\" " +
						"multiple=\"true\" " +
						"name=\"A_Select\" " +
						"type=\"text\" " +
						"width=\"small\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Select]]>" +
						"</entry> " +
					"</meta-data> " +
					"<dynamic-element name=\"option_1\" type=\"option\" value=\"value 1\"> " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"label\">" +
								"<![CDATA[Option 1]]>" +
							"</entry> " +
						"</meta-data> " +
					"</dynamic-element> " +
					"<dynamic-element name=\"option_2\" type=\"option\" value=\"value 2\"> " +
						"<meta-data locale=\"en_US\"> " +
							"<entry name=\"label\">" +
								"<![CDATA[Option 2]]>" +
							"</entry> " +
						"</meta-data>" +
					"</dynamic-element> " +
				"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements != nil)
		XCTAssertEqual(1, elements!.count)
		XCTAssertTrue(elements![0] is DDLElementString)

		let stringElement = elements![0] as DDLElementString

		XCTAssertEqual(DDLElementDataType.DDLString, stringElement.dataType)
		XCTAssertEqual(DDLElementType.Text, stringElement.type)

		XCTAssertTrue(stringElement.options != nil)
		XCTAssertEqual(2, stringElement.options.count)

		var option = stringElement.options[0]

		XCTAssertEqual("option_1", option.name)
		XCTAssertEqual("value 1", option.value)
		XCTAssertEqual("Option 1", option.label)

		option = stringElement.options[1]

		XCTAssertEqual("option_2", option.name)
		XCTAssertEqual("value 2", option.value)
		XCTAssertEqual("Option 2", option.label)
	}

	func test_Parse_ShouldExtractPredefinedOptions_WhenParsingSelectStringFieldsWithPredefinedValue() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertTrue(stringElement.predefinedValue is [DDLStringOption])
		let predefinedOptions = stringElement.predefinedValue as [DDLStringOption]

		XCTAssertEqual(2, predefinedOptions.count)

		var predefinedOption = predefinedOptions[0]

		XCTAssertEqual("option_1", predefinedOption.name)
		XCTAssertEqual("value 1", predefinedOption.value)
		XCTAssertEqual("Option 1", predefinedOption.label)

		predefinedOption = predefinedOptions[1]

		XCTAssertEqual("option_2", predefinedOption.name)
		XCTAssertEqual("value 2", predefinedOption.value)
		XCTAssertEqual("Option 2", predefinedOption.label)
	}

	func test_CurrentValue_ShouldBeTheSameAsPredefinedValue_AfterParsingSelectString() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

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

	func test_CurrenStringValue_ShouldContainAllSelectedOptionsSeparatedByComma() {
		parser.xml = selectWithPredefinedValues

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		XCTAssertTrue(stringElement.predefinedValue is [DDLStringOption])
		let predefinedOptions = stringElement.predefinedValue as [DDLStringOption]

		XCTAssertTrue(stringElement.currentStringValue != nil)

		stringElement.currentStringValue!.hasPrefix(predefinedOptions[0].label + ", ")
		stringElement.currentStringValue!.hasSuffix(", " + predefinedOptions[0].label)
	}


	private let selectWithPredefinedValues =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"string\" " +
					"indexType=\"keyword\" " +
					"multiple=\"true\" " +
					"name=\"A_Select\" " +
					"type=\"text\" " +
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
			"</dynamic-element> </root>"


}
