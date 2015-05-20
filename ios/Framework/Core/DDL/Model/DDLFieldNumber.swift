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


public class DDLFieldNumber : DDLField {

	public var maximumDecimalDigits = 2
	public var minimumDecimalDigits = 2

	public var isDecimal:Bool {
		return dataType != DataType.DDLInteger
	}


	//MARK: DDLField

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:NSNumber?

		if let stringValue = value {
			result = NSNumberFormatter().numberFromString(stringValue)

			if result != nil {
				switch CFNumberGetType(result! as CFNumberRef) {
					case .Float32Type, .Float64Type, .FloatType, .CGFloatType:
						return NSDecimalNumber(float: result!.floatValue)
					case .DoubleType:
						return NSDecimalNumber(double: result!.doubleValue)
					default:
						return NSInteger(result!.integerValue)
				}
			}
		}

		return result
	}

	override internal func convert(fromLabel label: String?) -> AnyObject? {
		var result: AnyObject?

		if label != nil {
			let formatter = NSNumberFormatter()

			formatter.locale = self.currentLocale

			if isDecimal {
				formatter.numberStyle = .DecimalStyle
				formatter.roundingMode = .RoundHalfUp
				formatter.maximumFractionDigits = maximumDecimalDigits
				formatter.minimumFractionDigits = minimumDecimalDigits
			}

			result = formatter.numberFromString(label!)
		}

		return result
	}

	override internal func convert(fromCurrentValue value: AnyObject?) -> String? {
		return formatNumber(value as? NSNumber,
				locale: NSLocale(localeIdentifier: "en_US"))
	}

	override internal func convertToLabel(fromCurrentValue value: AnyObject?) -> String? {
		return formatNumber(value as? NSNumber, locale: self.currentLocale)
	}

	override internal func onChangedCurrentValue() {
		if !isDecimal && currentValue is NSDecimalNumber {
			let decimal = (currentValue as! NSDecimalNumber).doubleValue
			currentValue = NSNumber(double: decimal + 0.5).integerValue
		}
	}


	//MARK: Private methods

	private func formatNumber(number: NSNumber?, locale:NSLocale) -> String? {
		if number == nil {
			return nil
		}

		let formatter = NSNumberFormatter()

		formatter.locale = locale

		if isDecimal {
			formatter.numberStyle = .DecimalStyle
			formatter.roundingMode = .RoundHalfUp
			formatter.maximumFractionDigits = maximumDecimalDigits
			formatter.minimumFractionDigits = minimumDecimalDigits
		}

		return formatter.stringFromNumber(number!)
	}

}
