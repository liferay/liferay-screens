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
import UIKit


class DDLListScreenlet_ParseFields_Tests: XCTestCase {

	var screenlet: DDLListScreenlet?

	override func setUp() {
		super.setUp()

		screenlet = DDLListScreenlet(frame: CGRectZero)
		screenlet!.themeName = "test"
		screenlet!.screenletView = screenlet!.loadScreenletView()
	}

	func test_LabelFields_ShouldParse_SingleField() {
		screenlet!.labelFields = "A"

		let parsedFields = (screenlet!.screenletView as DDLListViewModel).labelFields

		XCTAssertEqual(1, parsedFields.count)
		XCTAssertEqual("A", parsedFields[0])
	}

	func test_LabelFields_ShouldParse_SingleFieldWithTrailingSpaces() {
		screenlet!.labelFields = " A "

		let parsedFields = (screenlet!.screenletView as DDLListViewModel).labelFields

		XCTAssertEqual(1, parsedFields.count)
		XCTAssertEqual("A", parsedFields[0])
	}

	func test_LabelFields_ShouldParse_SeveralFields() {
		screenlet!.labelFields = "A,B,C"

		let parsedFields = (screenlet!.screenletView as DDLListViewModel).labelFields

		XCTAssertEqual(3, parsedFields.count)
		XCTAssertEqual("A", parsedFields[0])
		XCTAssertEqual("B", parsedFields[1])
		XCTAssertEqual("C", parsedFields[2])
	}

	func test_LabelFields_ShouldParse_SeveralFieldsWithTrailingSpaces() {
		screenlet!.labelFields = " A, B ,C "

		let parsedFields = (screenlet!.screenletView as DDLListViewModel).labelFields

		XCTAssertEqual(3, parsedFields.count)
		XCTAssertEqual("A", parsedFields[0])
		XCTAssertEqual("B", parsedFields[1])
		XCTAssertEqual("C", parsedFields[2])
	}

}
