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
		if let blogsEntry = blogsEntry {
			return blogsEntry["blogId"]!.description.asLong!
		}
		return 0
	}

	public var subtitle: String {
		if let blogsEntry = blogsEntry {
			return blogsEntry["subtitle"]!.description
		}
		return ""
	}

	public var userName: String {
		if let blogsEntry = blogsEntry {
			return blogsEntry["userName"]!.description
		}
		return ""
	}

	public var displayDate: Int64 {
		if let blogsEntry = blogsEntry {
			return blogsEntry["displayDate"]!.description.asLong!
		}
		return 0
	}

	public var content: String {
		if let blogsEntry = blogsEntry {
			return blogsEntry["content"]!.description
		}
		return ""
	}

	public var userId: Int64 {
		if let blogsEntry = blogsEntry {
			return blogsEntry["userId"]!.description.asLong!
		}
		return 0
	}

	public var coverImageFileEntryId: Int64 {
		if let blogsEntry = blogsEntry {
			return blogsEntry["coverImageFileEntryId"]!.description.asLong!
		}
		return 0
	}
}
