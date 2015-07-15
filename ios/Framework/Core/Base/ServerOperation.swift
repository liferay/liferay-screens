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


public class ServerOperation: NSOperation {

	private struct OperationsQueue {

		static private var queue: NSOperationQueue?

		static func addOperation(operation: ServerOperation) {
			if queue == nil {
				queue = NSOperationQueue()
				queue!.maxConcurrentOperationCount = 1
			}

			queue!.addOperation(operation)
		}

	}


	public typealias HUDMessage = (String, details: String?)


	public var lastError: NSError?

	public var hudLoadingMessage: HUDMessage? { return nil }
	public var hudFailureMessage: HUDMessage? { return nil }
	public var hudSuccessMessage: HUDMessage? { return nil }

	internal var screenlet: BaseScreenlet
	internal var onComplete: (ServerOperation -> Void)?

	internal var anonymousAuth: AnonymousBasicAuthType? {
		return screenlet as? AnonymousBasicAuthType
	}


	public init(screenlet: BaseScreenlet) {
		self.screenlet = screenlet

		super.init()
	}


	//MARK: NSOperation

	public override func main() {
		if preRun() {
			if let session = createSession() {
				prepareRun()
				doRun(session: session)
				postRun()
				finishRun()
			}
		}
		else {
			lastError = NSError.errorWithCause(.AbortedDueToPreconditions, userInfo: nil)
		}

		callOnComplete()
	}


	//MARK: Public methods

	public func validateAndEnqueue(onComplete: (ServerOperation -> Void)? = nil) -> Bool {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		let result = validateData()

		if result {
			OperationsQueue.addOperation(self)
		}

		return result
	}


	//MARK: Internal methods

	internal func validateData() -> Bool {
		// Do not add any code here. Children classes may not call super
		return true
	}



	internal func preRun() -> Bool {
		// Do not add any code here. Children classes may not call super
		return true
	}

	internal func doRun(#session: LRSession) {
		// Do not add any code here. Children classes may not call super
	}

	internal func postRun() {
		// Do not add any code here. Children classes may not call super
	}

	internal func createSession() -> LRSession? {
		if let anonymousAuthValue = anonymousAuth {
			if anonymousAuthValue.anonymousApiUserName == nil ||
					anonymousAuthValue.anonymousApiPassword == nil {

				lastError = NSError.errorWithCause(.AbortedDueToPreconditions,
						message: "User name and password are required for anonymous API calls")

				return nil
			}

			return LRSession(
					server: LiferayServerContext.server,
					authentication: LRBasicAuthentication(
							username: anonymousAuthValue.anonymousApiUserName!,
							password: anonymousAuthValue.anonymousApiPassword!))
		}
		else if !SessionContext.hasSession {
			lastError = NSError.errorWithCause(.AbortedDueToPreconditions,
					message: "Login required to use this screenlet")

			return nil
		}

		return SessionContext.createSessionFromCurrentSession()
	}

	//MARK: HUD methods

	internal func showHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.showHUDWithMessage(message, details: details)
		}
	}

	internal func showHUD(
			#message: String,
			details: String?,
			closeMode: BaseScreenlet.CloseMode,
			spinnerMode: BaseScreenlet.SpinnerMode) {

		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.showHUDWithMessage(message,
					details: details,
					closeMode: closeMode,
					spinnerMode: spinnerMode)
		}
	}

	internal func showValidationHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.showHUDAlert(message: message, details: details)
		}
	}

	internal func hideHUD() {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.hideHUD()
		}
	}

	internal func hideHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.hideHUDWithMessage(message, details: details)
		}
	}

	internal func hideHUD(#errorMessage: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.showHUDWithMessage(errorMessage,
					details: details,
					closeMode: .ManualClose(true),
					spinnerMode:.NoSpinner)
		}
	}

	internal func hideHUD(#error: NSError, message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.showHUDWithMessage(message,
				details: details,
				closeMode:.ManualClose(true),
				spinnerMode:.NoSpinner)
		}
	}


	//MARK: Private methods

	private func callOnComplete() {
		if self.onComplete != nil {
			dispatch_async(dispatch_get_main_queue()) {
				self.onComplete!(self)

				// this breaks the retain cycle between the op and 'onComplete'
				self.onComplete = nil
			}
		}
	}

	private func prepareRun() {
		self.screenlet.onStartOperation()
		self.screenlet.screenletView?.onStartOperation()

		if let messageValue = hudLoadingMessage {
			showHUD(message: messageValue.0, details: messageValue.details)
		}
	}

	private func finishRun() {
		if let lastErrorValue = lastError {
			if let messageValue = hudFailureMessage {
				hideHUD(error: lastErrorValue,
						message: messageValue.0,
						details: messageValue.details)
			}
			else if hudLoadingMessage != nil {
				hideHUD()
			}
		}
		else {
			if let messageValue = hudSuccessMessage {
				hideHUD(message: messageValue.0, details: messageValue.details)
			}
			else if hudLoadingMessage != nil {
				hideHUD()
			}
		}

		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.onFinishOperation()
			self.screenlet.screenletView?.onFinishOperation()
		}
	}

}
