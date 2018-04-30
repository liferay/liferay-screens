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

/// The LoginScreenletDelegate protocol defines some methods that you use to manage the
/// LoginScreenlet events. All of them are optional.
@objc(LoginScreenletDelegate)
public protocol LoginScreenletDelegate: BaseScreenletDelegate {

	/// Called when login successfully completes.
	/// The user attributes are passed as a dictionary of keys (String or NSStrings) 
	/// and values (AnyObject or NSObject).
	///
	/// - Parameters:
	///   - screenlet: Login screenlet instance.
	///   - attributes: User attributes.
	@objc optional func screenlet(_ screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String: AnyObject])

	///  Called when an error occurs during login.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet: Login screenlet instance.
	///   - error: Error in login.
	@objc optional func screenlet(_ screenlet: BaseScreenlet,
			onLoginError error: NSError)

	/// Called when the user credentials are stored after a successful login.
	///
	/// - Parameters:
	///   - screenlet: Login screenlet instance.
	///   - attributes: User attributes.
	@objc optional func screenlet(_ screenlet: BaseScreenlet,
		onCredentialsSavedUserAttributes attributes: [String: AnyObject])

	/// Called when the user credentials are retrieved. Note that this only occurs when the 
	/// Screenlet is used and stored credentials are available.
	///
	/// - Parameters:
	///   - screenlet: Login screenlet instance.
	///   - attributes: User attributes.
	@objc optional func screenlet(_ screenlet: LoginScreenlet,
		onCredentialsLoadedUserAttributes attributes: [String: AnyObject])

}

@objc(LoginScreenlet)
open class LoginScreenlet: BaseScreenlet, BasicAuthBasedType {

	// MARK: Inspectables

