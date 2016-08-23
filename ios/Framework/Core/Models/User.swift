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


//TODO if we have an User model, we have to use in the SessionContext object 
// (instead of "userAttributes" property)
@objc public class User: Asset {

	public var user: [String:AnyObject]? {
		return attributes["object"]?["user"] as? [String:AnyObject]
	}

	public var firstName: String {
		return stringValue("firstName") ?? ""
	}

	public var lastName: String {
		return stringValue("lastName") ?? ""
	}

	public var middleName: String {
		return stringValue("middleName") ?? ""
	}

	public var screenName: String {
		return stringValue("screenName") ?? ""
	}

	public var greeting: String {
		return stringValue("greeting") ?? ""
	}

	public var jobTitle: String {
		return stringValue("jobTitle") ?? ""
	}

	public var email: String {
		return stringValue("emailAddress") ?? ""
	}

	public var userId: Int64 {
		return int64Value("userId") ?? 0
	}

	private func int64Value(key: String) -> Int64? {
		return user?[key]?.description.asLong
	}

	private func stringValue(key: String) -> String? {
		return user?[key]?.description
	}
}
