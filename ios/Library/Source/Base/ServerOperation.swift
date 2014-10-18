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


	internal typealias HUDMessage = (String, details: String?)


	internal var lastError: NSError?
	internal var screenlet: BaseScreenlet

	internal var onComplete: (ServerOperation -> Void)?

	internal var anonymousAuth: AnonymousAuthData? {
		return screenlet as? AnonymousAuthData
	}

	internal var hudLoadingMessage: HUDMessage? { return nil }
	internal var hudFailureMessage: HUDMessage? { return nil }
	internal var hudSuccessMessage: HUDMessage? { return nil }


	internal init(screenlet: BaseScreenlet) {
		self.screenlet = screenlet

		super.init()
	}


	//MARK: NSOperation

	public override func main() {
		if preRun() {
			var session: LRSession?

			if let anonymousAuthValue = anonymousAuth {
				if anonymousAuthValue.anonymousApiUserName == nil ||
						anonymousAuthValue.anonymousApiPassword == nil {

					lastError = createError(
							cause: .AbortedDueToPreconditions,
							message: "User name and password are required for anonymous API calls")
					callOnComplete()

					return
				}

				session = LRSession(
						server: LiferayServerContext.server,
						username: anonymousAuthValue.anonymousApiUserName!,
						password: anonymousAuthValue.anonymousApiPassword!)
			}
			else {
				session = SessionContext.createSessionFromCurrentSession()
				if session == nil {
					lastError = createError(
							cause: .AbortedDueToPreconditions,
							message: "Login required to use this screenlet")
					callOnComplete()

					return
				}
			}

			if let messageValue = hudLoadingMessage {
				showHUD(message: messageValue.0, details: messageValue.details)
			}

			doRun(session: session!)
			postRun()
			closeHUD()
			callOnComplete()
		}
		else {
			lastError = createError(cause: .AbortedDueToPreconditions, userInfo: nil)
			callOnComplete()
		}

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


	//MARK: HUD methods

	internal func showHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.startOperationWithMessage(message, details: details)
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
			self.screenlet.finishOperation()
		}
	}

	internal func hideHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.screenlet.finishOperationWithMessage(message, details: details)
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
			self.screenlet.finishOperationWithError(error, message: message, details: details)
		}
	}


	//MARK: Private methods

	private func callOnComplete() {
		if self.onComplete != nil {
			dispatch_sync(dispatch_get_main_queue()) {
				self.onComplete!(self)
			}
		}
	}

	private func closeHUD() {
		if lastError == nil {
			if let messageValue = hudSuccessMessage {
				hideHUD(message: messageValue.0, details: messageValue.details)
			}
			else if hudLoadingMessage != nil {
				hideHUD()
			}
		}
		else {
			if let messageValue = hudFailureMessage {
				hideHUD(error: lastError!,
						message: messageValue.0,
						details: messageValue.details)
			}
			else if hudLoadingMessage != nil {
				hideHUD()
			}
		}
	}

}
