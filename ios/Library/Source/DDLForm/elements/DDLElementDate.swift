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

public class DDLElementDate : DDLElement {

	private let serverDateFormat = "MM/dd/yyyy"

	private let serverDateFormatter = NSDateFormatter()
	private let clientDateFormatter = NSDateFormatter()

	override init(attributes: [String:String], localized: [String:AnyObject]) {
		serverDateFormatter.dateFormat = serverDateFormat

		clientDateFormatter.dateStyle = .MediumStyle
		clientDateFormatter.timeStyle = .NoStyle

		super.init(attributes: attributes, localized: localized)
	}


	override internal func convert(fromString value:String?) -> AnyObject? {
		return serverDateFormatter.dateFromString(value)
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let date = value as? NSDate {
			result = clientDateFormatter.stringFromDate(date)
		}

		return result
	}

}
