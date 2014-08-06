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

public enum AuthType: String {
	case Email = "Email Address"
	case ScreenName = "Screen Name"
	case UserId = "User ID"

	public static let KeyboardTypes = [
		AuthType.Email: UIKeyboardType.EmailAddress,
		AuthType.ScreenName: UIKeyboardType.ASCIICapable,
		AuthType.UserId: UIKeyboardType.NumberPad]
}

public class LoginView: BaseWidgetView {

	public var shouldRememberCredentials: Bool {
		return true
	}

	public func setAuthType(authTypeDescription: String) {
	}

	public func getUserName() -> String {
		return ""
	}

	public func getPassword() -> String {
		return ""
	}

	public func setUserName(userName: String) {
	}

	public func setPassword(password: String) {
	}

}
