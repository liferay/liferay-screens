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


@objc public class Comment : NSObject, NSCoding {

	public let attributes :[String:AnyObject]

	public var body: String {
		return attributes["body"]!.description
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		let keys = Array(self.attributes.keys)

		aCoder.encodeObject(keys, forKey:"comment-attr-keys")

		for (k,v) in self.attributes {
			if let coderValue = v as? NSCoder {
				aCoder.encodeObject(coderValue, forKey:"comment-attr-\(k)")
			}
		}
	}

	//MARK: Init

	public init(attributes: [String:AnyObject]) {
		self.attributes = attributes
	}

	public required init?(coder aDecoder: NSCoder) {
		let keys = (aDecoder.decodeObjectForKey("comment-attr-keys") as? [String]) ?? [String]()

		var attrs = [String:AnyObject]()

		for k in keys {
			if let v = aDecoder.decodeObjectForKey("comment-attr-\(k)") {
				attrs[k] = v
			}
		}

		self.attributes = attrs

		super.init()
	}
}