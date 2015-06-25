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


@objc public class AudienceTargetingLoader : NSObject {

	private var groupId: Int64
	private var appId: String

	private var contentCache: [String: String] = [:]

	public init(groupId: Int64, appId: String) {
		self.groupId = groupId
		self.appId = appId

		super.init()
	}

	public class func computeUserContext() -> [String:String] {
		var result = [String:String]()

		if SessionContext.hasSession {
			result["userId"] = (SessionContext.userAttribute("userId") as! Int).description
		}

		// device
		result["os-name"] = "ios"
		result["os-version"] = NSProcessInfo.processInfo().operatingSystemVersionString

		result["locale"] = NSLocale.currentLocaleString

		// more...

		return result
	}

	public func clearCache() {
		contentCache.removeAll(keepCapacity: true)
	}

	public func hasContentCached(#placeholderId: String) -> Bool {
		return contentCache[placeholderId] != nil
	}

	public func content(#placeholderId: String,
			result: (String?, NSError?) -> Void) {

		if let cachedValue = contentCache[placeholderId] {
			result(cachedValue, nil)
		}
		else {
			loadContent(placeholderId: placeholderId, result: result)
		}
	}

	public func loadContent(
			#placeholderId: String,
			result: (String?, NSError?) -> Void) {

		let operation = AudienceTargetingLoadPlaceholderOperation()

		operation.groupId = (groupId != 0) ? groupId : LiferayServerContext.groupId

		operation.appId = appId
		operation.placeholderId = placeholderId

		operation.userContext = AudienceTargetingLoader.computeUserContext()

		// TODO retain-cycle on operation?
		operation.onComplete = {
			if let error = $0.lastError {
				result(nil, error)
			}
			else {
				let loadOp = $0 as! AudienceTargetingLoadPlaceholderOperation

				if let customContent = loadOp.results?.first?.customContent {
					self.contentCache[loadOp.placeholderId!] = customContent
					result(customContent, nil)
				}
				else {
					// no error, no content
					result(nil, nil)
				}
			}
		}

		if !operation.validateAndEnqueue() {
			result(nil, NSError.errorWithCause(.AbortedDueToPreconditions))
		}
	}

}
