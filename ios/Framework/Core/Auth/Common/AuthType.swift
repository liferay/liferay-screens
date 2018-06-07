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

	case basic = 1
	case cookie = 2
	case oauth2Redirect = 3
	case oauth2UsernameAndPassword = 4

}

public func StringFromAuthType(_ authType: AuthType) -> String {
	switch authType {
	case .basic:
		return "basic"
	case .cookie:
		return "cookie"
	case .oauth2Redirect:
		return "oauth2redirect"
	case .oauth2UsernameAndPassword:
		return "oauth2usernameandpassword"
	}
}

public func AuthTypeFromString(_ str: String) -> AuthType? {
	switch str.lowercased() {
	case "basic":
		return .basic
	case "cookie":
		return .cookie
	case "oauth2usernameandpassword":
		return .oauth2UsernameAndPassword
	case "oauth2redirect":
		return .oauth2Redirect
	default:
		return nil
	}
}
