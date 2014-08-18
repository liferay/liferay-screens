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

public struct DDLStringOption {

	public var name:String
	public var value:String
	public var label:String

}

public class DDLElementString : DDLElement {

	private(set) var multiple:Bool
	private(set) var options:[DDLStringOption]

	override init(attributes: [String:String], localized: [String:AnyObject]) {
		multiple = Bool.from(string: attributes["multiple"] ?? "false")

		if let optionsArray = (localized["options"] as AnyObject?) as? [[String:AnyObject]] {

			options = []

			for optionDict in optionsArray {
				let name = (optionDict["name"] ?? "") as String
				let value = (optionDict["value"] ?? "") as String
				let label = (optionDict["label"] ?? "") as String

				options.append(DDLStringOption(name: name, value: value, label: label))
			}
		}
		else {
			options = []
		}

		super.init(attributes: attributes, localized: localized)
	}

	override func convert(fromCurrentValue value: AnyObject?) -> String? {
		return value?.description
	}

	override func doValidate() -> Bool {
		var result = super.doValidate()

		if result && currentStringValue != nil && required {
			let trimmedString = currentStringValue?.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceCharacterSet())

			result = (trimmedString != "")
		}

		return result
	}

}
