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

open class HttpDownloadConnector: AsyncServerConnector {

	open var url: URL

	open var resultUrl: URL?

	// MARK: Initializers

	public init(url: URL) {
		self.url = url

		super.init()
	}

	// MARK: AsyncServerConnector

	override open func doRun(session: LRSession) {

		let session = URLSession(configuration: .default)

		let urlRequest = NSMutableURLRequest(url: self.url)

		if let auth = SessionContext.currentContext?.session.authentication as? LRCookieAuthentication {
			auth.authenticate(urlRequest)
		}

		session.downloadTask(with: urlRequest as URLRequest, completionHandler: { (_localURL, response, error) in
				if let error = error {
					self.lastError = error as NSError?
					self.resultUrl = nil
				}
				else if _localURL == nil {
					self.lastError = NSError.errorWithCause(.invalidServerResponse,
							message: "Could not download because local URL not found.")
					self.resultUrl = nil
				}
				else if let localURL = _localURL {
					do {
						let newPathURL = try self.moveTmpToCache(localURL.absoluteString,
							fileExtension: self.fileExtension(response))
						self.resultUrl = newPathURL
						self.lastError = nil
						self.callOnComplete()
					}
					catch let error as NSError {
						self.lastError = error
						self.resultUrl = nil
					}
				}
		}).resume()
	}

	// MARK: Private methods

	fileprivate func moveTmpToCache(_ localPath: String, fileExtension: String) throws -> URL {

		let cachePath = cacheFilePath()
		let cachePathUrl = URL(fileURLWithPath: cachePath + "." + fileExtension)

		try FileManager.default.moveItem(at: URL(string: localPath)!,
		                                                 to: cachePathUrl)

		return cachePathUrl
	}

	fileprivate func fileExtension(_ response: URLResponse?) -> String {

		if let ext = response?.suggestedFilename?.split(separator: ".").map(String.init).last {
			return ext
		}

		if let ext = response?.mimeType?.split(separator: "/").map(String.init)[1] {
			return ext
		}

		return "tmp"
	}
}
