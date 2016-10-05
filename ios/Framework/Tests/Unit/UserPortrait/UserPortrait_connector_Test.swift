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
import UIKit
import XCTest

class UserPortrait_connector_Test: XCTestCase {

	override func setUp() {
		super.setUp()

		SessionContext.loginWithBasic(username: "fake", password: "fake", userAttributes: [:])
	}

	func test_shouldNotUploadImageWithSizeLessThan100x100px() {
		let imageToUpload = UIImage(
				named: "tinyImage.png",
				inBundle: NSBundle(forClass: self.dynamicType),
				compatibleWithTraitCollection: nil)!

		let expectation = expectationWithDescription("Should throw fileToolarge error")
		
		let connector = Liferay70UploadUserPortraitConnector(userId: 1000, image: imageToUpload)

		connector.onComplete = { connector in
			XCTAssertNotNil(connector.lastError)

			XCTAssertEqual(connector.lastError!.code, -2)
			XCTAssertNotNil(connector.lastError!.userInfo)
			XCTAssertEqual(connector.lastError!.userInfo[NSLocalizedDescriptionKey] as? String,
					"User portrait image is too large")
			expectation.fulfill()
		}

		connector.start()

		waitForExpectationsWithTimeout(5, handler: nil)
	}
}
