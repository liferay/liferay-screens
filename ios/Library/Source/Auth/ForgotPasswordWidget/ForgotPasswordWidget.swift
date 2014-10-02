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


@objc public protocol ForgotPasswordWidgetDelegate {

	optional func onForgotPasswordResponse(newPasswordSent:Bool)
	optional func onForgotPasswordError(error: NSError)

}


@IBDesignable public class ForgotPasswordWidget: BaseWidget, AuthBasedData, AnonymousAuthData {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String? = "test@liferay.com"
	@IBInspectable public var anonymousApiPassword: String? = "test"

	@IBInspectable public var authMethod: String? = AuthMethod.Email.toRaw() {
		didSet {
			copyAuth(source: self, target: widgetView)

			switch AuthMethod.create(authMethod) {
				case .Email:
					serverOperation = LiferayForgotPasswordEmailOperation(widget: self)
				case .ScreenName:
					serverOperation = LiferayForgotPasswordScreenNameOperation(widget: self)
				case .UserId:
					serverOperation = LiferayForgotPasswordUserIdOperation(widget: self)
				default:
					serverOperation = nil
			}
		}
	}

	@IBInspectable var companyId: Int64 = 0 {
		didSet {
			(widgetView as? ForgotPasswordData)?.companyId = self.companyId
		}
	}


	@IBOutlet public var delegate: ForgotPasswordWidgetDelegate?


	public var saveCredentials: Bool {
		get { return false }
		set {}
	}

	internal var forgotPasswordData: ForgotPasswordData {
		return widgetView as ForgotPasswordData
	}

	internal var forgotPasswordOperation: LiferayForgotPasswordBaseOperation {
		return serverOperation as LiferayForgotPasswordBaseOperation
	}


	//MARK: BaseWidget

	override internal func onCreated() {
		super.onCreated()

		copyAuth(source: self, target: widgetView)

		forgotPasswordData.companyId = companyId

		if let userName = SessionContext.currentUserName {
			forgotPasswordData.userName = userName
		}
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		serverOperation?.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onForgotPasswordError?(error)
			}
			else {
				self.delegate?.onForgotPasswordResponse?(
						self.forgotPasswordOperation.newPasswordSent!)
			}
		}
	}

}

