//
//  BaseConnector.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 28/09/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit


enum LiferayConnectorsQueue {

	static var queue: NSOperationQueue?

	static func addConnector(connector: BaseConnector) {
		if queue == nil {
			queue = NSOperationQueue()
			queue!.maxConcurrentOperationCount = 1
			queue!.qualityOfService = .UserInitiated
		}

		queue!.addOperation(connector)
	}

}


class BaseConnector: NSOperation {

	var lastError: NSError?
	var widget: BaseWidget

	internal var onComplete: (BaseConnector -> Void)?

	internal var anonymousAuth: AnonymousAuth? {
		return widget as? AnonymousAuth
	}

	init(widget: BaseWidget) {
		self.widget = widget

		super.init()

		self.name = NSStringFromClass(self.dynamicType)
	}


	func enqueue(onComplete: (BaseConnector -> Void)? = nil) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		LiferayConnectorsQueue.addConnector(self)
	}

	internal override func main() {
		if let anonymousAuthValue = anonymousAuth {
			assert(anonymousAuthValue.anonymousApiUserName != nil,
					"User name required for anonymous API calls")
			assert(anonymousAuthValue.anonymousApiPassword != nil,
					"Password required for anonymous API calls")
		}

		if preRun() {
			var session: LRSession?

			if let anonymousAuthValue = anonymousAuth {
				session = LRSession(
						server: LiferayServerContext.instance.server,
						username: anonymousAuthValue.anonymousApiUserName!,
						password: anonymousAuthValue.anonymousApiPassword!)
			}
			else {
				session = SessionContext.createSessionFromCurrentSession()
				assert(session != nil, "Login required to use this widget")
			}

			doRun(session: session!)

			postRun()

			if self.onComplete != nil {
				dispatch_sync(dispatch_get_main_queue()) {
					self.onComplete!(self)
				}
			}

			if self is NSCopying {
				widget.connector = self.copy() as? BaseConnector
			}
		}
	}

	internal func preRun() -> Bool {
		return false
	}

	internal func doRun(#session: LRSession) {
	}

	internal func postRun() {
	}

	internal func showHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.startOperationWithMessage(message, details: details)
		}
	}

	internal func hideHUD() {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperation()
		}
	}

	internal func hideHUD(#message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperationWithMessage(message, details: details)
		}
	}

	internal func hideHUD(#errorMessage: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.showHUDWithMessage(errorMessage,
					details: details,
					closeMode:.NoAutoclose(true),
					spinnerMode:.NoSpinner)
		}
	}

	internal func hideHUD(#error: NSError, message: String, details: String? = nil) {
		dispatch_async(dispatch_get_main_queue()) {
			self.widget.finishOperationWithError(error, message: message, details: details)
		}
	}

}
