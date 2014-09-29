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


public let LoginAuthTypeEmail = LoginAuth.Email.toRaw()
public let LoginAuthTypeScreenName = LoginAuth.ScreenName.toRaw()
public let LoginAuthTypeUserId = LoginAuth.UserId.toRaw()

public typealias LoginAuthType = String


public enum LoginAuth: String {

	case Email = "Email Address"
	case ScreenName = "Screen Name"
	case UserId = "User ID"

	public var iconType: String {
		let iconTypes = [
				LoginAuth.Email: "mail",
				LoginAuth.ScreenName: "user",
				LoginAuth.UserId: "user"]

		return iconTypes[self] ?? ""
	}

	public var keyboardType: UIKeyboardType {
		let keyboardTypes = [
				LoginAuth.Email: UIKeyboardType.EmailAddress,
				LoginAuth.ScreenName: UIKeyboardType.ASCIICapable,
				LoginAuth.UserId: UIKeyboardType.NumberPad]

		return keyboardTypes[self] ?? .Default
	}

}
