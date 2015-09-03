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
	whenFailure: NSError -> ()) -> Bool


public class ServerOperationInteractor: Interactor {

	public var cacheStrategy = CacheStrategyType.OnlineFirst


	override public func start() -> Bool {
		if let operation = createOperation() {
			let strategyImpl = getCacheStrategyImpl(cacheStrategy)
			return strategyImpl(operation,
				whenSuccess: { () -> () in
					self.completedOperation(operation)
					self.callOnSuccess()
				},
				whenFailure: { (err: NSError) -> () in
					self.completedOperation(operation)
					self.callOnFailure(err)
				})
		}

		self.callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))

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

}


public extension ServerOperationInteractor {

	public func getCacheStrategyImpl(strategyType: CacheStrategyType) -> CacheStrategy {
		switch strategyType {
		case .OnlineOnly:
			return onlineOnlyStrategy

		case .CacheOnly:
			return cacheOnlyStrategy

		case .OnlineFirst:
			return createOnlineFirstStrategy()

		case .CacheFirst:
			return createCacheFirstStrategy()
		}
	}

	private func onlineOnlyStrategy(
			operation: ServerOperation,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) -> Bool {

		let validationError = operation.validateAndEnqueue() {
			if let error = $0.lastError {
				whenFailure(error.domain == "NSURLErrorDomain"
					? NSError.errorWithCause(.NotAvailable,
						message: error.localizedDescription)
					: error)
			}
			else {
				self.writeToCache(operation)
				whenSuccess()
			}
		}

		if let validationError = validationError {
			whenFailure(validationError)
		}

		return (validationError == nil)
	}

	private func cacheOnlyStrategy(
			operation: ServerOperation,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) -> Bool {

		self.readFromCache(operation) {
			if $0 != nil {
				whenSuccess()
			}
			else {
				whenFailure(NSError.errorWithCause(.NotAvailable))
			}
		}

		return true
	}

	private func createOnlineFirstStrategy() -> CacheStrategy {
		return createTwoPhaseStrategy(
			onlineOnlyStrategy,
			cacheOnlyStrategy)
	}

	private func createCacheFirstStrategy() -> CacheStrategy {
		return createTwoPhaseStrategy(
			cacheOnlyStrategy,
			onlineOnlyStrategy)
	}

	private func createTwoPhaseStrategy(
			mainStrategy: CacheStrategy,
			_ backupStrategy: CacheStrategy) -> CacheStrategy {

		return { (operation: ServerOperation,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> Bool in
			return mainStrategy(operation,
				whenSuccess: whenSuccess,
				whenFailure: { err -> () in
					if err.code == ScreensErrorCause.NotAvailable.rawValue {
						backupStrategy(operation,
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