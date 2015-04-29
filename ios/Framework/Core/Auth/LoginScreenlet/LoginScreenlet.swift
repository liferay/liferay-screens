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


@objc public protocol LoginScreenletDelegate {

	optional func onLoginResponse(attributes: [String:AnyObject])
	optional func onLoginError(error: NSError)

	optional func onCredentialsSaved()
	optional func onCredentialsLoaded()

}


public class LoginScreenlet: BaseScreenlet, AuthBasedType {

	//MARK: Inspectables

	@IBInspectable public var authMethod: String? = AuthMethod.Email.rawValue {
		didSet {
			copyAuth(source: self, target: screenletView)
		}
	}

	@IBInspectable public var saveCredentials: Bool = false {
		didSet {
			(screenletView as? AuthBasedType)?.saveCredentials = self.saveCredentials
		}
	}

	@IBInspectable public var companyId: Int64 = 0


	@IBOutlet public weak var delegate: LoginScreenletDelegate?


	public var viewModel: LoginViewModel {
		return screenletView as! LoginViewModel
	}


	//MARK: BaseScreenlet

	override public func onCreated() {
		super.onCreated()
		
		copyAuth(source: self, target: screenletView)

		if SessionContext.loadSessionFromStore() {
			viewModel.userName = SessionContext.currentUserName!
			viewModel.password = SessionContext.currentPassword!

			delegate?.onCredentialsLoaded?()
		}
	}

	override internal func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		let interactor = LoginInteractor(screenlet: self)

		interactor.onSuccess = {
			self.delegate?.onLoginResponse?(interactor.resultUserAttributes!)

			if self.saveCredentials {
				if SessionContext.storeSession() {
					self.delegate?.onCredentialsSaved?()
				}
			}
		}

		interactor.onFailure = {
			self.delegate?.onLoginError?($0)
			return
		}

		return interactor
	}

}
