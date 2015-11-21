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


public class DDLRecord: NSObject, NSCoding {

	public var fields: [DDLField] = []

	public var attributes: [String:AnyObject] = [:]

	public var recordId: Int64? {
		get {
			return attributes["recordId"]?.longLongValue
		}
		set {
			if let newValue = newValue {
				attributes["recordId"] = NSNumber(longLong: newValue)
			}
			else {
				attributes.removeValueForKey("recordId")
			}
		}
	}

	public subscript(fieldName: String) -> DDLField? {
		return fieldBy(name: fieldName)
	}

	public var values: [String:AnyObject] {
		var result = [String:AnyObject]()

		for field in fields {
			if let value = field.currentValueAsString {
				//FIXME - LPS-49460
				// Server rejects the request if the value is empty string.
				// This way we workaround the problem but a field can't be
				// emptied when you're editing an existing row.
				if value != "" {
					result[field.name] = value
				}
			}
		}

		return result
	}


	//MARK: Init
	
	public init(xsd: String, locale: NSLocale) {
		super.init()

		if let parsedFields = DDLXSDParser().parse(xsd, locale: locale) {
		 	if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}
	}

	public init(data: [String:AnyObject], attributes: [String:AnyObject]) {
		super.init()

		let parsedFields = DDLValuesParser().parse(data)
		if !parsedFields.isEmpty {
			fields = parsedFields
		}

		self.attributes = attributes
	}


	public init(dataAndAttributes: [String:AnyObject]) {
		super.init()

		if let recordFields = (dataAndAttributes["modelValues"] ?? nil) as? [String:AnyObject] {
			let parsedFields = DDLValuesParser().parse(recordFields)
		 	if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}

		if let recordAttributes = (dataAndAttributes["modelAttributes"] ?? nil) as? [String:AnyObject] {
			attributes = recordAttributes
		}
	}

	public required init?(coder aDecoder: NSCoder) {
		fields = aDecoder.decodeObjectForKey("fields") as! [DDLField]
		attributes = aDecoder.decodeObjectForKey("attributes") as! [String:AnyObject]

		super.init()
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		aCoder.encodeObject(fields, forKey:"fields")
		aCoder.encodeObject(attributes, forKey:"attributes")
	}



	//MARK: Public methods

	public func fieldBy(name name: String) -> DDLField? {
		for field in fields {
			if field.name.lowercaseString == name.lowercaseString {
				return field
			}
		}

		return nil
	}

	public func fieldsBy(type type: AnyClass) -> [DDLField] {
		var result = [DDLField]()
		let typeName = NSStringFromClass(type)

		for field in fields {
			if NSStringFromClass(field.dynamicType) == typeName {
				result.append(field)
			}
		}

		return result
	}

	public func updateCurrentValues(values: [String:AnyObject]) {
		fields.forEach {
			let fieldValueLabel: AnyObject? = (values[$0.name] ?? nil)
			if fieldValueLabel != nil {
				if fieldValueLabel is String {
					$0.currentValueAsLabel = fieldValueLabel as? String
				}
				else {
					$0.currentValue = fieldValueLabel
				}
			}
		}
	}

	public func clearValues() {
		fields.forEach {
			$0.currentValue = $0.predefinedValue
		}
	}

}
