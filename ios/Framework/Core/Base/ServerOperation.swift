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

	public var lastError: NSError?
	public var usedSession: LRSession?

	internal var onComplete: (ServerOperation -> Void)?


	//MARK: NSOperation

	public override func main() {
		if preRun() {
			if let session = createSession() {
				usedSession = session
				doRun(session: session)
				postRun()
			}
		}
		else {
			lastError = NSError.errorWithCause(.AbortedDueToPreconditions, userInfo: nil)
		}

		callOnComplete()
	}


	//MARK: Public methods

	public func validateAndEnqueue(onComplete: (ServerOperation -> Void)? = nil) -> ValidationError? {
		let error = validateData()

		if error == nil {
			enqueue(onComplete: onComplete)
		}

		return error
	}

	public func enqueue(onComplete: (ServerOperation -> Void)? = nil) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		OperationsQueue.addOperation(self)
	}


	//MARK: Template methods

	public func validateData() -> ValidationError? {
		// Do not add any code here. Children classes may not call super
		return nil
	}

	public func preRun() -> Bool {
		// Do not add any code here. Children classes may not call super
		return true
	}

	public func doRun(#session: LRSession) {
		// Do not add any code here. Children classes may not call super
	}

	public func postRun() {
		// Do not add any code here. Children classes may not call super
	}

	public func createSession() -> LRSession? {
		if !SessionContext.hasSession {
			lastError = NSError.errorWithCause(.AbortedDueToPreconditions,
					message: "Login required to use this operation")

			return nil
		}

		return SessionContext.createSessionFromCurrentSession()
	}

	public func callOnComplete() {
		if self.onComplete != nil {
			dispatch_main {
				self.onComplete!(self)

				// this breaks the retain cycle between the op and 'onComplete'
				self.onComplete = nil
			}
		}
	}

}
