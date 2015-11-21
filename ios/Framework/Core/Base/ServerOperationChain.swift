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


@objc public class ServerOperationChain: ServerOperation {

	private struct StreamOperationsQueue {

		static private var queue: NSOperationQueue?

		static func addOperation(operation: NSOperation) {
			if queue == nil {
				queue = NSOperationQueue()
				queue!.maxConcurrentOperationCount = 1
			}

			queue!.addOperation(operation)
		}

	}

	public var onNextStep: ((ServerOperation, Int) -> ServerOperation?)?

	public let headOperation: ServerOperation
	public var currentOperation: ServerOperation


	public init(head: ServerOperation) {
		headOperation = head
		currentOperation = head

		super.init()
	}


	//MARK: ServerOperation methods

	override public func createSession() -> LRSession? {
		return headOperation.createSession()
	}

	override public func enqueue(onComplete: (ServerOperation -> Void)?) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		StreamOperationsQueue.addOperation(self)
	}

	private func doStep(
			number: Int,
			_ op: ServerOperation,
			_ waitGroup: dispatch_group_t) -> ValidationError? {

		let originalCallback = op.onComplete

		return op.validateAndEnqueue { operation in
			self.lastError = operation.lastError ?? self.lastError

			originalCallback?(operation)

			if let nextOp = self.onNextStep?(operation, number) {
				let validationError = self.doStep(number + 1, nextOp, waitGroup)

				if let validationError = validationError {
					self.lastError = validationError
					dispatch_group_leave(waitGroup)
				}
				else {
					self.currentOperation = nextOp
				}
			}
			else {
				dispatch_group_leave(waitGroup)
			}
		}
	}

	override public func doRun(session session: LRSession) {
		let waitGroup = dispatch_group_create()

		dispatch_group_enter(waitGroup)

		if let validationError = doStep(0, headOperation, waitGroup) {
			self.lastError = validationError
		}

		dispatch_group_wait(waitGroup, DISPATCH_TIME_FOREVER)
	}

	override public func callOnComplete() {
		super.callOnComplete()

		self.onNextStep = nil
	}

}
