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

	optional func screenlet(screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject])

	optional func screenlet(screenlet: BaseScreenlet,
			onLoginError error: NSError)

	optional func onScreenletCredentialsSaved(screenlet: BaseScreenlet)
	optional func onScreenletCredentialsLoaded(screenlet: BaseScreenlet)

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

			delegate?.onScreenletCredentialsLoaded?(self)
		}
	}

	override public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		let interactor = LoginInteractor(screenlet: self)

		interactor.onSuccess = {
			self.delegate?.screenlet?(self,
					onLoginResponseUserAttributes: interactor.resultUserAttributes!)

			if self.saveCredentials {
				if SessionContext.storeSession() {
					self.delegate?.onScreenletCredentialsSaved?(self)
				}
			}
		}

		interactor.onFailure = {
			self.delegate?.screenlet?(self, onLoginError: $0)
			return
		}

		return interactor
	}

}
