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
import SMXMLDocument

extension DDMField {

	public enum DataType: String {

		case ddmBoolean = "boolean"
		case ddmString = "string"
		case ddmDate = "date"
		case ddmInteger = "integer"
		case ddmNumber = "number"
		case ddmDouble = "double"
		case ddmDocument = "document-library"
		case ddmImage = "image"
		case ddmGeolocation = "geolocation"
		case unsupported = ""

		public static func from(xmlElement: SMXMLElement) -> DataType {
			return DataType(rawValue: xmlElement.attributeNamed("dataType") ?? "") ?? .unsupported
		}

		public static func from(json: JSONObject) -> DataType {
			return DataType(rawValue: (json["dataType"] as? String) ?? "") ?? .unsupported
		}

		// swiftlint:disable cyclomatic_complexity
		public func createField(
				attributes: [String: AnyObject],
				locale: Locale,
				version: LiferayServerVersion)
				-> DDMField? {

			switch self {
			case .ddmBoolean:
				return DDMFieldBoolean(
						attributes: attributes,
						locale: locale)

			case .ddmString:
				switch DDMField.Editor.from(attributes: attributes) {
				case .select, .radio:
					return DDMFieldStringWithOptions(
							attributes: attributes,
							locale: locale)
				default:
					return DDMFieldString(
							attributes: attributes,
							locale: locale)
				}

			case .ddmDate:
				switch version {
				case .v62:
					return DDMFieldDate_v62(
						attributes: attributes,
						locale: locale)
				case .v70, .v71:
					return DDMFieldDate_v70(
						attributes: attributes,
						locale: locale)
				}

			case .ddmInteger, .ddmNumber, .ddmDouble:
				return DDMFieldNumber(
						attributes: attributes,
						locale: locale)

			case .ddmDocument:
				return DDMFieldDocument(
						attributes: attributes,
						locale: locale)

			case .ddmImage:
				return DDMFieldImage(
					attributes: attributes,
					locale: locale)

			case .ddmGeolocation:
				return DDMFieldGeolocation(
					attributes: attributes,
					locale: locale)

			default: ()
			}

			return nil
		}

	}
}
