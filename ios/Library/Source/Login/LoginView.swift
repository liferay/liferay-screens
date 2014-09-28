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


public class LoginView: BaseWidgetView {

	public var saveCredentials: Bool {
		get {
			return _saveCredentials
		}
		set {
			_saveCredentials = newValue
		}
	}

	public var authType: LoginAuthType? = .Email

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

	private var _saveCredentials: Bool = false

}
