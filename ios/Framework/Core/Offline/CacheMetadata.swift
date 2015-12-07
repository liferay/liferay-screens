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
import YapDatabase


@objc public class CacheMetadata : NSObject, NSCoding {

	public let synchronized: NSDate?
	public let attributes: [String:AnyObject]

	public init(synchronized: NSDate?, attributes: [String:AnyObject]) {
		self.synchronized = synchronized
		self.attributes = attributes

		super.init()
	}

	public required convenience init?(coder aDecoder: NSCoder) {
		let synchronized = aDecoder.decodeObjectForKey("sync_date") as? NSDate
		let attributes = (aDecoder.decodeObjectForKey("attributes") as? [String:AnyObject]) ?? [:]

		self.init(synchronized: synchronized, attributes: attributes)
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(synchronized, forKey:"sync_date")
		aCoder.encodeObject(attributes, forKey:"attributes")
	}

}
