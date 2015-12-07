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

	public class var currentLanguageString: String {
		get {
			var preferredLanguage = NSLocale.preferredLanguages()[0] 

			preferredLanguage = preferredLanguage.substringToIndex(
				preferredLanguage.startIndex.advancedBy(2))

			return preferredLanguage
		}
		set {
			NSUserDefaults.standardUserDefaults().setObject([newValue], forKey: "AppleLanguages")
			NSUserDefaults.standardUserDefaults().synchronize()
		}
	}

	public class var currentLocaleString: String {
		//FIXME Portal doesn't support any country, just few predefined ones
		let language = NSLocale.currentLanguageString

		switch language {
			case "ca", "es":
				return language + "_ES"
			case "zh":
				return language + "_CN"
			case "fi":
				return language + "_FI"
			case "fr":
				return language + "_FR"
			case "de":
				return language + "_DE"
			case "iw", "he":
				return "iw_IL"
			case "hu":
				return language + "_HU"
			case "ja":
				return language + "_JP"
			case "pt":
				return language + "_BR"
			default: ()
		}

		return "en_US"
	}

	public class func bundleForCurrentLanguageInBundle(bundle: NSBundle) -> NSBundle? {
		if let path = bundle.pathForResource(currentLanguageString, ofType: "lproj") {
			return NSBundle(path: path)
		}

		return nil
	}

}
