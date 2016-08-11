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

	private let AllowedTags = ["strong", "i", "b", "a", "/strong", "/i", "/b", "/a"]

	public let attributes :[String:AnyObject]

	public var originalBody: String {
		return attributes["body"]!.description
			.stringByReplacingOccurrencesOfString("\n", withString: "")
			.stringByReplacingOccurrencesOfString("</p><p>", withString: "\n")
			.stringByReplacingOccurrencesOfString("<p>", withString: "")
			.stringByReplacingOccurrencesOfString("</p>", withString: "")
	}

	public var plainBody: String {
		return originalBody.stringByReplacingOccurrencesOfString(
			"<[^>]+>", withString: "", options: .RegularExpressionSearch, range: nil)
	}

	public var htmlBody: String {
		return originalBody.stringByReplacingOccurrencesOfString(
				"(?i)<(?!\(AllowedTags.joinWithSeparator("|"))).*?>", withString: "",
				options: .RegularExpressionSearch, range: nil)
			.stringByReplacingOccurrencesOfString("\n", withString: "</br>")
	}

	public var isStyled: Bool {
		return plainBody != htmlBody.stringByReplacingOccurrencesOfString("</br>", withString: "\n")
	}

	public var commentId: Int64 {
		return (attributes["commentId"]! as! String).asLong!
	}

	public var userName: String {
		return attributes["userName"]!.description
	}

	public var userId: Int64 {
		return (attributes["userId"]! as! String).asLong!
	}

	public var createDate: NSDate {
		let milliseconds = (attributes["createDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
	}

	public var modifiedDate: NSDate {
		let milliseconds = (attributes["modifiedDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
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