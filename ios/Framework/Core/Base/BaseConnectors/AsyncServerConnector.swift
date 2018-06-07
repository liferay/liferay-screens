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

import Foundation

@objc(AsyncServerConnector)
open class AsyncServerConnector: ServerConnector {

	fileprivate var _finished: Bool = false

	fileprivate var _executing: Bool = false

	override open var isFinished: Bool {
		get {
			return _finished
		}
		set {
			willChangeValue(forKey: "isFinished")
			_finished = newValue
			didChangeValue(forKey: "isFinished")
		}
	}

	override open var isExecuting: Bool {
		get {
			return _executing
		}
		set {
			willChangeValue(forKey: "isExecuting")
			_executing = newValue
			didChangeValue(forKey: "isExecuting")
		}
	}

	open override var isAsynchronous: Bool {
		return true
	}

	open override func main() { }

	open override func start() {
		guard !isCancelled else {
			lastError = NSError.errorWithCause(.cancelled)
			callOnComplete()
			return
		}

		isExecuting = true
		isFinished = false

		if preRun() {
			if let session = createSession() {
				doRun(session: session)
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

	open override func callOnComplete() {
		self.isFinished = true
		self.isExecuting = false

		super.callOnComplete()
	}
}
