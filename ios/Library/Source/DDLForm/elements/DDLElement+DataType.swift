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


extension DDLElement {

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
			return fromRaw(xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
		}

		public func createElement(#attributes:[String:String],
				localized:[String:AnyObject]) -> DDLElement? {

			switch self {
				case .DDLBoolean:
					return DDLElementBoolean(attributes:attributes, localized:localized)

				case .DDLString:
					switch DDLElement.Editor.from(attributes: attributes) {
						case .Select, .Radio:
							return DDLElementStringWithOptions(
									attributes:attributes,
									localized:localized)
						default:
							return DDLElementString(attributes:attributes, localized:localized)
					}

				case .DDLDate:
					return DDLElementDate(attributes:attributes, localized:localized)

				case .DDLInteger, .DDLNumber, .DDLDouble:
					return DDLElementNumber(attributes:attributes, localized:localized)

				case .DDLDocument:
					return DDLElementDocument(attributes:attributes, localized:localized)

				default: ()
			}

			return nil
		}

	}

}
