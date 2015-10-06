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


public class LoginView_flat7: LoginView_default {

	@IBOutlet private var titleLabel: UILabel?
	@IBOutlet private var subtitleLabel: UILabel?
	@IBOutlet private var userNamePlaceholder: UILabel?
	@IBOutlet private var passwordPlaceholder: UILabel?


	//MARK: LoginView

	override public func onCreated() {
		super.onCreated()

		setFlat7ButtonBackground(loginButton)
	}

	override public var userName: String? {
		didSet {
			userNamePlaceholder!.changeVisibility(visible: userName != "")
		}
	}

	override public func onSetTranslations() {
		let bundle = NSBundle(forClass: self.dynamicType)

		titleLabel!.text = LocalizedString("flat7", "login-title", self)
		subtitleLabel!.text = LocalizedString("flat7", "login-subtitle", self)
		userNamePlaceholder!.text = LocalizedString("flat7" ,"login-email", self)
		passwordPlaceholder!.text = LocalizedString("flat7", "login-password", self)

		loginButton!.replaceAttributedTitle(LocalizedString("flat7", "login-login", self),
				forState: .Normal)

		userNameField!.placeholder = "";
		passwordField!.placeholder = "";
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return Flat7ProgressPresenter()
	}


	//MARK: UITextFieldDelegate

	internal func textField(textField: UITextField!,
			shouldChangeCharactersInRange range: NSRange,
			replacementString string: String!)
			-> Bool {

		let newText = (textField.text as NSString).stringByReplacingCharactersInRange(range, withString:string)

		let placeHolder = textField == userNameField ? userNamePlaceholder : passwordPlaceholder

		placeHolder!.changeVisibility(visible: newText != "")

		return true
	}

}
