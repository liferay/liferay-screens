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


public class DDLFieldDate : DDLField {

	override public var currentLocale: NSLocale {
		didSet {
			clientDateFormatter.locale = self.currentLocale
		}
	}

	private let clientDateFormatter = NSDateFormatter()

	override public init(attributes: [String:AnyObject], locale: NSLocale) {
		super.init(attributes: attributes, locale: locale)

		initFormatter(locale)
	}

	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)

		initFormatter(self.currentLocale)
	}

	private func initFormatter(locale: NSLocale) {
		clientDateFormatter.dateStyle = .LongStyle
		clientDateFormatter.timeStyle = .NoStyle
		clientDateFormatter.locale = locale
	}


	//MARK: DDLField

	override internal func convert(fromString value:String?) -> AnyObject? {
		guard let stringValue = value else {
			return nil
		}

		// minimum date length in mm/dd/yy is 6 characters
		guard stringValue.characters.count >= 6 else {
			return nil
		}

		let separator = stringValue[stringValue.endIndex.advancedBy(-3)]

		let formatter = NSDateFormatter()
		formatter.timeZone = NSTimeZone(abbreviation: "GMT")
		formatter.dateFormat = (separator == "/") ? "MM/dd/yy" : "MM/dd/yyyy"

		return formatter.dateFromString(stringValue)
	}

	override func convert(fromLabel label: String?) -> AnyObject? {
		var result: AnyObject?

		if label != nil {
			result = clientDateFormatter.dateFromString(label!)
		}

		return result
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let date = value as? NSDate {
			// Java uses milliseconds instead of seconds
			let epoch = UInt64(date.timeIntervalSince1970 * 1000)

			result = "\(epoch)"
		}

		return result
	}

	override func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let date = currentValue as? NSDate {
			result = clientDateFormatter.stringFromDate(date)
		}

		return result
	}

}
