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
import LiferayScreens

open class WesterosCardProgressPresenter: ProgressPresenter {

	let screenlet: BaseScreenlet?

	var cardView: WesterosCardView? {
		return firstCardView(screenlet) as? WesterosCardView
	}

	public init(screenlet: BaseScreenlet?) {
		self.screenlet = screenlet
	}

	@objc open func showHUDInView(_ view: UIView, message: String?, forInteractor interactor: Interactor) {
		cardView?.activityIndicator.startAnimating()
	}

	@objc open func hideHUDFromView(_ view: UIView?, message: String?, forInteractor interactor: Interactor, withError error: NSError?) {
		cardView?.activityIndicator.stopAnimating()
	}

	func firstCardView(_ view: UIView?) -> CardView? {
		if let superview = view?.superview {
			if let cardView = superview as? CardView {
				return cardView
			}

			return firstCardView(superview)
		}

		return nil
	}
}
