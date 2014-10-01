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
				queue!.qualityOfService = .UserInitiated
			}

			queue!.addOperation(operation)
		}

	}


	internal typealias HUDMessage = (String, details: String?)


	internal var lastError: NSError?
	internal var widget: BaseWidget

	internal var onComplete: (ServerOperation -> Void)?

	internal var anonymousAuth: AnonymousAuth? {
		return widget as? AnonymousAuth
	}

	internal var hudLoadingMessage: HUDMessage? { return nil }
	internal var hudFailureMessage: HUDMessage? { return nil }
	internal var hudSuccessMessage: HUDMessage? { return nil }


	internal init(widget: BaseWidget) {
		self.widget = widget

		super.init()

		self.name = NSStringFromClass(self.dynamicType)
	}


	//MARK: NSOperation

	public override func main() {
		if preRun() {
			var session: LRSession?

			if let anonymousAuthValue = anonymousAuth {
				session = LRSession(
						server: LiferayServerContext.server,
						username: anonymousAuthValue.anonymousApiUserName!,
						password: anonymousAuthValue.anonymousApiPassword!)
			}
			else {
				session = SessionContext.createSessionFromCurrentSession()
				assert(session != nil, "Login required to use this widget")
			}

			if let messageValue = hudLoadingMessage {
				showHUD(message: messageValue.0, details: messageValue.details)
			}

			doRun(session: session!)

			postRun()

			if lastError == nil {
				if let messageValue = hudSuccessMessage {
					hideHUD(message: messageValue.0, details: messageValue.details)
				}
				else if hudLoadingMessage != nil {
					hideHUD()
				}
			}
			else if let messageValue = hudFailureMessage {
				hideHUD(error: lastError!, message: messageValue.0, details: messageValue.details)
			}

			callOnComplete()
		}
		else {
			lastError = createError(cause: .AbortedDueToPreconditions, userInfo: nil)
			callOnComplete()
		}

		// operation recycle
		if self is NSCopying {
			widget.serverOperation = self.copy() as? ServerOperation
		}
	}


	//MARK: Public methods

	public func validateAndEnqueue(onComplete: (ServerOperation -> Void)? = nil) -> Bool {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		let result = validateView()

		if result {
			OperationsQueue.addOperation(self)
		}

		return result
	}


	//MARK: Internal methods

	internal func validateView() -> Bool {
		return true
	}

	internal func preRun() -> Bool {
		if let anonymousAuthValue = anonymousAuth {
			assert(anonymousAuthValue.anonymousApiUserName != nil,
					"User name required for anonymous API calls")
			assert(anonymousAuthValue.anonymousApiPassword != nil,
					"Password required for anonymous API calls")
		}

		return true
	}

	internal func doRun(#session: LRSession) {
	}

	internal func postRun() {
	}


	//MARK: HUD methods

	internal func showHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.startOperationWithMessage(message, details: details)
		}
	}

	internal func showValidationHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.showHUDAlert(message: message, details: details)
		}
	}

	internal func hideHUD() {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperation()
		}
	}

	internal func hideHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperationWithMessage(message, details: details)
		}
	}

	internal func hideHUD(#errorMessage: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.showHUDWithMessage(errorMessage,
					details: details,
					closeMode:.NoAutoclose(true),
					spinnerMode:.NoSpinner)
		}
	}

	internal func hideHUD(#error: NSError, message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperationWithError(error, message: message, details: details)
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

}
