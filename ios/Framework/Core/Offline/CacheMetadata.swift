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

	public init(received: NSDate?, sent: NSDate?) {
		self.received = received
		self.sent = sent

		super.init()
	}

	public required convenience init(coder aDecoder: NSCoder) {
		let received = aDecoder.decodeObjectForKey("received_date") as? NSDate
		let sent = aDecoder.decodeObjectForKey("sent_date") as? NSDate

		self.init(received: received, sent: sent)
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(received, forKey:"received_date")
		aCoder.encodeObject(sent, forKey:"sent_date")
	}

}
