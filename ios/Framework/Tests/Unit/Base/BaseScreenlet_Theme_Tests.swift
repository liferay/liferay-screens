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


class BaseScreenlet_Theme_Tests: XCTestCase {

	var loginScreenlet:LoginScreenlet?

	override func setUp() {
		super.setUp()

		loginScreenlet = LoginScreenlet(frame: CGRectMake(0, 0, 100, 100))
	}

	func test_CurrentThemeName_ShouldReturnDefault_WhenNoThemeIsSelected() {
		XCTAssertEqual("default", loginScreenlet!.themeName!)
	}

	func test_themeName_ShouldReturnTheNameOfTheSelectedTheme_WhenThemeExists() {
		loginScreenlet!.themeName = "xyz"

		XCTAssertEqual("xyz", loginScreenlet!.themeName!)
	}

	func test_themeName_ShouldReturnTheNameOfTheSelectedThemeInLowercase() {
		loginScreenlet!.themeName = "XyZ"

		XCTAssertEqual("xyz", loginScreenlet!.themeName!)
	}

	func test_themeName_ShouldReturnDefaultTheme_WhenThemeIsNil() {
		loginScreenlet!.themeName = nil

		XCTAssertEqual("default", loginScreenlet!.themeName!)
	}

	func test_LoadScreenletView_ShouldReturnCustomizedView_WhenThemeIsSelected() {
		loginScreenlet!.themeName = "test"

		let view = loginScreenlet!.loadScreenletView()

		XCTAssertNotNil(view)
		XCTAssertEqual(1, loginScreenlet!.subviews.count)
		XCTAssertTrue(loginScreenlet!.subviews[0] is LoginView_test)
		XCTAssertEqual(view!, loginScreenlet!.subviews[0] as? BaseScreenletView)
	}

}
