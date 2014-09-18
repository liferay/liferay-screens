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


class DDLField_validation_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Validate_ShoulTriggerValidatedClosure_WhenValidationFails() {
		parser.xml = requiredBooleanFormDefinition

		let fields = parser.parse()

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertFalse(booleanField.validate())

		var closureCalled = false

		booleanField.validatedClosure = {isValid in
			XCTAssertFalse(isValid)
			closureCalled = true
		}

		XCTAssertFalse(closureCalled)
	}

	func test_Validate_ShoulTriggerValidatedClosure_WhenValidationSucceeds() {
		parser.xml = requiredBooleanFormDefinition

		let fields = parser.parse()

		let booleanField = fields![0] as DDLFieldBoolean

		booleanField.currentValue = true

		XCTAssertTrue(booleanField.validate())

		var closureCalled = false

		booleanField.validatedClosure = {isValid in
			XCTAssertTrue(isValid)
			closureCalled = true
		}

		XCTAssertFalse(closureCalled)
	}

	func test_ValidateOnbooleanField_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml = requiredBooleanFormDefinition

		let fields = parser.parse()

		let booleanField = fields![0] as DDLFieldBoolean

		XCTAssertTrue(booleanField.currentValue == nil)

		XCTAssertFalse(booleanField.validate())
	}

	func test_ValidateOnStringField_ShouldFail_WhenRequiredValueIsEmptyString() {
		validateOnStringField_ShouldFail_WhenRequiredValueIs("");
	}

	func test_ValidateOnStringField_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		validateOnStringField_ShouldFail_WhenRequiredValueIs("  ");
	}

	private func validateOnStringField_ShouldFail_WhenRequiredValueIs(value: String) {
		parser.xml = requiredTextFormDefinition

		let fields = parser.parse()

		let stringField = fields![0] as DDLFieldString

		stringField.currentValue = value

		XCTAssertFalse(stringField.validate())
	}

	private let requiredBooleanFormDefinition =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"boolean\" " +
					"name=\"A_Boolean\" " +
					"readOnly=\"false\" " +
					"repeatable=\"true\" " +
					"required=\"true\" " +
					"showLabel=\"true\" " +
					"type=\"checkbox\"> " +
				"<meta-data locale=\"en_US\"> " +
					"<entry name=\"label\">" +
						"<![CDATA[A Boolean]]>" +
					"</entry> " +
				"</meta-data> " +
			"</dynamic-element> </root>"

	private let requiredTextFormDefinition =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
				"<dynamic-element dataType=\"string\" " +
						"name=\"A_Text\" " +
						"readOnly=\"false\" " +
						"repeatable=\"true\" " +
						"required=\"true\" " +
						"showLabel=\"true\" " +
						"type=\"checkbox\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\">" +
							"<![CDATA[A Text]]>" +
						"</entry> " +
					"</meta-data> " +
				"</dynamic-element> </root>"

}
