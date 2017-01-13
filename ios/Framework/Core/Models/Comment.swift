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


@objc public class Comment: NSObject, NSCoding {

	public static func plainBodyToHtml(plainBody: String) -> String {
		return plainBody
			.stringByReplacingOccurrencesOfString("<", withString: "&lt;")
			.stringByReplacingOccurrencesOfString(">", withString: "&gt;")
			.characters
			.split("\n")
			.map(String.init)
			.map { "<p>\($0)</p>" }
			.joinWithSeparator("")
	}

	private let AllowedTags = ["strong", "i", "b", "a"]

	public let attributes: [String:AnyObject]

	public var originalBody: String {
		return attributes["body"]!.description
			.stringByReplacingOccurrencesOfString("\n ", withString: "")
			.stringByReplacingOccurrencesOfString("\n", withString: "")
			.stringByReplacingOccurrencesOfString("</p><p>", withString: "\n")
			.stringByReplacingOccurrencesOfString("<p>", withString: "")
			.stringByReplacingOccurrencesOfString("</p>", withString: "")
	}

	public var plainBody: String {
		return originalBody
			.stringByReplacingOccurrencesOfString(
				"<[^>]+>",
				withString: "",
				options: .RegularExpressionSearch,
				range: nil)
			.stringByReplacingOccurrencesOfString("&lt;", withString: "<")
			.stringByReplacingOccurrencesOfString("&gt;", withString: ">")
	}

	public var htmlBody: String {
		let closeTags = AllowedTags.map { "/\($0)" }
		let allTags = closeTags + AllowedTags

		return originalBody
			.stringByReplacingOccurrencesOfString(
				"(?i)<(?!\(allTags.joinWithSeparator("|"))).*?>",
				withString: "",
				options: .RegularExpressionSearch,
				range: nil)
			.stringByReplacingOccurrencesOfString("\n", withString: "</br>")
	}

	public var isStyled: Bool {
		return originalBody.containsString("<")
	}

	public var commentId: Int64 {
		return (attributes["commentId"]! as! NSNumber).longLongValue
	}

	public var userName: String {
		return attributes["userName"]!.description
	}

	public var userId: Int64 {
		return (attributes["userId"]! as! NSNumber).longLongValue
	}

	public var createDate: NSDate {
		let milliseconds = (attributes["createDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
	}

	public var modifiedDate: NSDate {
		let milliseconds = (attributes["modifiedDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
	}

	public var canDelete: Bool {
		return attributes["deletePermission"] as? Bool ?? false
	}

	public var canEdit: Bool {
		return attributes["updatePermission"] as? Bool ?? false
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(self.attributes, forKey:"comment-attrs")
	}

	//MARK: Initializers

	public init(attributes: [String:AnyObject]) {
		self.attributes = attributes

		super.init()
	}

	public required init?(coder aDecoder: NSCoder) {
		self.attributes = aDecoder.decodeObjectForKey("comment-attrs") as? [String:AnyObject] ?? [:]

		super.init()
	}
}
