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



public class HttpConnector: ServerConnector {

	public var url: NSURL
	public var resultData: NSData?

	public init(url: NSURL) {
		self.url = url

		super.init()
	}


	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())

		let requestSemaphore = dispatch_semaphore_create(0)

		session.dataTaskWithURL(self.url, completionHandler:
		{ (data, response, error) -> Void in
			if let error = error {
				self.lastError = error
				self.resultData = nil
			}
			else {
				self.resultData = data
				self.lastError = nil
			}
			dispatch_semaphore_signal(requestSemaphore)

		}).resume()

		dispatch_semaphore_wait(requestSemaphore, DISPATCH_TIME_FOREVER)
	}

	override public func createSession() -> LRSession? {
		// dummy session: won't be used
		let port = (url.port == nil) ? "" : ":\(url.port!)"
		return LRSession(server: "http://\(url.host!)\(port)")
	}

}
