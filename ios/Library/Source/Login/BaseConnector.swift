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

	static func addConnector(connector: BaseConnector, onComplete: BaseConnector -> Void) {
		if queue == nil {
			queue = NSOperationQueue()
			queue!.maxConcurrentOperationCount = 1
			queue!.qualityOfService = .UserInitiated
		}

		connector.completionBlock = {
			dispatch_async(dispatch_get_main_queue()) {
				onComplete(connector)
			}
		}

		queue!.addOperation(connector)
	}

}


class BaseConnector: NSOperation {

	var lastError: NSError?
	var session: LRSession?
	var widget: BaseWidget

	init(widget: BaseWidget, session: LRSession?) {
		self.widget = widget
		self.session = session

		super.init()
	}

	func addToQueue(onComplete: BaseConnector -> Void) {
		LiferayConnectorsQueue.addConnector(self, onComplete: onComplete)
	}

}
