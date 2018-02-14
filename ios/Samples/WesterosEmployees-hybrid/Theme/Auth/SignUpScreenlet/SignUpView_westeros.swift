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


open class SignUpView_westeros: SignUpView_default {

	override open func onCreated() {
		super.onCreated()

		let color = (firstNameField as! BorderedTextField).focusedColor!

		firstNameField?.attributedPlaceholder = NSAttributedString(
				string: firstNameField!.placeholder!,
				attributes: [.foregroundColor : color])

		lastNameField?.attributedPlaceholder = NSAttributedString(
				string: lastNameField!.placeholder!,
				attributes: [.foregroundColor : color])

		emailAddressField?.attributedPlaceholder = NSAttributedString(
				string: emailAddressField!.placeholder!,
				attributes: [.foregroundColor : color])

		passwordField?.attributedPlaceholder = NSAttributedString(
				string: passwordField!.placeholder!,
				attributes: [.foregroundColor : color])
	}

	override open func createProgressPresenter() -> ProgressPresenter {
		return WesterosProgressPresenter()
	}

	override open func onSetDefaultDelegate(_ delegate:AnyObject, view:UIView) -> Bool {
		return false
	}

	open override func onStartInteraction() {
	}

	open override func onFinishInteraction(_ result: AnyObject?, error: NSError?) {
	}

}
