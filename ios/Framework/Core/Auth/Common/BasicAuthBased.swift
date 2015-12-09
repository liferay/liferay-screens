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


@objc public protocol AnonymousBasicAuthType {

	var anonymousApiUserName: String? { get set }
	var anonymousApiPassword: String? { get set }

}


@objc public protocol BasicAuthBasedType {

	var basicAuthMethod: String? { get set }

}


public enum BasicAuthMethod: String {

	case Email = "email"
	case ScreenName = "screenName"
	case UserId = "userId"

	public static func all() -> [BasicAuthMethod] {
		return [.Email, .ScreenName, .UserId]
	}

	public static func create(text: String?) -> BasicAuthMethod {
		return all().filter {
				$0.rawValue.lowercaseString == text?.lowercaseString
			}.first ?? .Email
	}

	public static func fromUserName(userName: String) -> BasicAuthMethod {
		if userName.characters.indexOf("@") != nil {
			return .Email
		}

		if Int(userName) != nil {
			return .UserId
		}

		return .ScreenName
	}

	public var iconType: String {
		let iconTypes: [BasicAuthMethod:String] = [
				.Email: "mail",
				.ScreenName: "user",
				.UserId: "user"]

		return iconTypes[self] ?? ""
	}

	public var keyboardType: UIKeyboardType {
		let keyboardTypes: [BasicAuthMethod:UIKeyboardType] = [
				.Email: UIKeyboardType.EmailAddress,
				.ScreenName: UIKeyboardType.ASCIICapable,
				.UserId: UIKeyboardType.NumberPad]

		return keyboardTypes[self] ?? .Default
	}

	public var description: String {
		let descriptions: [BasicAuthMethod:String] = [
			.Email: "auth-method-email",
			.ScreenName: "auth-method-screenname",
			.UserId: "auth-method-userid"]

		return descriptions[self] ?? ""
	}

}

