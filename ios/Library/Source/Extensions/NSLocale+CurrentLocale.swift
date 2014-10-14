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
		//FIXME Portal doesn't support any country, just few predefined ones
		var preferredLanguage = NSLocale.preferredLanguages()[0].description as String

		preferredLanguage = preferredLanguage.substringToIndex(
				advance(preferredLanguage.startIndex, 2))

		switch preferredLanguage {
			case "ca", "es":
				return preferredLanguage + "_ES"
			case "zh":
				return preferredLanguage + "_CN"
			case "fi":
				return preferredLanguage + "_FI"
			case "fr":
				return preferredLanguage + "_FR"
			case "de":
				return preferredLanguage + "_DE"
			case "iw", "he":
				return "iw_IL"
			case "hu":
				return preferredLanguage + "_HU"
			case "ja":
				return preferredLanguage + "_JP"
			case "pt":
				return preferredLanguage + "_BR"
			default: ()
		}

		return "en_US"
	}

}
