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


@objc public protocol AnonymousAuth {

	var anonymousApiUserName: String? { get set }
	var anonymousApiPassword: String? { get set }

}


@objc public protocol AuthBased {

	var saveCredentials: Bool { get set }
	var authMethod: AuthMethodType { get set }

}

public let AuthMethodTypeEmail = AuthMethod.Email.toRaw()
public let AuthMethodTypeScreenName = AuthMethod.ScreenName.toRaw()
public let AuthMethodTypeUserId = AuthMethod.UserId.toRaw()

public typealias AuthMethodType = String


public enum AuthMethod: String {

	case Email = "Email Address"
	case ScreenName = "Screen Name"
	case UserId = "User ID"

	public var iconType: String {
		let iconTypes = [
				AuthMethod.Email: "mail",
				AuthMethod.ScreenName: "user",
				AuthMethod.UserId: "user"]

		return iconTypes[self] ?? ""
	}

	public var keyboardType: UIKeyboardType {
		let keyboardTypes = [
				AuthMethod.Email: UIKeyboardType.EmailAddress,
				AuthMethod.ScreenName: UIKeyboardType.ASCIICapable,
				AuthMethod.UserId: UIKeyboardType.NumberPad]

		return keyboardTypes[self] ?? .Default
	}

}


public func setAuthMethodStyles(
		#authMethod: AuthMethod,
		#userNameField: UITextField!,
		#userNameIcon: UIImageView!) {

	userNameField!.placeholder = authMethod.toRaw()
	userNameField!.keyboardType = authMethod.keyboardType
	userNameIcon?.image = UIImage(named:"default-\(authMethod.iconType)-icon")
}

