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


class DDLField_Validation_JSON_Tests: XCTestCase {

	private let spanishLocale = NSLocale(localeIdentifier: "es_ES")

	func test_Validate_ShoulTriggerOnPostValidation_WhenValidationFails() {
		let fields = DDLJSONParser().parse(requiredBooleanFormDefinitionJSON, locale: spanishLocale)

		let booleanField = fields![0] as! DDLFieldBoolean

		let expectation = expectationWithDescription("OnPostValidation must be called")

		booleanField.onPostValidation = {
			XCTAssertFalse($0)
			expectation.fulfill()
		}

		XCTAssertFalse(booleanField.validate())
		waitForExpectationsWithTimeout(0, handler: nil)
	}

	func test_Validate_ShoulTriggerOnPostValidation_WhenValidationSucceeds() {
		let fields = DDLJSONParser().parse(requiredBooleanFormDefinitionJSON, locale: spanishLocale)

		let booleanField = fields![0] as! DDLFieldBoolean

		booleanField.currentValue = true

		let expectation = expectationWithDescription("OnPostValidation must be called")

		booleanField.onPostValidation = {
			XCTAssertTrue($0)
			expectation.fulfill()
		}

		XCTAssertTrue(booleanField.validate())
		waitForExpectationsWithTimeout(0, handler: nil)
	}

	func test_ValidateOnBooleanField_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDLJSONParser().parse(requiredBooleanFormDefinitionJSON, locale: spanishLocale)

		let booleanField = fields![0] as! DDLFieldBoolean

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
		let fields = DDLJSONParser().parse(requiredTextFormDefinitionJSON, locale: spanishLocale)

		let stringField = fields![0] as! DDLFieldString

		stringField.currentValue = value

		XCTAssertFalse(stringField.validate())
	}

	private let requiredBooleanFormDefinitionJSON =
		"{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Boolean\"}," +
		"\"dataType\": \"boolean\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Boolean\"," +
		"\"readOnly\": false," +
		"\"repeatable\": false," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"type\": \"checkbox\"}]}"

	private let requiredTextFormDefinitionJSON =
		"{\"availableLanguageIds\": [\"en_US\"]," +
		"\"defaultLanguageId\": \"en_US\"," +
		"\"fields\": [{" +
		"\"label\": {\"en_US\": \"A Text\"}," +
		"\"predefinedValue\": {\"en_US\": false}," +
		"\"dataType\": \"string\"," +
		"\"indexType\": \"keyword\"," +
		"\"localizable\": true," +
		"\"name\": \"A_Text\"," +
		"\"readOnly\": false," +
		"\"repeatable\": true," +
		"\"required\": true," +
		"\"showLabel\": true," +
		"\"type\": \"text\"}]}"

}
