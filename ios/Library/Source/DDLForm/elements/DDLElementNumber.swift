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

public class DDLElementNumber : DDLElement {

	public var maximumDecimalDigits = 2
	public var minimumDecimalDigits = 2

	public var isDecimal:Bool {
		get {
			return dataType != DDLElementDataType.DDLInteger
		}
	}

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result = NSNumberFormatter().numberFromString(value)

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
		return result
	}

	override func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if let numberValue = value as? NSNumber {
			let formatter = NSNumberFormatter()

			formatter.locale = NSLocale.currentLocale()

			if isDecimal {
				formatter.numberStyle = .DecimalStyle
				formatter.roundingMode = .RoundHalfUp
				formatter.maximumFractionDigits = maximumDecimalDigits
				formatter.minimumFractionDigits = minimumDecimalDigits
			}

			result = formatter.stringFromNumber(numberValue)
		}

		return result
	}

	override func onChangedCurrentValue() {
		if !isDecimal && currentValue is NSDecimalNumber {
			let decimal = (currentValue as NSDecimalNumber).doubleValue
			currentValue = NSNumber(double: decimal + 0.5).integerValue
		}
	}

}
