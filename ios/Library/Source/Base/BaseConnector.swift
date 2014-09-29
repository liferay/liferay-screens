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

	internal var anonymousAuth: AnonymousAuth? {
		return widget as? AnonymousAuth
	}

	internal var onComplete: (BaseConnector -> Void)?


	init(widget: BaseWidget) {
		self.widget = widget

		super.init()
	}

	func enqueue(onComplete: (BaseConnector -> Void)? = nil) {
		if onComplete != nil {
			self.onComplete = onComplete
		}

		LiferayConnectorsQueue.addConnector(self)
	}

	internal override func main() {
		if let anonymousAuthValue = anonymousAuth {
			if anonymousAuthValue.anonymousApiUserName == nil ||
					anonymousAuthValue.anonymousApiPassword == nil {

				println("ERROR: The credentials to use for anonymous API calls must be set " +
						"in order to use this widget")

				return
			}
		}

		if preRun() {
			doRun()
			postRun()

			if self.onComplete != nil {
				dispatch_async(dispatch_get_main_queue()) {
					self.onComplete!(self)
				}
			}
		}
	}

	internal func preRun() -> Bool {
		return false
	}

	internal func doRun() {
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
