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
import LiferayScreens


open class LoginView_westeros: LoginView_default {

	@IBAction func showPassword(_ sender: AnyObject) {
		passwordField!.isSecureTextEntry = !passwordField!.isSecureTextEntry
	}

	override open func createProgressPresenter() -> ProgressPresenter {
		return WesterosProgressPresenter()
	}

	override open func onShow() {
		userNameField!.attributedPlaceholder = NSAttributedString(
				string: userNameField!.placeholder!,
				attributes: [.foregroundColor : UIColor.white])

		passwordField!.attributedPlaceholder = NSAttributedString(
				string: passwordField!.placeholder!,
				attributes: [.foregroundColor : UIColor.white])
	}

	override open func onSetDefaultDelegate(_ delegate:AnyObject, view:UIView) -> Bool {
		return false
	}

	override open func onSetUserActionForControl(_ control: UIControl) -> Bool {
		return control == self.loginButton
	}

}
