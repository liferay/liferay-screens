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


public class LoginView_westeros: LoginView_default {

	@IBAction func showPassword(sender: AnyObject) {
		passwordField!.secureTextEntry = !passwordField!.secureTextEntry
	}

	override public func onCreated() {
		super.onCreated()

		BaseScreenlet.setHUDCustomColor(WesterosThemeBasicRed)
	}

	public override func onShow() {
		userNameField!.attributedPlaceholder = NSAttributedString(
				string: userNameField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : UIColor.whiteColor()])

		passwordField!.attributedPlaceholder = NSAttributedString(
				string: passwordField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : UIColor.whiteColor()])
	}

	override public func onSetDefaultDelegate(delegate:AnyObject, view:UIView) -> Bool {
		return false
	}

	override public func onSetUserActionForControl(control: UIControl) -> Bool {
		return control == self.loginButton
	}

}
