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
import UIKit

struct DefaultResources {

	static let OddColorBackground = UIColor(red: 228.0/255.0, green: 71.0/255.0, blue: 64.0/255.0, alpha: 1)

	static let EvenColorBackground = UIColor.white

	static let background = UIColor(red: 214.0/255.0, green: 214.0/255.0, blue: 214.0/255.0, alpha: 1)

	///Gets the background color for an index
	static func backgroundColorForIndex(_ index: Int) -> UIColor {
		return index % 2 == 1 ?
			DefaultResources.OddColorBackground : DefaultResources.EvenColorBackground
	}

	///Gets the text color for an index
	static func textColorForIndex(_ index: Int) -> UIColor {
		return index % 2 == 0 ?
			DefaultResources.OddColorBackground : DefaultResources.EvenColorBackground
	}

	///Gets the arrow image for an index
	static func arrowImageForIndex(_ index: Int) -> UIImage {
		let imageName = index % 2 == 0 ? "icon_DOWN" : "icon_DOWN_W"
		return UIImage(named: imageName)!
	}

}
