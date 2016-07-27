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


@objc public class Asset : NSObject, NSCoding {

	public let attributes :[String:AnyObject]

	public let title: String

	override public var description: String {
		return attributes["description"] as! String
	}

	public var classNameId: Int64 {
		return attributes["classNameId"]!.description.asLong!
	}

	public var classPK: Int64 {
		return attributes["classPK"]!.description.asLong!
	}

	public var groupId: Int64 {
		return attributes["groupId"]!.description.asLong!
	}

	public var companyId: Int64 {
		return attributes["companyId"]!.description.asLong!
	}

	public var url: String {
		return attributes["url"]!.description!
	}

	public var mimeType: String {
		return attributes["mimeType"]!.description!
	}

	public var entryId: Int64 {
		return attributes["entryId"]!.description.asLong!
	}

	override public var debugDescription: String {
		return attributes.debugDescription
	}

	//MARK: Init

	public init(attributes: [String:AnyObject]) {
		self.attributes = attributes

		let xmlTitle = attributes["title"] as! String
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))
	}

	public required init?(coder aDecoder: NSCoder) {
		let keys = (aDecoder.decodeObjectForKey("asset-attr-keys") as? [String]) ?? [String]()

		var attrs = [String:AnyObject]()

		for k in keys {
			if let v = aDecoder.decodeObjectForKey("asset-attr-\(k)") {
				attrs[k] = v
			}
		}

		self.attributes = attrs

		let xmlTitle = (attributes["title"] as? String) ?? ""
		title = xmlTitle.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))

		super.init()
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		let keys = Array(self.attributes.keys)

		aCoder.encodeObject(keys, forKey:"asset-attr-keys")

		for (k,v) in self.attributes {
			if let coderValue = v as? NSCoder {
				aCoder.encodeObject(coderValue, forKey:"asset-attr-\(k)")
			}
		}
	}
	
}
