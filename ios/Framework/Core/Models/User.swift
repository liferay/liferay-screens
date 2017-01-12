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


@objc public class User: NSObject, NSCoding {

	public let attributes: [String:AnyObject]
	
	public var firstName: String {
		return stringAttribute("firstName")
	}

	public var lastName: String {
		return stringAttribute("lastName")
	}

	public var middleName: String {
		return stringAttribute("middleName")
	}

	public var screenName: String {
		return stringAttribute("screenName")
	}

	public var greeting: String {
		return stringAttribute("greeting")
	}

	public var jobTitle: String {
		return stringAttribute("jobTitle")
	}

	public var email: String {
		return stringAttribute("emailAddress")
	}

	public var userId: Int64 {
		return int64Attribute("userId")
	}


	//MARK: Initializers

	public init(attributes: [String : AnyObject]) {
		self.attributes = attributes

		super.init()
	}

	public required init?(coder aDecoder: NSCoder) {
		self.attributes = aDecoder.decodeObjectForKey("asset-attrs") as? [String:AnyObject] ?? [:]

		super.init()
	}


	//MARK: Public methods

	public func int64Attribute(key: String) -> Int64 {
		return attributes[key]?.longLongValue ?? 0
	}

	public func stringAttribute(key: String) -> String {
		return attributes[key]?.description ?? ""
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(attributes, forKey: "asset-attrs")
	}
}
