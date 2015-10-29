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


@objc public class Interactor: NSObject {

	public var actionName: String?

	public var onSuccess: (Void -> Void)?
	public var onFailure: (NSError -> Void)?

	public var lastError: NSError?

	public let screenlet: BaseScreenlet?

	internal var cancelled = false


	public init(screenlet: BaseScreenlet?) {
		self.screenlet = screenlet

		super.init()
	}

	override public convenience init() {
		self.init(screenlet: nil)
	}

	public func callOnSuccess() {
		if !cancelled {
			dispatch_main {
				self.onSuccess?()
				self.finishWithError(nil)
			}
		}
	}

	public func callOnFailure(error: NSError) {
		if !cancelled {
			dispatch_main {
				self.onFailure?(error)
				self.finishWithError(error)
			}
		}
	}

	public func start() -> Bool {
		cancelled = false
		return false
	}

	public func cancel() {
		callOnFailure(NSError.errorWithCause(.Cancelled))
		cancelled = true
	}

	public func interactionResult() -> AnyObject? {
		return nil
	}

	private func finishWithError(error: NSError?) {
		screenlet?.endInteractor(self, error: error)

		// break retain cycle
		onSuccess = nil
		onFailure = nil
	}

}
