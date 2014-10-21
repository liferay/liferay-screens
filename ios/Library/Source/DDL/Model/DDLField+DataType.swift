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


extension DDLField {

	public enum DataType: String {

		case DDLBoolean = "boolean"
		case DDLString = "string"
		case DDLDate = "date"
		case DDLInteger = "integer"
		case DDLNumber = "number"
		case DDLDouble = "double"
		case DDLDocument = "document-library"
		case Unsupported = ""

		public static func from(#xmlElement:SMXMLElement) -> DataType {
			return DataType(rawValue: xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
		}

		public func createField(#attributes:[String:AnyObject]) -> DDLField? {

			switch self {
				case .DDLBoolean:
					return DDLFieldBoolean(attributes:attributes)

				case .DDLString:
					switch DDLField.Editor.from(attributes: attributes) {
						case .Select, .Radio:
							return DDLFieldStringWithOptions(attributes:attributes)
						default:
							return DDLFieldString(attributes:attributes)
					}

				case .DDLDate:
					return DDLFieldDate(attributes:attributes)

				case .DDLInteger, .DDLNumber, .DDLDouble:
					return DDLFieldNumber(attributes:attributes)

				case .DDLDocument:
					return DDLFieldDocument(attributes:attributes)

				default: ()
			}

			return nil
		}

	}

}
