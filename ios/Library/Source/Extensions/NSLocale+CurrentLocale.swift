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

extension NSLocale {

	public class func currentLocaleString() -> String {
		let locale:NSString = NSLocale.autoupdatingCurrentLocale().localeIdentifier

		let range = locale.rangeOfString("_")

		let preferredLanguage = NSLocale.preferredLanguages()[0].description

		return locale.stringByReplacingCharactersInRange(NSMakeRange(0, range.length+1),
			withString: preferredLanguage);
	}

}