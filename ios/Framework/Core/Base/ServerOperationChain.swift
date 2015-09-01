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

	public var onCompleteStep: (ServerOperation -> Void)?

	public subscript(i: Int) -> ServerOperation? {
		return operations[i]
	}

	public var last: ServerOperation? {
		return operations.last
	}

	internal var operations = [ServerOperation]()
	internal var originalCallbacks: [ServerOperation : (ServerOperation) -> ()] = [:]


	//MARK: Public methods

	public func addChainedOperation(op: ServerOperation) {
		if let lastOp = operations.last {
			op.addDependency(lastOp)
		}

		operations.append(op)
	}


	//MARK: ServerOperation methods

	override func createSession() -> LRSession? {
		// dummy session: won't be used
		return LRSession(server: "")
	}

	override func validateData() -> ValidationError? {
		return
			operations.map {
				$0.validateData()
			}.filter {
				$0 != nil
			}.map {
				return $0!
			}.first
	}

	override func doRun(#session: LRSession) {
		let waitGroup = dispatch_group_create()

		dispatch_group_enter(waitGroup)

		for op in operations {
			if let originalCallback = op.onComplete {
				originalCallbacks[op] = originalCallback
			}

			op.onComplete = { operation in
				self.lastError = operation.lastError ?? self.lastError

				dispatch_main {
					self.finishStep(operation)

					if op == self.operations.last {
						dispatch_group_leave(waitGroup)
					}
				}
			}

			op.enqueue()
		}

		dispatch_group_wait(waitGroup, DISPATCH_TIME_FOREVER)

		dispatch_main {
			self.finishChain()
		}
	}

	//MARK: Private methods

	private func finishStep(op: ServerOperation) {
		originalCallbacks[op]?(op)
		originalCallbacks.removeValueForKey(op)

		onCompleteStep?(op)
	}

	private func finishChain() {
		onComplete?(self)

		onCompleteStep = nil
		onComplete = nil

		originalCallbacks.removeAll(keepCapacity: true)
	}

}
