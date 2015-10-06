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
import LiferayScreens


public let Flat7ThemeBasicGreen = UIColor(red: 39.0/255, green: 174.0/255, blue: 97.0/255, alpha: 1)


public func setFlat7ButtonBackground(button: UIButton?) {
	let stretchableImage = UIImage(
			named: "flat7-button",
			inBundle: NSBundle(forClass: BaseScreenlet.self),
			compatibleWithTraitCollection: nil)

	if let stretchableImageValue = stretchableImage {
		let backgroundImage = stretchableImageValue.resizableImageWithCapInsets(
				UIEdgeInsetsMake(19, 19, 19, 19),
				resizingMode: UIImageResizingMode.Stretch)

		button?.setBackgroundImage(backgroundImage, forState: UIControlState.Normal)

		button?.backgroundColor = UIColor.clearColor()
	}
}


