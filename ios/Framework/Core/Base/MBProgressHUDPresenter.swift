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

#if LIFERAY_SCREENS_FRAMEWORK
	import MBProgressHUD
#endif


internal let MBProgressHUDLock = "hud-lock"


@objc public class MBProgressHUDPresenter: NSObject, ProgressPresenter {

	private var instance: MBProgressHUD?

	var customView: UIView?
	var customColor: UIColor?
	var customOpacity = Float(0.8)

	internal dynamic func simpleTapDetected(recognizer: UIGestureRecognizer!) {
		if let hud = recognizer.view as? MBProgressHUD {
			hud.hide(true)
			instance = nil
		}
	}

	public func showHUDInView(view: UIView) {
		showHUDInView(view,
			message: nil,
			details: nil,
			closeMode: .ManualClose,
			spinnerMode: .IndeterminateSpinner)
	}

	public func showHUDInView(view: UIView, message:String) {
		showHUDInView(view,
			message: message,
			details: nil,
			closeMode: .ManualClose,
			spinnerMode: .IndeterminateSpinner)
	}

	public func hideHUD() {
		assert(self.instance != nil, "MBProgressHUD must exist")

		synchronized(MBProgressHUDLock) {
			dispatch_main {
				self.instance!.hide(true)
				self.instance = nil
			}
		}
	}

	public func hideHUDWithMessage(message: String) {
		assert(self.instance != nil, "MBProgressHUD must exist")

		synchronized(MBProgressHUDLock) {
			dispatch_main {
				self.configureAndShowHUD(self.instance!,
					message: message,
					details: nil,
					closeMode: .Autoclose_TouchClosable,
					spinnerMode: .NoSpinner)
			}
		}
	}

	public func showHUDInView(view: UIView,
			message: String?,
			details: String?,
			closeMode: ProgressCloseMode,
			spinnerMode: ProgressSpinnerMode) {

		synchronized(MBProgressHUDLock) {
			dispatch_main {
				if self.instance == nil {
					self.instance = MBProgressHUD.showHUDAddedTo(view, animated:true)
				}

				self.configureAndShowHUD(self.instance!,
					message: message,
					details: details,
					closeMode: closeMode,
					spinnerMode: spinnerMode)
			}
		}
	}


	//MARK: PRIVATE METHODS

	private func configureAndShowHUD(hud: MBProgressHUD,
			message: String?,
			details: String?,
			closeMode: ProgressCloseMode,
			spinnerMode: ProgressSpinnerMode) {

		let hud = self.instance!

		hud.customView = customView
		hud.color = customColor
		hud.opacity = customOpacity

		hud.mode = spinnerModeToProgressModeHUD(spinnerMode)
		hud.minShowTime = 0.5

		if closeMode == .ManualClose_TouchClosable
				|| closeMode == .Autoclose_TouchClosable {
			hud.addGestureRecognizer(
				UITapGestureRecognizer(
					target: self,
					action: "simpleTapDetected:"))
		}

		if message != nil {
			hud.labelText = message
		}

		hud.detailsLabelText = (details ?? "") as String

		hud.show(true)

		if closeMode == .Autoclose_TouchClosable {
			// compute autodelay based on text's length
			let len = count(hud.labelText) + count(hud.detailsLabelText)
			let closeDelay = 1.5 + (Double(len) * 0.01)

			hud.hide(true, afterDelay: closeDelay)

			self.instance = nil
		}
	}


	private func rootView(currentView:UIView) -> UIView {
		if currentView.superview == nil {
			return currentView;
		}
		
		return rootView(currentView.superview!)
	}

	private func spinnerModeToProgressModeHUD(spinnerMode: ProgressSpinnerMode) -> MBProgressHUDMode {
		switch spinnerMode {
		case .IndeterminateSpinner:
			return .Indeterminate
		case .DeterminateSpinner:
			return .Determinate
		case .NoSpinner:
			return .Text
		}
	}

}

