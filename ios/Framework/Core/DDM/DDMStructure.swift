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


//TODO: Unit test


@objc public class DDMStructure: NSObject, NSCoding {

	public var fields = [DDMField]()
	public let attributes: [String:AnyObject]
	public let locale: NSLocale

	public subscript(fieldName: String) -> DDMField? {
		return fieldBy(name: fieldName)
	}

	public override var debugDescription: String {
		return "DDMStructure[fields=\(fields)]"
	}


	//MARK: Init
	
	public init(xsd: String, locale: NSLocale, attributes: [String:AnyObject]) {
		if let parsedFields = DDMXSDParser().parse(xsd, locale: locale) {
		 	if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}

		self.locale = locale
		self.attributes = attributes

		super.init()
	}

	public init(json: String, locale: NSLocale) {
		if let parsedFields = DDMJSONParser().parse(json, locale: locale) {
			if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}

		self.locale = locale
		self.attributes = [:]

		super.init()
	}

	public required init?(coder aDecoder: NSCoder) {
		fields = aDecoder.decodeObjectForKey("fields") as! [DDMField]
		attributes = aDecoder.decodeObjectForKey("attributes") as! [String:AnyObject]
		locale = aDecoder.decodeObjectForKey("locale") as! NSLocale

		super.init()
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(fields, forKey:"fields")
		aCoder.encodeObject(attributes, forKey:"attributes")
		aCoder.encodeObject(locale, forKey:"locale")
	}



	//MARK: Public methods

	public func fieldBy(name name: String) -> DDMField? {
		return fields.filter {
			$0.name.lowercaseString == name.lowercaseString
		}.first
	}

	public func fieldsBy(type type: AnyClass) -> [DDMField] {
		let typeName = NSStringFromClass(type)

		return fields.filter {
			NSStringFromClass($0.dynamicType) == typeName
		}
	}

}
