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


public class DDLRecord: NSObject {

	public var fields: [DDLField] = []

	public var attributes: [String:AnyObject] = [:]

	public var recordId: Int64 {
		get {
			return (attributes["recordId"] ?? 0).longLongValue
		}
		set {
			attributes["recordId"] = Int(newValue)
		}
	}

	public subscript(fieldName: String) -> DDLField? {
		return fieldBy(name: fieldName)
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

	public init(recordData: [String:AnyObject]) {
		super.init()

		if let recordFields = (recordData["modelValues"] ?? nil) as? [String:AnyObject] {
			let parsedFields = DDLValuesParser().parse(recordFields)
		 	if !parsedFields.isEmpty {
				fields = parsedFields
			}
		}

		if let recordAttributes = (recordData["modelAttributes"] ?? nil) as? [String:AnyObject] {
			attributes = recordAttributes
		}
	}


	//MARK: Public methods

	public func fieldBy(#name: String) -> DDLField? {
		for field in fields {
			if field.name.lowercaseString == name.lowercaseString {
				return field
			}
		}

		return nil
	}

	public func updateCurrentValues(values: [String:AnyObject]) {
		for (index,field) in enumerate(fields) {
			let fieldValueLabel: AnyObject? = (values[field.name] ?? nil)
			if fieldValueLabel != nil {
				if fieldValueLabel is String {
					field.currentValueAsLabel = fieldValueLabel as? String
				}
				else {
					field.currentValue = fieldValueLabel
				}
			}
		}
	}

	public func clearValues() {
		for field in fields {
			field.currentValue = field.predefinedValue
		}
	}

}
