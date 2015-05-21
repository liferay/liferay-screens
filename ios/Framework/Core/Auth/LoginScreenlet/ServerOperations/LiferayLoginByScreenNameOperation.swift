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


public class LiferayLoginByScreenNameOperation: GetUserByScreenNameOperation {

	override public var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("login-screenlet", "loading-message", self),
				details: LocalizedString("login-screenlet", "loading-details", self))
	}
	override public var hudFailureMessage: HUDMessage? {
		return (LocalizedString("login-screenlet", "loading-error", self), details: nil)
	}

	override internal func validateData() -> Bool {
		let valid = super.validateData()

		if !valid {
			showValidationHUD(message: LocalizedString("login-screenlet", "validation", self))
		}

		return valid
	}

	override internal func postRun() {
		if lastError == nil {
			setResultAsSessionContext()
		}
	}

}
