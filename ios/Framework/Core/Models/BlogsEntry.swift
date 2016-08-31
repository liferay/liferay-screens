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
import Foundation


@objc public class BlogsEntry: Asset {

	public var blogsEntry: [String:AnyObject]? {
		return attributes["object"]?["blogsEntry"] as? [String:AnyObject]
	}

	public var blogId: Int64 {
		return int64Value("blogId") ?? 0
	}

	public var subtitle: String {
		return stringValue("subtitle") ?? ""
	}

	public var userName: String {
		return stringValue("userName") ?? ""
	}

	public var displayDate: NSDate? {
		guard let value = int64Value("displayDate") else {
			return nil
		}

		let timeStamp = NSTimeInterval(value)/1000.0
		return NSDate(timeIntervalSince1970: timeStamp)
	}

	public var content: String {
		return stringValue("content") ?? ""
	}

	public var userId: Int64 {
		return int64Value("userId") ?? 0
	}

	public var coverImageFileEntryId: Int64 {
		return int64Value("coverImageFileEntryId") ?? 0
	}

	// MARK: MimeTypeable

	override public var mimeType: String? {
		return "text/html"
	}

	private func int64Value(key: String) -> Int64? {
		return blogsEntry?[key]?.description.asLong
	}

	private func stringValue(key: String) -> String? {
		return blogsEntry?[key]?.description
	}

}
