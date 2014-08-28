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

public class DDLElementDocument : DDLElement {

	public var mimeType: String? {
		var result:String?

		switch currentValue {
			case is UIImage:
				result = "image/png"
			case is NSURL:
				result = "video/mpeg"
			default: ()
		}

		return result
	}

	override internal func convert(fromString value:String?) -> AnyObject? {
		var result:String? = nil

		if value != nil && value! != "" {
			result = value!
		}

		return result
	}

	override func convert(fromCurrentValue value: AnyObject?) -> String? {
		var result: String?

		if value is UIImage {
			result = "Image"
		}
		else if value is NSURL {
			result = "Video"
		}

		return result
	}

}
