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

#if LIFERAY_SCREENS_FRAMEWORK
	import LRMobileSDK
#endif


public struct PlaceholderMapping {
	var className: String?
	var classPK: Int64?
	var customContent: String?
	var priority: Int

	init?(className: String?, classPK: Int64?, customContent: String?, priority: Int?) {
		if customContent != nil && (className == nil || classPK == nil) {
			return nil
		}
		if priority == nil {
			return nil
		}

		self.className = className
		self.classPK = classPK
		self.customContent = (customContent ?? "") == "" ? nil : customContent
		self.priority = priority!
	}
}


public class AudienceTargetingLoadPlaceholderOperation: ServerOperation {

	public var appId: String?
	public var groupId: Int64?
	public var placeholderId: String?
	public var userContext: [String : String]?

	public var results: [PlaceholderMapping]?


	//MARK: ServerOperation

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (appId ?? "" != "")
		valid = valid && (groupId != nil)
		valid = valid && (placeholderId  ?? "" != "")
		valid = valid && !(userContext ?? [:]).isEmpty

		return valid
	}

	override internal func doRun(#session: LRSession) {
		let service = LRScreensmobileService_v62(session: session)

		lastError = nil

		let result = service.getContentWithAppId(appId!,
				groupId: groupId!,
				placeholderId: placeholderId!,
				userContext: userContext!,
				serviceContext: nil,
				error: &lastError)

		if lastError == nil {
			results = []

			let resultList = result as! [[String:AnyObject]]
			for content in resultList {
				if let placeholderMap = PlaceholderMapping(
						className: content["className"] as? String,
						classPK: content["classPK"].map { $0 as! Int }.map { Int64($0) },
						customContent: content["customContent"] as? String,
						priority: content["campaignId"] as? Int) {
					results?.append(placeholderMap)
				}
				else {
					println("Wrong audience targeting mapping: \(content)")
				}
			}

			if results?.count > 1 {
				results?.sort { $0.priority > $1.priority }
			}
		}
	}

}
