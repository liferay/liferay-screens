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


@objc public protocol ForgotPasswordScreenletDelegate : BaseScreenletDelegate {

	/// Called when a password reset email is successfully sent.
	///
	/// - Parameters:
	///   - screenlet
	///   - passwordSent: indicates whether the email contains the new password
	/// or a password reset link.
	@objc optional func screenlet(_ screenlet: ForgotPasswordScreenlet,
			onForgotPasswordSent passwordSent: Bool)

	/// Called when an error occurs in the process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error while requesting password.
	@objc optional func screenlet(_ screenlet: ForgotPasswordScreenlet,
			onForgotPasswordError error: NSError)

}


open class ForgotPasswordScreenlet: BaseScreenlet, BasicAuthBasedType,
		AnonymousBasicAuthType {


	//MARK: Inspectables

	@IBInspectable open var anonymousApiUserName: String? = "test@liferay.com"

	@IBInspectable open var anonymousApiPassword: String? = "test"

	@IBInspectable open var basicAuthMethod: String? = BasicAuthMethod.email.rawValue {
		didSet {
			(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod
		}
	}

	@IBInspectable var companyId: Int64 = 0


	open var forgotPasswordDelegate: ForgotPasswordScreenletDelegate? {
		return delegate as? ForgotPasswordScreenletDelegate
	}


	open var saveCredentials: Bool {
		get { return false }
		set {}
	}

	open var viewModel: ForgotPasswordViewModel {
		return screenletView as! ForgotPasswordViewModel
	}


	//MARK: BaseScreenlet

	override open func onCreated() {
		super.onCreated()

		(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod

		if let userName = SessionContext.currentContext?.basicAuthUsername {
			viewModel.userName = userName
		}
	}

	override open func createInteractor(name: String, sender: AnyObject?) -> Interactor? {
		let interactor = ForgotPasswordInteractor(screenlet: self)

		interactor.onSuccess = {
			self.forgotPasswordDelegate?.screenlet?(self,
					onForgotPasswordSent: interactor.resultPasswordSent!)
		}

		interactor.onFailure = {
			self.forgotPasswordDelegate?.screenlet?(self, onForgotPasswordError: $0)
		}

		return interactor
	}

}

