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

extension DDMField {

	public enum Editor: String {

		case Checkbox = "checkbox"
		case Text = "text"
		case Textarea = "textarea"
		case Select = "select"
		case Radio = "radio"
		case Date = "date"
		case Number = "number"
		case Document = "documentlibrary"
		case Geolocation = "geolocation"
		case Image = "image"
		case Unsupported = ""

		public var defaultDataType: DataType {
			switch self {
			case .Checkbox:
				return DataType.DDMBoolean
			case .Text, .Textarea, .Select, .Radio:
				return DataType.DDMString
			case .Date:
				return DataType.DDMDate
			case .Number:
				return DataType.DDMNumber
			case .Document:
				return DataType.DDMDocument
			case .Image:
				return DataType.DDMImage
			case .Geolocation:
				return DataType.DDMGeolocation
			case .Unsupported:
				return DataType.Unsupported
			}
		}

		public static func from(attributes: [String: AnyObject]) -> Editor {
			return from(attributeValue: ((attributes["type"] as? String ?? "") ))
		}

		public static func from(attributeValue: String) -> Editor {
			let value = attributeValue.replacingOccurrences(of: "ddm-", with: "")

			switch value {
			case "integer", "decimal":
				return .Number
			case "boolean":
				return .Checkbox
			default:
				return Editor(rawValue: value) ?? .Unsupported
			}
		}

		public static func all() -> [Editor] {
			return [Checkbox, Text, Textarea, Select, Radio, Date, Number, Document, Geolocation]
		}

		public func toCapitalizedName() -> String {
			var typeName = rawValue

			// hack for names prefixed with ddm
			if typeName.hasPrefix("ddm-") {
				typeName = typeName.replacingOccurrences(of: "ddm-",
						with: "")
			}

			// Capitalize first char

			let secondCharIndex = typeName.index(after: typeName.startIndex)

			return typeName[..<secondCharIndex].uppercased() + typeName[secondCharIndex...]
		}

	}

}
