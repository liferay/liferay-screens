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


public class DDLRecord: NSObject {

	public var fields: [DDLField] = []

	public var recordId: Int?

	public class func recordParsedFromXML(xml: String, locale: NSLocale) -> DDLRecord? {
		var result: DDLRecord?

		let parser = DDLParser(locale: locale)

		parser.xml = xml

		if let parsedFields = parser.parse() {
		 	if !parsedFields.isEmpty {
				result = DDLRecord()
				result!.fields = parsedFields
			}
		}

		return result
	}

	public func updateCurrentValues(values: [String:AnyObject]) {
		if fields.isEmpty {
			// TODO create list of DDLFields only with current value
		}
		else {
			fields = []

			for (index,field) in enumerate(fields) {
				let fieldValue = (values[field.name] ?? nil) as? String
				if let fieldStringValue = fieldValue {
					field.currentStringValue = fieldStringValue

					fields.append(field)
				}
			}
		}
	}


}
