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

		let record = DDLRecord(dataAndAttributes: ["modelValues":values as AnyObject])

		XCTAssertEqual(2, record.fields.count)

		XCTAssertEqual(record.fields.filter { $0.name == "field1" }.count, 1)
		XCTAssertEqual(record.fields.filter { $0.name == "field1" }[0].currentValue as? String, "value1")

		XCTAssertEqual(record.fields.filter { $0.name == "field2" }.count, 1)
		XCTAssertEqual(record.fields.filter { $0.name == "field2" }[0].currentValue as? String, "value2")
	}

	func test_ModelValues_ShouldBe_Updated() {
		let values = ["field1":"value1", "field2":"value2"]

		let record = DDLRecord(dataAndAttributes: ["modelValues":values as AnyObject])

		record.updateCurrentValues(values: [
			"field1":"new_value1" as AnyObject,  // update this field
			"new_field3":"value3" as AnyObject])	// ignore this field

		XCTAssertEqual(2, record.fields.count)

		// updated
		XCTAssertEqual(record.fields.filter { $0.name == "field1" }.count, 1)
		XCTAssertEqual(record.fields.filter { $0.name == "field1" }[0].currentValue as? String, "new_value1")

		// not updated
		XCTAssertEqual(record.fields.filter { $0.name == "field2" }.count, 1)
		XCTAssertEqual(record.fields.filter { $0.name == "field2" }[0].currentValue as? String, "value2")

	}

	func test_ModelAttributes_ShouldBe_Read() {
		let attributes = ["attr1":"attrvalue1"]

		let record = DDLRecord(dataAndAttributes: ["modelAttributes":attributes as AnyObject])

		XCTAssertEqual(1, record.attributes.count)
		XCTAssertEqual("attrvalue1", record.attributes["attr1"] as? String)
	}

	func test_RecordId_ShouldBe_Read() {
		let attributes = ["recordId": 123]

		let record = DDLRecord(dataAndAttributes: ["modelAttributes":attributes as AnyObject])

		XCTAssertEqual(Int64(123), record.recordId!)
	}

	func test_fieldByName_Should_FindTheField() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"] as AnyObject])

		let field = record.fieldBy(name: "field1")

		XCTAssertNotNil(field)
		XCTAssertEqual("field1", field!.name)
		XCTAssertEqual("value1", field!.currentValue as? String)
		XCTAssertTrue(field === record["field1"])
	}

	func test_fieldByName_Should_FindTheField_WhenCaseInsensitive() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"] as AnyObject])

		let field = record.fieldBy(name: "FIELD1")

		XCTAssertNotNil(field)
		XCTAssertEqual("field1", field!.name)
		XCTAssertEqual("value1", field!.currentValue as? String)
		XCTAssertTrue(field === record["FIELD1"])
	}

	func test_fieldByName_Should_FindReturnNil_WhenFieldDoesNotExist() {
		let record = DDLRecord(dataAndAttributes: ["modelValues":["field1":"value1", "field2":"value2"] as AnyObject])

		let field = record.fieldBy(name: "field33")

		XCTAssertNil(field)
		XCTAssertTrue(field === record["field33"])
	}

}
