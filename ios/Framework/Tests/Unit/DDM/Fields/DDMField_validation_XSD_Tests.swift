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

class DDMField_Validation_XSD_Tests: XCTestCase {

	fileprivate let spanishLocale = Locale(identifier: "es_ES")

	func test_Validate_ShoulTriggerOnPostValidation_WhenValidationFails() {
		let fields = DDMXSDParser().parse(requiredBooleanFormDefinitionXSD, locale: spanishLocale)

		let booleanField = fields![0] as! DDMFieldBoolean

		let expectation = self.expectation(description: "OnPostValidation must be called")

		booleanField.onPostValidation = {
			XCTAssertFalse($0)
			expectation.fulfill()
		}

		XCTAssertFalse(booleanField.validate())
		waitForExpectations(timeout: 0, handler: nil)
	}

	func test_Validate_ShoulTriggerOnPostValidation_WhenValidationSucceeds() {
		let fields = DDMXSDParser().parse(requiredBooleanFormDefinitionXSD, locale: spanishLocale)

		let booleanField = fields![0] as! DDMFieldBoolean

		booleanField.currentValue = true

		let expectation = self.expectation(description: "OnPostValidation must be called")

		booleanField.onPostValidation = {
			XCTAssertTrue($0)
			expectation.fulfill()
		}

		XCTAssertTrue(booleanField.validate())
		waitForExpectations(timeout: 0, handler: nil)
	}

	func test_ValidateOnBooleanField_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDMXSDParser().parse(requiredBooleanFormDefinitionXSD, locale: spanishLocale)

		let booleanField = fields![0] as! DDMFieldBoolean

		XCTAssertTrue(booleanField.currentValue == nil)

		XCTAssertFalse(booleanField.validate())
	}

	func test_ValidateOnStringField_ShouldFail_WhenRequiredValueIsEmptyString() {
		validateOnStringField_ShouldFail_WhenRequiredValueIs("")
	}

	func test_ValidateOnStringField_ShouldFail_WhenRequiredValueIsEmptyStringWithSpaces() {
		validateOnStringField_ShouldFail_WhenRequiredValueIs("  ")
	}

	fileprivate func validateOnStringField_ShouldFail_WhenRequiredValueIs(_ value: String) {
		let fields = DDMXSDParser().parse(requiredTextFormDefinitionXSD, locale: spanishLocale)

		let stringField = fields![0] as! DDMFieldString

		stringField.currentValue = value

		XCTAssertFalse(stringField.validate())
	}

	fileprivate let requiredBooleanFormDefinitionXSD =
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

	fileprivate let requiredTextFormDefinitionXSD =
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
