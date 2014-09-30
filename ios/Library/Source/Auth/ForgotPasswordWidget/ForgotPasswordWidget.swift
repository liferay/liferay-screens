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


@IBDesignable public class ForgotPasswordWidget: BaseWidget, AuthBased, AnonymousAuth {

	//MARK: Inspectables

	@IBInspectable public var anonymousApiUserName: String?
	@IBInspectable public var anonymousApiPassword: String?

	@IBInspectable public var authMethod: AuthMethodType = AuthMethod.Email.toRaw() {
		didSet {
			copyAuth(source: self, target: widgetView)

			switch AuthMethod.fromRaw(authMethod)! {
				case .Email:
					connector = LiferayForgotPasswordEmailConnector(widget: self)
				case .ScreenName:
					connector = LiferayForgotPasswordScreenNameConnector(widget: self)
				case .UserId:
					connector = LiferayForgotPasswordUserIdConnector(widget: self)
				default: ()
			}
		}
	}

	@IBOutlet public var delegate: ForgotPasswordWidgetDelegate?


	public var saveCredentials: Bool {
		get { return false }
		set {}
	}

	internal var forgotPasswordView: ForgotPasswordView {
		return widgetView as ForgotPasswordView
	}

	internal var forgotPasswordConnector: LiferayForgotPasswordBaseConnector {
		return connector as LiferayForgotPasswordBaseConnector
	}


	//MARK: BaseWidget

	override internal func onCreated() {
		super.onCreated()

		copyAuth(source: self, target: widgetView)

		if let userName = SessionContext.currentUserName {
			forgotPasswordView.userName = userName
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		if forgotPasswordView.userName != nil {
			connector?.enqueue() {
				if let error = $0.lastError {
					self.delegate?.onForgotPasswordError?(error)
				}
				else {
					self.delegate?.onForgotPasswordResponse?(
							self.forgotPasswordConnector.newPasswordSent!)
				}
			}
		}
		else {
			showHUDAlert(message: "Please, enter the user name")
		}
	}

}

