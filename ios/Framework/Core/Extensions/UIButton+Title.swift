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


extension UIButton {

	public func replaceAttributedTitle(title: String, forState state: UIControlState) {
		if let attributedTitle = self.attributedTitleForState(state) {
			let formattedString = NSMutableAttributedString(
					attributedString: attributedTitle)

			formattedString.replaceCharactersInRange(
					NSMakeRange(0, formattedString.length),
					withString: title)

			self.setAttributedTitle(formattedString, forState: state)
		}
		else {
			self.setAttributedTitle(NSAttributedString(string: title), forState: state)
		}

	}

}
