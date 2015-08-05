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


@objc public enum ProgressCloseMode: Int {

	case ManualClose
	case ManualClose_TouchClosable

	case Autoclose
	case Autoclose_TouchClosable
}


@objc public enum ProgressSpinnerMode: Int {

	case IndeterminateSpinner
	case DeterminateSpinner
	case NoSpinner

}

@objc public protocol ProgressPresenter {

	func showHUDInView(view: UIView)
	func showHUDInView(view: UIView, message:String)
	func showHUDInView(view: UIView,
		message: String?,
		details: String?,
		closeMode: ProgressCloseMode,
		spinnerMode: ProgressSpinnerMode)

	func hideHUD()
	func hideHUDWithMessage(message:String)

}

@objc protocol ProgressPresenterCreator {

	func createProgressPresenter() -> ProgressPresenter

}
