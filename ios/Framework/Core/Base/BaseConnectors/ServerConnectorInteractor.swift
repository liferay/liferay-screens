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
	ServerConnector,
	whenSuccess: () -> (),
	whenFailure: NSError -> ()) -> ()


public class ServerConnectorInteractor: Interactor {

	public var cacheStrategy = CacheStrategyType.RemoteFirst

	public var currentConnector: ServerConnector?


	override public func start() -> Bool {
		self.currentConnector = createConnector()

		if let currentConnector = self.currentConnector {
			getCacheStrategyImpl(cacheStrategy)(
				currentConnector,
				whenSuccess: {
					self.completedConnector(currentConnector)
					self.callOnSuccess()
				},
				whenFailure: { err in
					currentConnector.lastError = err
					self.completedConnector(currentConnector)
					self.callOnFailure(err)
				})

			return true
		}

		self.callOnFailure(NSError.errorWithCause(.AbortedDueToPreconditions))

		return false
	}

	override public func cancel() {
		currentConnector?.cancel()
		cancelled = true
	}


	public func createConnector() -> ServerConnector? {
		return nil
	}

	public func completedConnector(op: ServerConnector) {
	}

	override public func callOnSuccess() {
		super.callOnSuccess()
		currentConnector = nil
	}

	override public func callOnFailure(error: NSError) {
		super.callOnFailure(error)
		currentConnector = nil
	}

	public func readFromCache(op: ServerConnector, result: AnyObject? -> Void) {
		result(nil)
	}

	public func writeToCache(op: ServerConnector) {
	}

	public func getCacheStrategyImpl(strategyType: CacheStrategyType) -> CacheStrategy {
		return defaultStrategyRemote
	}


	//MARK: Default strategy implementations

	public func defaultStrategyRemote(
			connector: ServerConnector,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {

		let validationError = connector.validateAndEnqueue() {
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
			connector: ServerConnector,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {
		self.readFromCache(connector) {
			if $0 != nil {
				whenSuccess()
			}
			else {
				whenFailure(NSError.errorWithCause(.NotAvailable))
			}
		}
	}

	public func defaultStrategyWriteToCache(
			connector: ServerConnector,
			whenSuccess: () -> (),
			whenFailure: NSError -> ()) {

		// the closure is called before because it fires the 
		// "completedConnector" method and it should be run
		// before the write
		whenSuccess()
		self.writeToCache(connector)
	}


	//MARK: Strategy builders

	public func createStrategy(
			whenFails mainStrategy: CacheStrategy,
			then onFailStrategy: CacheStrategy) -> CacheStrategy {

		return { (connector: ServerConnector,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			mainStrategy(connector,
				whenSuccess: whenSuccess,
				whenFailure: { err -> () in
					if err.code == ScreensErrorCause.NotAvailable.rawValue {
						onFailStrategy(connector,
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

		return { (connector: ServerConnector,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			mainStrategy(connector,
				whenSuccess: {
					onSuccessStrategy(connector,
						whenSuccess: whenSuccess,
						whenFailure: whenFailure)
				},
				whenFailure: whenFailure)
		}
	}

	public func createStrategy(
			firstStrategy: CacheStrategy,
			andThen secondStrategy: CacheStrategy) -> CacheStrategy {

		return { (connector: ServerConnector,
				whenSuccess: () -> (),
				whenFailure: NSError -> ()) -> () in
			firstStrategy(connector,
				whenSuccess: {
					secondStrategy(connector,
						whenSuccess: whenSuccess,
						whenFailure: whenFailure)
				},
				whenFailure: { err -> () in
					if err.code == ScreensErrorCause.NotAvailable.rawValue {
						secondStrategy(connector,
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