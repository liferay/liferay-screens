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


public class SignUpView_westeros: SignUpView_default {

	override public func onCreated() {
		super.onCreated()

		BaseScreenlet.setHUDCustomColor(WesterosThemeBasicRed)

		let color = (firstNameField as! BorderedTextField).focusedColor!

		firstNameField?.attributedPlaceholder = NSAttributedString(
				string: firstNameField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : color])

		lastNameField?.attributedPlaceholder = NSAttributedString(
				string: lastNameField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : color])

		emailAddressField?.attributedPlaceholder = NSAttributedString(
				string: emailAddressField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : color])

		passwordField?.attributedPlaceholder = NSAttributedString(
				string: passwordField!.placeholder!,
				attributes: [NSForegroundColorAttributeName : color])
	}

	override public func onSetDefaultDelegate(delegate:AnyObject, view:UIView) -> Bool {
		return false
	}

}
