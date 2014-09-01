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

class DDLParser_basic_Tests: XCTestCase {

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Parse_ShouldReturnNil_WhenMalformedXML() {
		let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

		parser.xml = "#root available-locales=\"en_US\" default-locale=\"en_US\"> "

		let elements = parser.parse()

		XCTAssertNil(elements)
	}

	func test_Parse_ShouldReturnEmpty_WhenEmptyString() {
		let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

		parser.xml = ""

		let elements = parser.parse()

		XCTAssertNil(elements)
	}

	func test_Parse_ShouldReturnEmpty_WhenEmptyXML() {
		let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

		parser.xml = "<root available-locales=\"en_US\" default-locale=\"en_US\"></root>"

		let elements = parser.parse()

		XCTAssertNil(elements)
	}


}
