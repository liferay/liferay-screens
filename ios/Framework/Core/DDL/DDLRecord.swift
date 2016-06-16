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


@objc public class DDLRecord: NSObject, NSCoding {

	public var structure: DDMStructure?
	public let untypedValues: [DDMField]?

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

	public var fields: [DDMField] {
		return structure?.fields ?? untypedValues ?? []
	}

	public subscript(fieldName: String) -> DDMField? {
		return fieldBy(name: fieldName)
	}

	public var values: [String:AnyObject] {
		var result = [String:AnyObject]()

		for field in self.fields {
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

	public override var debugDescription: String {
		return "DDLRecord[" +
			" id=\( recordId?.description ?? "" )" +
			" attributes=\( attributes )" +
			" structure=\( structure?.debugDescription )" +
			" untypedValues=\( untypedValues )]"
	}


	//MARK: Init

	public init(structure: DDMStructure) {
		self.structure = structure
		untypedValues = nil

		super.init()
	}

	public init(xsd: String, locale: NSLocale) {
		structure = DDMStructure(xsd: xsd, locale: locale)
		untypedValues = nil

		super.init()
	}

	public init(json: String, locale: NSLocale) {
		structure = DDMStructure(json: json, locale: locale)
		untypedValues = nil

		super.init()
	}

	public init(data: [String:AnyObject], attributes: [String:AnyObject]) {
		structure = nil

		let parsedFields = DDLUntypedValuesParser().parse(data)
		if parsedFields.isEmpty {
			untypedValues = nil
		}
		else {
			untypedValues = parsedFields
		}

		self.attributes = attributes

		super.init()
	}

	public init(dataAndAttributes: [String:AnyObject]) {
		structure = nil

		if let recordFields = (dataAndAttributes["modelValues"] ?? nil) as? [String:AnyObject] {
			let parsedFields = DDLUntypedValuesParser().parse(recordFields)
		 	if parsedFields.isEmpty {
				untypedValues = nil
			}
			else {
				untypedValues = parsedFields
			}
		}
		else {
			untypedValues = nil
		}

		if let recordAttributes = (dataAndAttributes["modelAttributes"] ?? nil) as? [String:AnyObject] {
			attributes = recordAttributes
		}

		super.init()
	}

	public required init?(coder aDecoder: NSCoder) {
		structure = aDecoder.decodeObjectForKey("structure") as? DDMStructure
		untypedValues = aDecoder.decodeObjectForKey("untypedValues") as? [DDMField]
		attributes = aDecoder.decodeObjectForKey("attributes") as! [String:AnyObject]

		super.init()
	}

	public func encodeWithCoder(aCoder: NSCoder) {
		if let structure = structure {
			aCoder.encodeObject(structure, forKey: "structure")
		}
		if let untypedValues = untypedValues {
			aCoder.encodeObject(untypedValues, forKey: "untypedValues")
		}
		aCoder.encodeObject(attributes, forKey:"attributes")
	}


	//MARK: Public methods

	public func fieldBy(name name: String) -> DDMField? {
		return structure?.fieldBy(name: name)
					??
				untypedValues?.filter {
					$0.name.lowercaseString == name.lowercaseString
				}.first
	}

	public func fieldsBy(type type: AnyClass) -> [DDMField] {
		let typeName = NSStringFromClass(type)

		return structure?.fieldsBy(type: type)
					??
				untypedValues?.filter {
					NSStringFromClass($0.dynamicType) == typeName
				}
					??
				[]
	}

	public func updateCurrentValues(values values: [String:AnyObject]) {
		self.fields.forEach {
			if let fieldValue = values[$0.name] {
				if let fieldStringValue = fieldValue as? String {
					$0.currentValueAsString = fieldStringValue
				}
				else {
					$0.currentValue = fieldValue
				}
			}
		}
	}

	public func updateCurrentValues(xmlValues xmlValues: String) -> Int {
		let parser = DDMTypedValuesXMLParser()

		let count = parser.parse(xmlValues, structure: self.structure)

		if let createdStructure = parser.createdStructure
				where self.structure == nil {
			self.structure = createdStructure
		}

		return count
	}

	public func clearValues() {
		self.fields.forEach {
			$0.currentValue = $0.predefinedValue
		}
	}

}
