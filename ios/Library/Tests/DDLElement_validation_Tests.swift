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

class DDLElement_validation_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Validate_ShoulTriggerValidatedClosure_WhenValidationFails() {
		parser.xml = requiredBoolean

		let elements = parser.parse()

		let booleanElement = elements![0] as DDLElementBoolean

		XCTAssertFalse(booleanElement.validate())

		var closureCalled = false

		booleanElement.validatedClosure = {isValid in
			XCTAssertFalse(isValid)
			closureCalled = true
		}

		XCTAssertFalse(closureCalled)
	}

	func test_Validate_ShoulTriggerValidatedClosure_WhenValidationSucceeds() {
		parser.xml = requiredBoolean

		let elements = parser.parse()

		let booleanElement = elements![0] as DDLElementBoolean

		booleanElement.currentValue = true

		XCTAssertTrue(booleanElement.validate())

		var closureCalled = false

		booleanElement.validatedClosure = {isValid in
			XCTAssertTrue(isValid)
			closureCalled = true
		}

		XCTAssertFalse(closureCalled)
	}

	func test_ValidateOnBooleanElement_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml = requiredBoolean

		let elements = parser.parse()

		let booleanElement = elements![0] as DDLElementBoolean

		XCTAssertTrue(booleanElement.currentValue == nil)

		XCTAssertFalse(booleanElement.validate())
	}

	func test_ValidateOnStringElement_ShouldFail_WhenRequiredValueIsEmptyString() {
		parser.xml = requiredText

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		stringElement.currentValue = ""

		XCTAssertFalse(stringElement.validate())
	}

	func test_ValidateOnStringElement_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		parser.xml = requiredText

		let elements = parser.parse()

		let stringElement = elements![0] as DDLElementString

		stringElement.currentValue = "  "

		XCTAssertFalse(stringElement.validate())
	}


	private let requiredBoolean =
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

	private let requiredText =
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
