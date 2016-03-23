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


class DDLRecord_FieldValues_Tests: XCTestCase {

	override func setUp() {
		super.setUp()
	}

	func test_ModelValues_ShouldBe_Read() {
		let values = ["field1":"value1", "field2":"value2"]

		let record = DDLRecord(dataAndAttributes: ["modelValues":values])

		XCTAssertEqual(2, record.fields.count)

		XCTAssertEqual("field1", record.fields[0].name)
		XCTAssertEqual("value1", record.fields[0].currentValue as? String)

		XCTAssertEqual("field2", record.fields[1].name)
		XCTAssertEqual("value2", record.fields[1].currentValue as? String)
	}

	func test_ModelValues_ShouldBe_Updated() {
		let values = ["field1":"value1", "field2":"value2"]

		let record = DDLRecord(dataAndAttributes: ["modelValues":values])

		record.updateCurrentValues(values: [
			"field1":"new_value1",  // update this field
			"new_field3":"value3"])	// ignore this field

		XCTAssertEqual(2, record.fields.count)

		// updated
		XCTAssertEqual("field1", record.fields[0].name)
		XCTAssertEqual("new_value1", record.fields[0].currentValue as? String)

		// not updated
		XCTAssertEqual("field2", record.fields[1].name)
		XCTAssertEqual("value2", record.fields[1].currentValue as? String)
	}

	func test_ModelAttributes_ShouldBe_Read() {
		let attributes = ["attr1":"attrvalue1"]

		let record = DDLRecord(dataAndAttributes: ["modelAttributes":attributes])

		XCTAssertEqual(1, record.attributes.count)
		XCTAssertEqual("attrvalue1", record.attributes["attr1"] as? String)
	}

	func test_RecordId_ShouldBe_Read() {
		let attributes = ["recordId": 123]

		let record = DDLRecord(dataAndAttributes: ["modelAttributes":attributes])

		XCTAssertEqual(Int64(123), record.recordId!)
	}

	func test_fieldByName_Should_FindTheField() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"]])

		let field = record.fieldBy(name: "field1")

		XCTAssertNotNil(field)
		XCTAssertEqual("field1", field!.name)
		XCTAssertEqual("value1", field!.currentValue as? String)
		XCTAssertTrue(field === record["field1"])
	}

	func test_fieldByName_Should_FindTheField_WhenCaseInsensitive() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"]])

		let field = record.fieldBy(name: "FIELD1")

		XCTAssertNotNil(field)
		XCTAssertEqual("field1", field!.name)
		XCTAssertEqual("value1", field!.currentValue as? String)
		XCTAssertTrue(field === record["FIELD1"])
	}

	func test_fieldByName_Should_FindReturnNil_WhenFieldDoesNotExist() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"]])

		let field = record.fieldBy(name: "field33")

		XCTAssertNil(field)
		XCTAssertTrue(field === record["field33"])
	}

}
