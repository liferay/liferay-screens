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


public enum LoginAuthType: String {

	case Email = "Email Address"
	case ScreenName = "Screen Name"
	case UserId = "User ID"

	public var iconType: String {
		let iconTypes = [
				LoginAuthType.Email: "mail",
				LoginAuthType.ScreenName: "user",
				LoginAuthType.UserId: "user"]

		return iconTypes[self] ?? ""
	}

	public var keyboardType: UIKeyboardType {
		let keyboardTypes = [
				LoginAuthType.Email: UIKeyboardType.EmailAddress,
				LoginAuthType.ScreenName: UIKeyboardType.ASCIICapable,
				LoginAuthType.UserId: UIKeyboardType.NumberPad]

		return keyboardTypes[self] ?? .Default
	}

}
