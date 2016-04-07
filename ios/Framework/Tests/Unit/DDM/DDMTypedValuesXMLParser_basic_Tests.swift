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


class DDMTypedValuesXMLParser_Basic_Tests: XCTestCase {

	private let defaultLocale = NSLocale(localeIdentifier: "en_US")


	func test_Parse_ShouldSetValues_WhenStructureHasThoseFields() {
		let structure = DDMStructure(xsd: textXSD, locale: defaultLocale, attributes: [:])

		let field = structure.fieldBy(name: "A_Text")
		XCTAssertNotNil(field)
		XCTAssertNil(field?.currentValue)

		let result = DDMTypedValuesXMLParser().parse(textValues, structure: structure)

		XCTAssertEqual(1, result)
		XCTAssertEqual("A Text", field?.currentValueAsString)
	}

	private let textXSD =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"string\" " +
				"name=\"A_Text\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"true\" " +
				"showLabel=\"true\" " +
				"type=\"checkbox\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Text]]></entry> " +
					"</meta-data> " +
		"</dynamic-element> </root>"

	private let textValues =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element " +
				"name=\"A_Text\" " +
				"type=\"text\"> " +
				"<dynamic-content language-id=\"en_US\"><![CDATA[A Text]]></dynamic-content> " +
			"</dynamic-element></root>"

}
