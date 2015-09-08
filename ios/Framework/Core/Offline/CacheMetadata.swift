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

	public let received: NSDate?
	public let sent: NSDate?
	public let attributes: [String:AnyObject]?

	public init(received: NSDate?, sent: NSDate?, attributes: [String:AnyObject]?) {
		self.received = received
		self.sent = sent
		self.attributes = attributes

		super.init()
	}

	public required convenience init(coder aDecoder: NSCoder) {
		let received = aDecoder.decodeObjectForKey("received_date") as? NSDate
		let sent = aDecoder.decodeObjectForKey("sent_date") as? NSDate
		let attributes = aDecoder.decodeObjectForKey("attributes") as? [String:AnyObject]

		self.init(received: received, sent: sent, attributes: attributes)
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(received, forKey:"received_date")
		aCoder.encodeObject(sent, forKey:"sent_date")
		aCoder.encodeObject(attributes, forKey:"attributes")
	}

	public func mergedMetadata(#received: NSDate?, sent: NSDate?, attributes: [String:AnyObject]?) -> CacheMetadata {

		return CacheMetadata(
			received: received ?? self.received,
			sent: sent ?? self.sent,
			attributes: attributes ?? self.attributes)
	}

}
