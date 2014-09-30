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


class BaseWidget_TabSequence_Tests: XCTestCase {

	var view:SignUpView_default?

	override func setUp() {
		super.setUp()

		self.continueAfterFailure = false

		let widget = SignUpWidget(frame: CGRectMake(0, 0, 100, 100))
		view = widget.loadWidgetView() as? SignUpView_default
		
		XCTAssertNotNil(view)
	}

/* Doesn't work because isFirstResponder always return false
	func test_TabSequence_ShouldBeConfigured_WhenNextResponderForViewIsOverriden() {
		view!.textFieldShouldReturn(view!.firstNameField)
		XCTAssertFalse(view!.firstNameField!.isFirstResponder())
		XCTAssertTrue(view!.lastNameField!.editing)
	}
*/
	func test_TabSequence_ShouldFireCustomAction_WhenTabSequenceGoesToButton() {
		let expectation = expectationWithDescription("onUserAction must be called")

		view!.onUserAction = { (actionName, control) -> Void in
			expectation.fulfill()
		}

		view!.textFieldShouldReturn(view!.passwordField)

		waitForExpectationsWithTimeout(0, handler: nil)
	}

}
