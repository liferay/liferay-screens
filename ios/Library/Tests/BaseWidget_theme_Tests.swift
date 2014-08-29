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

class BaseWidget_theme_Tests: XCTestCase {

	var loginWidget:LoginWidget?

	override func setUp() {
		super.setUp()

		loginWidget = LoginWidget(frame: CGRectMake(0, 0, 100, 100))
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_CurrentThemeName_ShouldReturnDefault_WhenNoThemeIsSelected() {
		XCTAssertEqual("default", loginWidget!.currentThemeName())
	}

	func test_CurrentThemeName_ShouldReturnTheNameOfTheSelectedTheme_WhenThemeIsSelected() {
		loginWidget!.Theme = loginWidget!.loadImageFromIB("theme-xyz")
		XCTAssertEqual("xyz", loginWidget!.currentThemeName())
	}

	func test_LoadWidgetView_ShouldReturnBaseView_WhenNoThemeIsSelected() {
		let view = loginWidget!.loadWidgetView()

		XCTAssertNotNil(view)
		XCTAssertEqual(1, loginWidget!.subviews.count)
		XCTAssertTrue(loginWidget!.subviews[0] is LoginView)
		XCTAssertEqual(view!, loginWidget!.subviews[0] as BaseWidgetView)
	}

	func test_LoadWidgetView_ShouldReturnCustomizedView_WhenThemeIsSelected() {
		loginWidget!.Theme = loginWidget!.loadImageFromIB("theme-xyz")
		let view = loginWidget!.loadWidgetView()

		XCTAssertNotNil(view)
		XCTAssertEqual(1, loginWidget!.subviews.count)
		XCTAssertTrue(loginWidget!.subviews[0] is LoginView_xyz)
		XCTAssertEqual(view!, loginWidget!.subviews[0] as BaseWidgetView)
	}

	func test_PreviewImageNameForTheme_ShouldReturnThewImageName() {
		XCTAssertEqual("xyz-preview-login", loginWidget!.previewImageNameForTheme("xyz"))
	}

	func test_SignatureImageNameForTheme_ShouldReturnTheImageName() {
		XCTAssertEqual("theme-xyz", loginWidget!.signatureImageNameForTheme("xyz"))
	}

}
