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


class BaseWidget_Theme_Tests: XCTestCase {

	var loginWidget:LoginWidget?

	override func setUp() {
		super.setUp()

		loginWidget = LoginWidget(frame: CGRectMake(0, 0, 100, 100))
	}

	func test_CurrentThemeName_ShouldReturnDefault_WhenNoThemeIsSelected() {
		XCTAssertEqual("default", loginWidget!.themeName!)
	}

	func test_themeName_ShouldReturnTheNameOfTheSelectedTheme_WhenThemeExists() {
		loginWidget!.themeName = "xyz"

		XCTAssertEqual("xyz", loginWidget!.themeName!)
	}

	func test_themeName_ShouldReturnTheNameOfTheSelectedThemeInLowercase() {
		loginWidget!.themeName = "XyZ"

		XCTAssertEqual("xyz", loginWidget!.themeName!)
	}

	func test_themeName_ShouldReturnDefaultTheme_WhenThemeDoesNotExist() {
		loginWidget!.themeName = "not-exists"

		XCTAssertEqual("default", loginWidget!.themeName!)
	}

	func test_themeName_ShouldReturnDefaultTheme_WhenThemeIsNil() {
		loginWidget!.themeName = nil

		XCTAssertEqual("default", loginWidget!.themeName!)
	}

	func test_LoadWidgetView_ShouldReturnDefaultView_WhenNoThemeIsSelected() {
		let view = loginWidget!.loadWidgetView()

		XCTAssertNotNil(view)
		XCTAssertEqual(1, loginWidget!.subviews.count)
		XCTAssertTrue(loginWidget!.subviews[0] is LoginView_default)
		XCTAssertEqual(view!, loginWidget!.subviews[0] as BaseWidgetView)
	}

	func test_LoadWidgetView_ShouldReturnCustomizedView_WhenThemeIsSelected() {
		loginWidget!.themeName = "xyz"

		let view = loginWidget!.loadWidgetView()

		XCTAssertNotNil(view)
		XCTAssertEqual(1, loginWidget!.subviews.count)
		XCTAssertTrue(loginWidget!.subviews[0] is LoginView_xyz)
		XCTAssertEqual(view!, loginWidget!.subviews[0] as BaseWidgetView)
	}

	func test_PreviewImagePathForTheme_ShouldReturnThewImagePath_WhenExists() {
		XCTAssertTrue(
				loginWidget!.previewImagePathForTheme("xyz")!.hasSuffix("xyz-preview-login.png"))
	}

	func test_PreviewImagePathForTheme_ShouldReturnNil_WhenNotExists() {
		XCTAssertNil(loginWidget!.previewImagePathForTheme("not-exists"))
	}

}
