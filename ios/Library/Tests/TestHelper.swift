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
import Foundation
import XCTest


func XCTAssertOptional(expression: @autoclosure () -> AnyObject?, _ message: String? = nil) {
	let evaluatedExpression:AnyObject? = expression()

	if evaluatedExpression == nil {
		if let messageValue = message {
			XCTFail(messageValue)
		}
		else {
			XCTFail("Optional asertion failed: \(evaluatedExpression)")
		}
	}
}


func testResourcePath(name: String, ext: String) -> String {
	let bundle = NSBundle(forClass:BaseScreenlet_Basic_Tests.self)
	let path = bundle.pathForResource(name, ofType:ext)

	if let pathValue = path {
		return pathValue
	}

	println("TEST ERROR: Resource \(name).\(ext) can't be found")
	return ""
}
