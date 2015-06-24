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


/*
 * Handles the scenario when you have concurrent operations using
 * the same activity indicator. This leads to stop the indicator
 * when the first operation ends:
 *   Start A
 *   Start B
 *	 Start C
 *   Finish B -> without this extensions, the indicator will be stopped here
 *   Finish C
 *   Finish A -> with this extensions, the indicator will be stopped here
 */
extension UIActivityIndicatorView {

	private var startCount: Int {
		get {
			// since this is an extension, we cannot use stored properties
			// use tag to store the value
			return self.tag
		}
		set {
			self.tag = newValue
		}
	}

	public func startAnimatingConcurrent() {
		objc_sync_enter(self)

		if startCount == 0 {
			self.startAnimating()
		}

		startCount++

		objc_sync_exit(self)
	}

	public func stopAnimatingConcurrent() {
		if startCount > 0 {
			objc_sync_enter(self)

			startCount--

			if startCount == 0 {
				self.stopAnimating()
			}

			objc_sync_exit(self)
		}
	}

}
