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


@objc public class Asset : NSObject, NSCoding, MimeTypeable {

	public let attributes :[String:AnyObject]

	public let title: String


	//MARK: MimeTypeable

	public var mimeType: String? {
		return attributes["mimeType"]?.description
	}

	override public var description: String {
		return attributes["description"] as! String
	}

	public var classNameId: Int64 {
		return attributes["classNameId"]!.longLongValue
	}

	public var classPK: Int64 {
		return attributes["classPK"]!.longLongValue
	}

	public var groupId: Int64 {
		return attributes["groupId"]!.longLongValue
	}

	public var companyId: Int64 {
		return attributes["companyId"]!.longLongValue
	}

	public var url: String {
		return attributes["url"]!.description!
	}

	public var entryId: Int64 {
		return attributes["entryId"]!.longLongValue!
	}

	public var createDate: NSDate {
		let milliseconds = (attributes["createDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
	}

	public var modifiedDate: NSDate {
		let milliseconds = (attributes["modifiedDate"]! as! NSNumber).doubleValue
		return NSDate(millisecondsSince1970: milliseconds)
	}

	override public var debugDescription: String {
		return attributes.debugDescription
	}

	//MARK: Initializers

	public init(attributes: [String:AnyObject]) {
		self.attributes = attributes

		let xmlTitle = attributes["title"] as! String
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))

		super.init()
	}

	public required init?(coder aDecoder: NSCoder) {
		self.attributes = aDecoder.decodeObjectForKey("asset-attrs") as? [String:AnyObject] ?? [:]

		let xmlTitle = attributes["title"] as! String
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))

		super.init()
	}


	//MARK: Public methods

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(attributes, forKey: "asset-attrs")
	}
	
}