	/// Specifies the basic authentication option to use. You can set this attribute to email,
	/// screenName or userId. This must match the server’s authentication option. If you don’t set 
	/// this attribute, and don’t set the loginMode attribute to cookie, the Screenlet
	/// defaults to basic authentication with the email option.
	@IBInspectable open var basicAuthMethod: String? = BasicAuthMethod.email.rawValue {
		didSet {
			(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod
		}
	}

	/// When set, the user credentials and attributes are stored securely in the keychain. This 
	/// information can then be loaded in subsequent sessions by calling the 
	/// `SessionContext.loadStoredCredentials()` method.
	@IBInspectable open var saveCredentials: Bool = false

	/// The ID of the portal instance to authenticate to. If you don’t set this attribute or set it 
	/// to 0, the Screenlet uses the companyId setting in LiferayServerContext.
	@IBInspectable open var companyId: Int64 = 0

	/// Specifies if the system should handle the cookie expiration.
	/// If true, the cookie will be refreshed when its about to expire
	@IBInspectable open var shouldHandleCookieExpiration: Bool = true

	/// Specifies the cookie expiration time. In Minutes
	@IBInspectable open var cookieExpirationTime: Double = 1 * 60

	/// Scpecifies the clientId of the OAuth2 application
	@IBInspectable open var oauth2clientId: String = ""

	/// Specifies the clientSecret of the OAuth2 application. This is not needed for redirect flow.
	@IBInspectable open var oauth2clientSecret: String? = ""

	/// Specifies the redirectUrl. This has to be the same than the one configured in the server.
	@IBInspectable open var oauth2redirectURL: String? = ""

	/// Specifies the scopes that are going to be requested. Separated by a blank space
	@IBInspectable open var oauth2Scopes: String? = ""

	/// The Screenlet’s authentication type. You can set this attribute to basic or cookie. 
	/// If you don’t set this attribute, the Screenlet defaults to basic authentication.
	@IBInspectable open var loginMode: String = "login" {
		didSet {
			authType = AuthTypeFromString(loginMode) ?? .basic
		}
	}

	// MARK: Public properties

	open var loginDelegate: LoginScreenletDelegate? {
		return self.delegate as? LoginScreenletDelegate
	}

	open var viewModel: LoginViewModel {
		return screenletView as! LoginViewModel
	}

	open var authType: AuthType = .basic {
		didSet {
			copyAuthType()
		}
	}

	// MARK: BaseScreenlet

	override open func onCreated() {
		super.onCreated()

		(screenletView as? BasicAuthBasedType)?.basicAuthMethod = basicAuthMethod

		copyAuthType()
	}

	override open func createInteractor(name: String, sender: Any?) -> Interactor? {
		let interactor: Interactor & LoginResult

		switch authType {
		case .basic:
			interactor = createLoginBasicInteractor()
		case .cookie:
			interactor = createLoginCookieInteractor()
		case .oauth2Redirect:
			interactor = createOAuth2RedirectInteractor()
		case .oauth2UsernameAndPassword:
			interactor = createOAuth2UsernameAndPasswordInteractor()
		}

		interactor.onSuccess = {
			self.loginDelegate?.screenlet?(self,
				onLoginResponseUserAttributes: interactor.resultUserAttributes!)

			if let ctx = SessionContext.currentContext, self.saveCredentials {
				if ctx.storeCredentials() {
					self.loginDelegate?.screenlet?(self,
						onCredentialsSavedUserAttributes: interactor.resultUserAttributes!)
				}
			}
		}

		interactor.onFailure = {
			self.loginDelegate?.screenlet?(self, onLoginError: $0)
		}

		return interactor
	}

	// MARK: Public methods

	/// loadStoredCredentials loads credentials if exist in the current context.
	///
	/// - Returns: True if succeed, false otherwise.
	open func loadStoredCredentials() -> Bool {
		if SessionContext.loadStoredCredentials() {
			viewModel.userName = SessionContext.currentContext?.basicAuthUsername
			viewModel.password = SessionContext.currentContext?.basicAuthPassword

			let userAttributes = SessionContext.currentContext!.user.attributes

			// We don't want the session to be automatically created. Clear it.
			// User can recreate it again in the delegate method.
			SessionContext.logout()

			loginDelegate?.screenlet?(self,
			                          onCredentialsLoadedUserAttributes: userAttributes)

			return true
		}

		return false
	}

	// MARK: Private methods

	fileprivate func createLoginBasicInteractor() -> LoginBasicInteractor {
		return LoginBasicInteractor(loginScreenlet: self)
	}

	fileprivate func createLoginCookieInteractor() -> LoginCookieInteractor {
		return LoginCookieInteractor(screenlet: self,
				username: viewModel.userName ?? "",
				password: viewModel.password ?? "",
				shouldHandleCookieExpiration: shouldHandleCookieExpiration,
				cookieExpirationTime: cookieExpirationTime)
	}

	fileprivate func createOAuth2RedirectInteractor() -> LoginOAuth2RedirectInteractor {
		return LoginOAuth2RedirectInteractor(redirectURL: oauth2redirectURL ?? "",
			scopes: parseScopes(),
			clientId: oauth2clientId)
	}

	fileprivate func createOAuth2UsernameAndPasswordInteractor() -> LoginOAuth2UsernamePasswordInteractor {
		return LoginOAuth2UsernamePasswordInteractor(screenlet: self,
			username: viewModel.userName ?? "",
			password: viewModel.password ?? "",
			scopes: parseScopes(),
			clientId: oauth2clientId,
			clientSecret: oauth2clientSecret ?? "")
	}

	fileprivate func parseScopes() -> [String] {
		guard let oauth2Scopes = oauth2Scopes,
			!oauth2Scopes.isEmpty else {
			return []
		}

		return oauth2Scopes.components(separatedBy: " ")
	}

	fileprivate func copyAuthType() {
		let loginViewModel = screenletView as? LoginViewModel
		loginViewModel?.authType = StringFromAuthType(authType)
	}

}
