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


public class HttpDownloadConnector: ServerConnector {

	public var url: NSURL
	public var resultUrl: NSURL?

	public init(url: NSURL) {
		self.url = url

		super.init()
	}

	//MARK: ServerConnector

	override public func doRun(session session: LRSession) {
		let session = NSURLSession(configuration: NSURLSessionConfiguration.defaultSessionConfiguration())

		let requestSemaphore = dispatch_semaphore_create(0)

		session.downloadTaskWithURL(self.url, completionHandler:
			{ (_localURL, response, error) in
				guard let localURL = _localURL else {
					self.lastError = NSError.errorWithCause(.InvalidServerResponse)
					self.resultUrl = nil
					return
				}

				if let error = error {
					self.lastError = error
					self.resultUrl = nil
				}
				else {
					do {
						let newPathURL = try self.moveTmpToCache(localURL.absoluteString,
							fileExtension: self.fileExtension(response))
						self.resultUrl = newPathURL
						self.lastError = nil
					}
					catch let error as NSError {
						self.lastError = error
						self.resultUrl = nil
					}
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


	private func moveTmpToCache(localPath: String, fileExtension: String) throws -> NSURL {
		let cachePath = cacheFilePath()
		let cachePathUrl = NSURL(fileURLWithPath: cachePath + "." + fileExtension)

		try NSFileManager.defaultManager().moveItemAtURL(NSURL(string: localPath)!, toURL: cachePathUrl)

		return cachePathUrl
	}

	private func fileExtension(response: NSURLResponse?) -> String {
		if let ext = response?.MIMEType?.characters.split("/").map(String.init)[1] {
			return ext
		}

		if let ext = response?.suggestedFilename?.characters.split(".").map(String.init).last {
			return ext
		}

		return "tmp"
	}
}
