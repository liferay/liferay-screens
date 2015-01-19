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


public class PortraitView_flat7: PortraitView_default {

	override public func loadPlaceholder() {
		if self.portraitImage?.image == nil {
			self.portraitImage?.image = UIImage(named: "flat7-portrait-placeholder")
		}
	}

	override func onShow() {
		super.onShow()

		self.borderColor = Flat7ThemeBasicGreen

		portraitImage?.layer.cornerRadius = min(frame.size.width, frame.size.height) / 2
	}

}
