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


@objc public class MBProgressHUDPresenter: NSObject, ProgressPresenter {

	public var instance: MBProgressHUD?

	public var customView: UIView?
	public var customColor: UIColor?
	public var customOpacity = Float(0.8)

	internal dynamic func simpleTapDetected(recognizer: UIGestureRecognizer!) {
		if let hud = recognizer.view as? MBProgressHUD {
			hud.hide(true)
			instance = nil
		}
	}

	public func hideHUD() {
		if self.instance == nil {
			return
		}

		dispatch_main {
			self.instance!.hide(true)
			self.instance = nil
		}
	}

	public func showHUDInView(view: UIView,
			message: String?,
			closeMode: ProgressCloseMode,
			spinnerMode: ProgressSpinnerMode) {

		dispatch_main {
			if self.instance == nil {
				self.instance = MBProgressHUD.showHUDAddedTo(view, animated:true)
			}

			self.configureAndShowHUD(self.instance!,
				message: message,
				closeMode: closeMode,
				spinnerMode: spinnerMode)
		}
	}


	//MARK: PRIVATE METHODS

	private func configureAndShowHUD(hud: MBProgressHUD,
			message: String?,
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
					action: #selector(MBProgressHUDPresenter.simpleTapDetected(_:))))
		}

		let components = message?.componentsSeparatedByCharactersInSet(NSCharacterSet.newlineCharacterSet())

		if let components = components {
			hud.labelText = components[0]
			hud.detailsLabelText = components.count > 1 ? components[1] : nil
		}

		hud.show(true)

		if closeMode == .Autoclose_TouchClosable {
			// compute autodelay based on text's length
			let len = (hud.labelText ?? "").characters.count
				+ (hud.detailsLabelText ?? "").characters.count

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
