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

	public var currentOperation: ServerOperation?


	override public func start() -> Bool {
		self.currentOperation = createOperation()

		if let currentOperation = self.currentOperation {
			getCacheStrategyImpl(cacheStrategy)(
				currentOperation,
				whenSuccess: {
					self.completedOperation(currentOperation)
					self.callOnSuccess()
				},
				whenFailure: { err in
					currentOperation.lastError = err
					self.completedOperation(currentOperation)
					self.callOnFailure(err)
				})

			return true
		}

		self.callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))

		return false
	}

	override public func cancel() {
		currentOperation?.cancel()
		cancelled = true
	}


	public func createOperation() -> ServerOperation? {
		return nil
	}

	public func completedOperation(op: ServerOperation) {
	}

	override public func callOnSuccess() {
		super.callOnSuccess()
		currentOperation = nil
	}

	override public func callOnFailure(error: NSError) {
		super.callOnFailure(error)
		currentOperation = nil
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
				if error.domain == "NSURLErrorDomain" {
					$0.lastError = NSError.errorWithCause(.NotAvailable)
				}
				whenFailure($0.lastError!)
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

		// the closure is called before because it fires the 
		// "completedOperation" method and it should be run
		// before the write
		whenSuccess()
		self.writeToCache(operation)
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

	public func createStrategy(
			firstStrategy: CacheStrategy,
			andThen secondStrategy: CacheStrategy) -> CacheStrategy {

		return { (operation: ServerOperation,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			firstStrategy(operation,
				whenSuccess: {
					secondStrategy(operation,
						whenSuccess: whenSuccess,
						whenFailure: whenFailure)
				},
				whenFailure: { err -> () in
					if err.code == ScreensErrorCause.NotAvailable.rawValue {
						secondStrategy(operation,
							whenSuccess: whenSuccess,
							whenFailure: whenFailure)
					}
					else {
						whenFailure(err)
					}
				})
			}
	}

}