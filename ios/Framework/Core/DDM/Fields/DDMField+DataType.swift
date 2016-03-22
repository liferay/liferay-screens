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

#if LIFERAY_SCREENS_FRAMEWORK
	import SMXMLDocument
#endif


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

		public static func from(xmlElement xmlElement:SMXMLElement) -> DataType {
			return DataType(rawValue: xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
		}

		public static func from(json json: JSONObject) -> DataType {
			return DataType(rawValue: (json["dataType"] as? String) ?? "") ?? .Unsupported
		}

		public func createField(
				attributes attributes:[String:AnyObject],
				locale: NSLocale,
				version: LiferayServerVersion)
				-> DDLField? {

			switch self {
				case .DDLBoolean:
					return DDLFieldBoolean(
							attributes:attributes,
							locale: locale)

				case .DDLString:
					switch DDLField.Editor.from(attributes: attributes) {
						case .Select, .Radio:
							return DDLFieldStringWithOptions(
									attributes: attributes,
									locale: locale)
						default:
							return DDLFieldString(
									attributes:attributes,
									locale: locale)
					}

				case .DDLDate:
					switch version {
					case .v62:
						return DDLFieldDate_v62(
							attributes:attributes,
							locale: locale)
					case .v70:
						return DDLFieldDate_v70(
							attributes:attributes,
							locale: locale)
					}

				case .DDLInteger, .DDLNumber, .DDLDouble:
					return DDLFieldNumber(
							attributes:attributes,
							locale: locale)

				case .DDLDocument:
					return DDLFieldDocument(
							attributes:attributes,
							locale: locale)

				default: ()
			}

			return nil
		}

	}

}
