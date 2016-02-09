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


@objc public enum AuthType: Int {

	case Basic = 1
	case OAuth = 2

}

public func StringFromAuthType(authType: AuthType) -> String {
	switch authType {
	case .Basic:
		return "basic"
	case .OAuth:
		return "oauth"
	}
}

public func AuthTypeFromString(str: String) -> AuthType? {
	switch str.lowercaseString {
	case "basic":
		return .Basic
	case "oauth":
		return .OAuth
	default:
		return nil
	}
}
