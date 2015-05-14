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


public class DDLField: NSObject, Equatable, Printable {

	public var onPostValidation: (Bool -> Void)?
	public var lastValidationResult:Bool?

	public var currentValue:AnyObject? {
		didSet {
			onChangedCurrentValue()
		}
	}

	public var currentValueAsString:String? {
		get {
			return convert(fromCurrentValue: currentValue)
		}
		set {
			currentValue = convert(fromString: newValue)
		}
	}

	public var currentValueAsLabel: String? {
		get {
			return convertToLabel(fromCurrentValue: currentValue)
		}
		set {
			currentValue = convert(fromLabel: newValue)
		}
	}

	public override var description: String {
		let currentValue = self.currentValueAsString
		var str = "DDLField[" +
				" name=\( self.name )" +
				" type=\( self.dataType.rawValue )" +
				" editor=\( self.editorType.rawValue )"
		if currentValue != nil {
			if currentValue! == "" {
				str += " value='' ]"
			}
			else {
				str += " value=\( currentValue! ) ]"
			}
		}
		else {
			str += " ]"
		}

		return str
	}

	public var currentLocale: NSLocale


	internal(set) var dataType: DataType
	internal(set) var editorType: Editor

	internal(set) var name: String

	internal(set) var label: String
	internal(set) var tip: String

	internal(set) var predefinedValue: AnyObject?

	internal(set) var readOnly: Bool
	internal(set) var repeatable: Bool
	internal(set) var required: Bool

	internal(set) var showLabel: Bool


	public init(attributes: [String:AnyObject], locale: NSLocale) {
		dataType = DataType(rawValue: valueAsString(attributes, key:"dataType")) ?? .Unsupported
		editorType = Editor.from(attributes: attributes)
		name = valueAsString(attributes, key:"name")

		readOnly = Bool.from(any: attributes["readOnly"] ?? "false")
		repeatable = Bool.from(any: attributes["repeatable"] ?? "false")
		required = Bool.from(any: attributes["required"] ?? "true")
		showLabel = Bool.from(any: attributes["showLabel"] ?? "false")

		label = valueAsString(attributes, key:"label")
		tip = valueAsString(attributes, key:"tip")

		currentLocale = locale

		super.init()

		predefinedValue = attributes["predefinedValue"] ?? nil
		if predefinedValue is String {
			predefinedValue = convert(fromString: predefinedValue as? String)
		}
		else {
			let predefinedStringValue = convert(fromCurrentValue: predefinedValue)
			predefinedValue = convert(fromString: predefinedStringValue)
		}

		currentValue = predefinedValue
	}

	public func validate() -> Bool {
		var valid = !(currentValue == nil && required)

		if valid {
			valid = doValidate()
		}

		onPostValidation?(valid)

		lastValidationResult = valid

		return valid
	}

	//MARK: Internal methods

	internal func doValidate() -> Bool {
		return true
	}

	internal func convert(fromString value:String?) -> AnyObject? {
		return value
	}

	internal func convert(fromLabel value:String?) -> AnyObject? {
		return value
	}

	internal func convert(fromCurrentValue value:AnyObject?) -> String? {
		return value?.description
	}

	internal func convertToLabel(fromCurrentValue value:AnyObject?) -> String? {
		return value?.description
	}

	internal func onChangedCurrentValue() {
	}

}


//MARK: Equatable

public func ==(left: DDLField, right: DDLField) -> Bool {
	return left.name == right.name
}


//MARK: Util func

private func valueAsString(dict: [String:AnyObject], #key: String) -> String {
	return (dict[key] ?? "") as! String
}
