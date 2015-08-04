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

public class ServerOperationInteractor: Interactor {

	override public func start() -> Bool {
		var result = false

		if let operation = createOperation() {
			result = operation.validateAndEnqueue() {
				self.completedOperation(operation)

				if let error = $0.lastError {
					if error.domain == "NSURLErrorDomain" {
						self.readFromCache(operation) {
							self.completedOperation(operation)

							if let value = $0 {
								self.callOnSuccess()
							}
							else {
								self.callOnFailure(error)
							}
						}
					}
					else {
						self.callOnFailure(error)
					}
				}
				else {
					self.writeToCache(operation)
					self.callOnSuccess()
				}
			}
		}
		else {
			self.callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))
		}

		return result
	}


	public func createOperation() -> ServerOperation? {
		return nil
	}

	public func completedOperation(op: ServerOperation) {
	}

	public func readFromCache(op: ServerOperation, result: String? -> Void) {
		result(nil)
	}

	public func writeToCache(op: ServerOperation) {
	}

}
