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


public typealias CacheStrategy = (
	ServerOperation,
	whenSuccess: () -> (),
	whenFailure: NSError -> ()) -> ()


public class ServerOperationInteractor: Interactor {

	public var cacheStrategy = CacheStrategyType.RemoteFirst


	override public func start() -> Bool {
		if let operation = createOperation() {
			getCacheStrategyImpl(cacheStrategy)(
				operation,
				whenSuccess: {
					operation.lastError = nil
					self.completedOperation(operation)
					self.callOnSuccess()
				},
				whenFailure: {
					self.completedOperation(operation)
					self.callOnFailure($0)
				})

			return true
		}

		return false
	}


	public func createOperation() -> ServerOperation? {
		return nil
	}

	public func completedOperation(op: ServerOperation) {
	}

	public func readFromCache(op: ServerOperation, result: AnyObject? -> Void) {
		result(nil)
	}

	public func writeToCache(op: ServerOperation) {
	}

	public func getCacheStrategyImpl(strategyType: CacheStrategyType) -> CacheStrategy {
		return defaultStrategyRemote
	}


	//MARK: Default strategy implementations

	public func defaultStrategyRemote(
			operation: ServerOperation,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {

		let validationError = operation.validateAndEnqueue() {
			if let error = $0.lastError {
				whenFailure(error.domain == "NSURLErrorDomain"
					? NSError.errorWithCause(.NotAvailable)
					: error)
			}
			else {
				whenSuccess()
			}
		}

		if let validationError = validationError {
			whenFailure(validationError)
		}
	}

	public func defaultStrategyReadFromCache(
			operation: ServerOperation,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {
		self.readFromCache(operation) {
			if $0 != nil {
				whenSuccess()
			}
			else {
				whenFailure(NSError.errorWithCause(.NotAvailable))
			}
		}
	}

	public func defaultStrategyWriteToCache(
			operation: ServerOperation,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {
		self.writeToCache(operation)
		whenSuccess()
	}


	//MARK: Strategy builders

	public func createStrategy(
			whenFails mainStrategy: CacheStrategy,
			then onFailStrategy: CacheStrategy) -> CacheStrategy {

		return { (operation: ServerOperation,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			mainStrategy(operation,
				whenSuccess: whenSuccess,
				whenFailure: { err -> () in
					if err.code == ScreensErrorCause.NotAvailable.rawValue {
						onFailStrategy(operation,
							whenSuccess: whenSuccess,
							whenFailure: whenFailure)
					}
					else {
						whenFailure(err)
					}
				})
		}
	}

	public func createStrategy(
			whenSucceeds mainStrategy: CacheStrategy,
			then onSuccessStrategy: CacheStrategy) -> CacheStrategy {

		return { (operation: ServerOperation,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			mainStrategy(operation,
				whenSuccess: {
					onSuccessStrategy(operation,
						whenSuccess: whenSuccess,
						whenFailure: whenFailure)
				},
				whenFailure: whenFailure)
		}
	}

}