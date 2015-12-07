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


public class ForgotPasswordView_default: BaseScreenletView, ForgotPasswordViewModel {

	@IBOutlet public var userNameIcon: UIImageView?
	@IBOutlet public var userNameField: UITextField?
	@IBOutlet public var requestPasswordButton: UIButton?


	override public var progressMessages: [String:ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction :
				[.Working : LocalizedString("default", key: "forgotpassword-loading-message", obj: self),
				.Failure : LocalizedString("default", key: "forgotpassword-loading-error", obj: self),
				.Success : LocalizedString("default", key: "forgotpassword-\(successMessageKey)", obj: self)]
		]
	}

	private var successMessageKey = "password-sent"

	//MARK: ForgotPasswordViewModel

	public var userName: String? {
		get {
			return nullIfEmpty(userNameField!.text)
		}
		set {
			userNameField!.text = newValue
		}
	}


	//MARK: AuthBasedViewModel

	public var basicAuthMethod: String? = BasicAuthMethod.Email.rawValue {
		didSet {
			setBasicAuthMethodStyles(
					view: self,
					basicAuthMethod: BasicAuthMethod.create(basicAuthMethod),
					userNameField: userNameField,
					userNameIcon: userNameIcon)
		}
	}

	public var saveCredentials: Bool {
		get {
			return false
		}
		set {}
	}


	//MARK: BaseScreenletView

	override public func onCreated() {
		super.onCreated()

		setButtonDefaultStyle(requestPasswordButton)
	}

	override public func onSetTranslations() {
		requestPasswordButton?.replaceAttributedTitle(
				LocalizedString("default", key: "forgotpassword-button", obj: self),
				forState: .Normal)

	}

	override public func onStartInteraction() {
		requestPasswordButton!.enabled = false
	}

	override public func onFinishInteraction(result: AnyObject?, error: NSError?) {
		requestPasswordButton!.enabled = true

		if let resultPasswordSent = result as? Bool {
			successMessageKey = resultPasswordSent ? "password-sent" : "reset-sent"
		}
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}


	//MARK: UITextFieldDelegate

	internal func textFieldDidBeginEditing(textField: UITextField!) {
		userNameField!.highlighted = (textField == userNameField)
	}

}
