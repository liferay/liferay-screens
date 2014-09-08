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

public enum DDLElementDataType: String {

	case Boolean = "boolean"
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementDataType {
		return fromRaw(xmlElement.attributeNamed("dataType") ?? "") ?? .Unsupported
	}

	public func createElement(#attributes:[String:String], localized:[String:String]) -> DDLElement? {
		switch self {
		case .Boolean:
			return DDLBooleanElement(attributes:attributes, localized:localized)
		default:
			return nil
		}
	}
}


public enum DDLElementType: String {

	case Checkbox = "checkbox"
	case Unsupported = ""

	public static func from(#xmlElement:SMXMLElement) -> DDLElementType {
		return fromRaw(xmlElement.attributeNamed("type") ?? "") ?? .Unsupported
	}

	public static func all() -> [DDLElementType] {
		return [Checkbox]
	}

	public func toCapitalizedName() -> String {
		let typeName = toRaw()

		let secondCharIndex = typeName.startIndex.successor()

		return typeName.substringToIndex(secondCharIndex).uppercaseString + typeName.substringFromIndex(secondCharIndex)
	}

}


public class DDLElement {

	public var currentValue:AnyObject?

	public var currentStringValue:String? {
		get {
			return convert(fromCurrentValue: currentValue)
		}
	}

	public var validatedClosure: ((Bool) -> ())?


	private(set) var dataType:DDLElementDataType
	private(set) var type:DDLElementType

	private(set) var name:String

	private(set) var label:String
	private(set) var tip:String

	private(set) var predefinedValue:AnyObject?

	private(set) var readOnly:Bool
	private(set) var repeatable:Bool
	private(set) var required:Bool

	private(set) var showLabel:Bool 	// Makes sense in mobile??
	private(set) var width:Int? 		// Makes sense in mobile??



	public init(attributes:[String:String], localized:[String:String]) {
		dataType = DDLElementDataType.fromRaw(attributes["dataType"] ?? "") ?? .Unsupported
		type = DDLElementType.fromRaw(attributes["type"] ?? "") ?? .Unsupported
		name = attributes["name"] ?? ""

		readOnly = Bool.from(string: attributes["readOnly"] ?? "false")
		repeatable = Bool.from(string: attributes["repeatable"] ?? "false")
		required = Bool.from(string: attributes["required"] ?? "true")
		showLabel = Bool.from(string: attributes["showLabel"] ?? "true")

		label = localized["label"] ?? ""
		tip = localized["tip"] ?? ""
		predefinedValue = convert(fromString:localized["predefinedValue"])
		currentValue = predefinedValue
	}

	public func validate() -> Bool {
		var valid = !(currentValue == nil && required)

		if valid {
			valid = doValidate()
		}

		validatedClosure?(valid)

		return valid
	}

	internal func doValidate() -> Bool {
		return true
	}

	internal func convert(fromString value:String?) -> AnyObject? {
		return value
	}

	internal func convert(fromCurrentValue value:AnyObject?) -> String? {
		return value?.description
	}

}
