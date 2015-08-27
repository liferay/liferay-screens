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


@objc public class UserPortraitDefaultProgressPresenter: DefaultProgressPresenter {

	private weak var spinner: UIActivityIndicatorView?

	public init(spinner: UIActivityIndicatorView) {
		self.spinner = spinner

		super.init()
	}

	override public func hideHUD() {
		super.hideHUD()
		showSpinner(false)
	}

	override public func showHUDInView(view: UIView,
			message: String?,
			closeMode: ProgressCloseMode,
			spinnerMode: ProgressSpinnerMode) {

		if closeMode == .ManualClose_TouchClosable {
			showSpinner(false)

			super.showHUDInView(view,
				message: message,
				closeMode: .ManualClose_TouchClosable,
				spinnerMode: .NoSpinner)
		}
		else {
			showSpinner(true)
		}
	}


	private func showSpinner(show: Bool) {
		if self.spinner != nil && self.spinner!.isAnimating() != show {
			dispatch_main {
				if show {
					self.spinner?.startAnimating()
				}
				else {
					self.spinner?.stopAnimating()
				}
			}
		}
	}

}
