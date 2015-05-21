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


@objc public protocol AnonymousAuthType {

	var anonymousApiUserName: String? { get set }
	var anonymousApiPassword: String? { get set }

}


@objc public protocol AuthBasedType {

	var authMethod: String? { get set }
	var saveCredentials: Bool { get set }

}


public func copyAuth(#source: AuthBasedType, #target: AnyObject?) {
	if let authBasedTarget = target as? AuthBasedType {
		authBasedTarget.authMethod = source.authMethod
		authBasedTarget.saveCredentials = source.saveCredentials
	}
}


public let AuthMethodTypeEmail = AuthMethod.Email.rawValue
public let AuthMethodTypeScreenName = AuthMethod.ScreenName.rawValue
public let AuthMethodTypeUserId = AuthMethod.UserId.rawValue

public typealias AuthMethodType = String


public enum AuthMethod: String {

	case Email = "email"
	case ScreenName = "screenName"
	case UserId = "userId"

	public static func create(text: String?) -> AuthMethod {
		if text?.lowercaseString == AuthMethod.ScreenName.rawValue.lowercaseString {
			return .ScreenName
		} else if text?.lowercaseString == AuthMethod.UserId.rawValue.lowercaseString {
			return .UserId
		}

		return .Email
	}

	public static func fromUserName(userName: String) -> AuthMethod {
		if find(userName, "@") != nil {
			return .Email
		}

		if userName.toInt() != nil {
			return .UserId
		}

		return .ScreenName
	}

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

	public var description: String {
		let descriptions = [
			AuthMethod.Email: "auth-method-email",
			AuthMethod.ScreenName: "auth-method-screenname",
			AuthMethod.UserId: "auth-method-userid"]

		return descriptions[self] ?? ""
	}

}

