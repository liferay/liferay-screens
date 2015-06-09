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


@objc public protocol ForgotPasswordScreenletDelegate {

	optional func screenlet(screenlet: ForgotPasswordScreenlet,
			onForgotPasswordSent passwordSent: Bool)

	optional func screenlet(screenlet: ForgotPasswordScreenlet,
			onForgotPasswordError error: NSError)

}


@IBDesignable public class ForgotPasswordScreenlet: BaseScreenlet, BasicAuthBasedType,
		AnonymousBasicAuthType {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String? = "test@liferay.com"
	@IBInspectable public var anonymousApiPassword: String? = "test"

	@IBInspectable public var basicAuthMethod: String? = BasicAuthMethod.Email.rawValue {
		didSet {
			copyBasicAuth(source: self, target: screenletView)
		}
	}

	@IBInspectable var companyId: Int64 = 0


	@IBOutlet public weak var delegate: ForgotPasswordScreenletDelegate?


	public var saveCredentials: Bool {
		get { return false }
		set {}
	}

	public var viewModel: ForgotPasswordViewModel {
		return screenletView as! ForgotPasswordViewModel
	}


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()

		copyBasicAuth(source: self, target: screenletView)

		if let userName = SessionContext.currentBasicUserName {
			viewModel.userName = userName
		}
	}

	override public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		let interactor = ForgotPasswordInteractor(screenlet: self)

		interactor.onSuccess = {
			self.delegate?.screenlet?(self,
					onForgotPasswordSent: interactor.resultPasswordSent!)
			return
		}

		interactor.onFailure = {
			self.delegate?.screenlet?(self, onForgotPasswordError: $0)
			return
		}

		return interactor
	}

}

