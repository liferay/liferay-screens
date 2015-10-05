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


public class UserPortraitView_flat7: UserPortraitView_default {

	override public func onShow() {
		super.onShow()

		self.borderColor = Flat7ThemeBasicGreen

		portraitImage?.layer.cornerRadius = min(frame.size.width, frame.size.height) / 2
	}

	override public func loadPlaceholder() {
		self.portraitImage?.image = NSBundle.imageInBundles(
				name: "flat7-portrait-placeholder",
				currentClass: self.dynamicType)
	}

}
