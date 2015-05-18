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

	public var onSuccess: (Void -> Void)?
	public var onFailure: (NSError -> Void)?

	public let screenlet: BaseScreenlet

	public init(screenlet: BaseScreenlet) {
		self.screenlet = screenlet
	}

	public func callOnSuccess() {
		if NSThread.isMainThread() {
			onSuccess?()
			finish()
		}
		else {
			dispatch_async(dispatch_get_main_queue()) {
				self.onSuccess?()
				self.finish()
			}
		}
	}

	public func callOnFailure(error: NSError) {
		if NSThread.isMainThread() {
			onFailure?(error)
			finish()
		}
		else {
			dispatch_async(dispatch_get_main_queue()) {
				self.onFailure?(error)
				self.finish()
			}
		}
	}

	public func start() -> Bool {
		return false
	}

	private func finish() {
		screenlet.endInteractor(self)

		// break retain cycle
		onSuccess = nil
		onFailure = nil
	}

}
