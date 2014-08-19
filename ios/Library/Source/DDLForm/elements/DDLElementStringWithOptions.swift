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

public class DDLStringOption {

	public var label:String
	public var name:String
	public var value:String

	public init(label:String, name:String, value:String) {
		self.label = label
		self.name = name
		self.value = value
	}

}

public class DDLElementStringWithOptions : DDLElement {

	// FIXME Multiple selection not supported yet
	private(set) var multiple:Bool

	private(set) var options:[DDLStringOption]

	override init(attributes: [String:String], localized: [String:AnyObject]) {
		multiple = Bool.from(string: attributes["multiple"] ?? "false")

		options = []

		if let optionsArray = (localized["options"] as AnyObject?) as? [[String:AnyObject]] {

			for optionDict in optionsArray {
				let label = (optionDict["label"] ?? "") as String
				let name = (optionDict["name"] ?? "") as String
				let value = (optionDict["value"] ?? "") as String

				let option = DDLStringOption(label:label, name:name, value:value)

				options.append(option)
			}
		}

		super.init(attributes: attributes, localized: localized)

		if options.count > 0 {
			predefinedValue = processPredefinedOptions()
			currentValue = predefinedValue
		}
	}

	override func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result:String = "[\""

		if let currentOptions = value as? [DDLStringOption] {
			var first = true
			for option in currentOptions {
				if first {
					first = false
				}
				else {
					result += "\", \""
				}

				result += option.value
			}
		}

		return result + "\"]"
	}

	override func doValidate() -> Bool {
		let current = (currentValue as [DDLStringOption]?) ?? []

		return !(required && current.count == 0)
	}

	override internal func onChangedCurrentValue() {
		if !(currentValue is [DDLStringOption]) && currentValue is String {
			if let foundOption = findOption(byLabel: currentValue as String) {
				currentValue = [foundOption]
			}
			else {
				currentValue = nil
			}
		}
	}


	private func processPredefinedOptions() -> [DDLStringOption] {
		var result:[DDLStringOption] = []

		if let predefinedStringValue = predefinedValue as? String {
			if predefinedStringValue != "" {
				let predefinedContent = removeFirstAndLastChars(predefinedStringValue)

				for predefinedOption in predefinedContent.componentsSeparatedByString(",") {
					let predefinedOptionValue = removeFirstAndLastChars(predefinedOption)

					if let foundOption = findOption(byValue:predefinedOptionValue) {
						result.append(foundOption)
					}
				}
			}
		}

		return result
	}

	private func removeFirstAndLastChars(value:String) -> String {
		var result: String = value

		if countElements(value) >= 2 {
			let range = Range<String.Index>(
							start: value.startIndex.successor(),
							end: value.endIndex.predecessor())
			result = value.substringWithRange(range)
		}

		return result
	}

	private func findOption(byValue value:String) -> DDLStringOption? {
		for option in options {
			if option.value == value {
				return option
			}
		}

		return nil
	}

	private func findOption(byLabel label:String) -> DDLStringOption? {
		for option in options {
			if option.label == label {
				return option
			}
		}

		return nil
	}


}
