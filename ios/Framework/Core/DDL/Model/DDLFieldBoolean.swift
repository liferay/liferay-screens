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


public class DDLFieldBoolean : DDLField {

	override internal func convert(fromString value:String?) -> AnyObject? {
		return value != nil ? Bool.from(string: value!) : nil
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		let trueLabel = LocalizedString("core", key: "yes", obj: self).lowercaseString
		let falseLabel = LocalizedString("core", key: "no", obj: self).lowercaseString

		if label?.lowercaseString == trueLabel {
			return true
		}
		else if label?.lowercaseString == falseLabel {
			return false
		}

		return nil
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let boolValue = value as? Bool {
			result = boolValue ? "true" : "false"
		}

		return result
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let boolValue = value as? Bool {
			result = LocalizedString("core", key: boolValue ? "yes" : "no", obj: self)
		}

		return result
	}


}
