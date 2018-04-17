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

		case checkbox = "checkbox"
		case text = "text"
		case textarea = "textarea"
		case select = "select"
		case radio = "radio"
		case date = "date"
		case number = "number"
		case document = "documentlibrary"
		case geolocation = "geolocation"
		case image = "image"
		case unsupported = ""

		public var defaultDataType: DataType {
			switch self {
			case .checkbox:
				return DataType.ddmBoolean
			case .text, .textarea, .select, .radio:
				return DataType.ddmString
			case .date:
				return DataType.ddmDate
			case .number:
				return DataType.ddmNumber
			case .document:
				return DataType.ddmDocument
			case .image:
				return DataType.ddmImage
			case .geolocation:
				return DataType.ddmGeolocation
			case .unsupported:
				return DataType.unsupported
			}
		}

		public static func from(attributes: [String: AnyObject]) -> Editor {
			return from(attributeValue: ((attributes["type"] as? String ?? "") ))
		}

		public static func from(attributeValue: String) -> Editor {
			let value = attributeValue.replacingOccurrences(of: "ddm-", with: "")

			switch value {
			case "integer", "decimal":
				return .number
			case "boolean":
				return .checkbox
			default:
				return Editor(rawValue: value) ?? .unsupported
			}
		}

		public static func all() -> [Editor] {
			return [checkbox, text, textarea, select, radio, date, number, document, geolocation]
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
