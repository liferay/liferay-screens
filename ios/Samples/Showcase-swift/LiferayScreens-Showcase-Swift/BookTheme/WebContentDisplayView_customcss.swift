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


open class WebContentDisplayView_customcss: WebContentDisplayView_default {


	//MARK: WebContentDisplayViewModel

	override open func getCustomCssStyle() -> String {
		return "<style>.MobileCSS {padding: 4%; width: 92%;} " +
			".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, .MobileCSS h2, .MobileCSS h3 { " +
			"font-size: 110%; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; font-weight: 200; } " +
			".MobileCSS img { width: 100% !important; } " +
			".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }</style>"
	}
}
