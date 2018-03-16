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

@objc(ServerConnector)
@objcMembers
open class ServerConnector: Operation {

	fileprivate struct ConnectorsQueue {

		static fileprivate var queue: OperationQueue?

		static func addConnector(_ connector: ServerConnector) {
			if queue == nil {
				queue = OperationQueue()
				queue!.maxConcurrentOperationCount = 1
			}

			queue!.addOperation(connector)
		}

	}

	var retried = false
	var currentSession: LRSession?

	open var lastError: NSError?

	internal var onComplete: ((ServerConnector) -> Void)?

	// MARK: NSOperation

	open override func main() {
		if self.isCancelled {
			lastError = NSError.errorWithCause(.cancelled)
		}
		else {
			if preRun() {
				if let session = createSession() {
					doRun(session: session)
					postRun()
				}
				else {
					if lastError == nil {
						lastError = NSError.errorWithCause(.notAvailable, message: "Could not create session")
					}
				}
			}
			else {
				lastError = NSError.errorWithCause(.abortedDueToPreconditions)
			}
		}

		callOnComplete()
	}

	// MARK: Public methods
	@discardableResult
	open func validateAndEnqueue(_ onComplete: ((ServerConnector) -> Void)? = nil) -> ValidationError? {
		let error = validateData()

		if error == nil {
			enqueue(onComplete)
		}

		return error
	}

	open func enqueue(_ onComplete: ((ServerConnector) -> Void)? = nil) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		ConnectorsQueue.addConnector(self)
	}

	// MARK: Template methods

	open func validateData() -> ValidationError? {
		// Do not add any code here. Children classes may not call super
		return nil
	}

	open func preRun() -> Bool {
		// Do not add any code here. Children classes may not call super
		return true
	}

	open func doRun(session: LRSession) {
		// Do not add any code here. Children classes may not call super
	}

	open func postRun() {
		// Do not add any code here. Children classes may not call super
	}

	open func createSession() -> LRSession? {
		guard SessionContext.isLoggedIn else {
			lastError = NSError.errorWithCause(.abortedDueToPreconditions,
					message: "Login required to use this connector")

			return nil
		}

		return SessionContext.currentContext?.createRequestSession()
	}

	open func callOnComplete() {
		if self.onComplete != nil {
			dispatch_main {
				self.onComplete!(self)

				// this breaks the retain cycle between the op and 'onComplete'
				self.onComplete = nil
			}
		}
	}
}
