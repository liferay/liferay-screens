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


@objc public class ChainedServerOperations: NSObject {

	public var onCompletedStep: (ServerOperation -> Void)?
	public var onCompleted: (NSError? -> Void)?

	internal var operations = [ServerOperation]()
	internal var originalCallbacks: [ServerOperation : (ServerOperation) -> ()] = [:]

	internal var lastError: NSError?


	//MARK: Public methods

	public func addChainedOperation(op: ServerOperation) {
		if let lastOp = operations.last {
			op.addDependency(lastOp)
		}

		operations.append(op)
	}

	public func validateAndEnqueueAll() -> [ServerOperation : ValidationError]? {
		let errors = operations.toDictionary {
			(op) -> (op: ServerOperation, error: ValidationError)? in

			if let validationError = op.validateData() {
				return (op, validationError)
			}

			return nil
		}

		if !errors.isEmpty {
			return errors
		}

		for op in operations {
			if let originalCallback = op.onComplete {
				originalCallbacks[op] = originalCallback
			}

			op.onComplete = { operation in
				self.lastError = operation.lastError ?? self.lastError

				dispatch_main {
					self.finishStep(operation)

					if op == self.operations.last {
						self.finishChain()
					}
				}
			}

			op.enqueue()
		}

		return [:]
	}


	//MARK: Private methods

	private func finishStep(op: ServerOperation) {
		originalCallbacks[op]?(op)
		originalCallbacks.removeValueForKey(op)

		onCompletedStep?(op)
	}

	private func finishChain() {
		onCompleted?(self.lastError)

		onCompletedStep = nil
		onCompleted = nil

		originalCallbacks.removeAll(keepCapacity: true)
	}

}
