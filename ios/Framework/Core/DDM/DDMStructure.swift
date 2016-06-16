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

	public init(fields: [DDMField], locale: NSLocale, attributes: [String:AnyObject]) {
		self.fields = fields
		self.locale = locale
		self.attributes = attributes

		super.init()
	}
	
	public convenience init?(xsd: String, locale: NSLocale, attributes: [String:AnyObject] = [:]) {
		guard let parsedFields = DDMXSDParser().parse(xsd, locale: locale) else {
			return nil
		}
		guard !parsedFields.isEmpty else {
			return nil
		}

		self.init(fields: parsedFields, locale: locale, attributes: attributes)
	}

	public convenience init?(json: String, locale: NSLocale, attributes: [String:AnyObject] = [:]) {
		guard let parsedFields = DDMJSONParser().parse(json, locale: locale) else {
			return nil
		}
		guard !parsedFields.isEmpty else {
			return nil
		}

		self.init(fields: parsedFields, locale: locale, attributes: attributes)
	}

	public convenience init?(structureData: [String:AnyObject], locale: NSLocale) {
		var newData = structureData

		if let xsd = structureData["xsd"] as? String {
			// v6.2: xml based structure
			newData.removeValueForKey("xsd")
			self.init(xsd: xsd, locale: locale, attributes: newData)
		}
		else if let json = structureData["definition"] as? String {
			// v7.0+: json based structure
			newData.removeValueForKey("definition")
			self.init(json: json, locale: locale, attributes: newData)
		}
		else {
			return nil
		}
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
