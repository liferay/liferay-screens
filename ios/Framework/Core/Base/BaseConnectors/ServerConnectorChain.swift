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


@objc public class ServerConnectorChain: ServerConnector {

	private struct StreamConnectorsQueue {

		static private var queue: NSOperationQueue?

		static func addConnector(connector: NSOperation) {
			if queue == nil {
				queue = NSOperationQueue()
				queue!.maxConcurrentOperationCount = 1
			}

			queue!.addOperation(connector)
		}

	}

	public var onNextStep: ((ServerConnector, Int) -> ServerConnector?)?

	public let headConnector: ServerConnector
	public var currentConnector: ServerConnector


	public init(head: ServerConnector) {
		headConnector = head
		currentConnector = head

		super.init()
	}


	//MARK: ServerConnector methods

	override public func createSession() -> LRSession? {
		return headConnector.createSession()
	}

	override public func enqueue(onComplete: (ServerConnector -> Void)?) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		StreamConnectorsQueue.addConnector(self)
	}

	private func doStep(
			number: Int,
			_ op: ServerConnector,
			_ waitGroup: dispatch_group_t) -> ValidationError? {

		let originalCallback = op.onComplete

		return op.validateAndEnqueue { connector in
			self.lastError = connector.lastError ?? self.lastError

			originalCallback?(connector)

			if let nextOp = self.onNextStep?(connector, number) {
				let validationError = self.doStep(number + 1, nextOp, waitGroup)

				if let validationError = validationError {
					self.lastError = validationError
					dispatch_group_leave(waitGroup)
				}
				else {
					self.currentConnector = nextOp
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

		if let validationError = doStep(0, headConnector, waitGroup) {
			self.lastError = validationError
		}

		dispatch_group_wait(waitGroup, DISPATCH_TIME_FOREVER)
	}

	override public func callOnComplete() {
		super.callOnComplete()

		self.onNextStep = nil
	}

}
