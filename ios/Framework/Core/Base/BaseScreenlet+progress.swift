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


internal let BaseScreenletHudLock = "hud-lock"


internal struct MBProgressHUDInstance {

	static var instance:MBProgressHUD?
	static var touchHandler:HUDTouchHandler?
	static var customView:UIView? {
		didSet {
			if instance != nil {
				instance!.customView = customView
			}
		}
	}
	static var customColor:UIColor? {
		didSet {
			if instance != nil {
				instance!.color = customColor
			}
		}
	}
	static var customOpacity:Float = 0.8 {
		didSet {
			if instance != nil {
				instance!.opacity = customOpacity
			}
		}
	}

}


internal class HUDTouchHandler {

	internal dynamic func simpleTapDetected(recognizer:UIGestureRecognizer!) {
		if let hud = recognizer.view as? MBProgressHUD {
			hud.hide(true)
			MBProgressHUDInstance.instance = nil
		}
	}
}


/*!
 * This extension to BaseScreenlet adds methods to display an "In Progress" HUD.
 */
extension BaseScreenlet {

	public enum CloseMode {

		case ManualClose(Bool)
		case AutocloseDelayed(Double, Bool)
		case AutocloseComputedDelay(Bool)

		internal func allowCloseOnTouch() -> Bool {
			var result = false

			switch self {
				case .AutocloseComputedDelay(let touchClose):
					result = touchClose
				case .AutocloseDelayed(let delay, let touchClose):
					result = touchClose
				case .ManualClose(let touchClose):
					result = touchClose
			}

			return result
		}

	}


	public enum SpinnerMode {

		case IndeterminateSpinner
		case DeterminateSpinner
		case NoSpinner

		internal func toProgressModeHUD() -> MBProgressHUDMode {
			switch self {
				case IndeterminateSpinner:
					return .Indeterminate
				case DeterminateSpinner:
					return .Determinate
				case NoSpinner:
					return .Text
			}
		}

	}


	//MARK: Class methods

	public class func setHUDCustomView(newValue:UIView?) {
		MBProgressHUDInstance.customView = newValue
	}

	public class func setHUDCustomColor(newValue:UIColor?) {
		MBProgressHUDInstance.customColor = newValue
	}

	/*
	 * showHUDWithMessage shows an animated Progress HUD with the message and details provided.
	*/
	public func showHUDWithMessage(message:String?,
			details:String? = nil,
			closeMode:CloseMode = .ManualClose(false),
			spinnerMode:SpinnerMode = .IndeterminateSpinner) {

		synchronized(BaseScreenletHudLock) {
			if MBProgressHUDInstance.instance == nil {
				MBProgressHUDInstance.instance =
					MBProgressHUD.showHUDAddedTo(self.rootView(self), animated:true)
			}

			MBProgressHUDInstance.instance?.customView = MBProgressHUDInstance.customView
			MBProgressHUDInstance.instance?.color = MBProgressHUDInstance.customColor
			MBProgressHUDInstance.instance!.mode = spinnerMode.toProgressModeHUD()
			MBProgressHUDInstance.instance!.minShowTime = 0.5

			if closeMode.allowCloseOnTouch() {
				MBProgressHUDInstance.touchHandler = HUDTouchHandler()
				MBProgressHUDInstance.instance!.addGestureRecognizer(
						UITapGestureRecognizer(
							target: MBProgressHUDInstance.touchHandler!,
							action: "simpleTapDetected:"))
			}

			if message != nil {
				MBProgressHUDInstance.instance!.labelText = message
			}

			MBProgressHUDInstance.instance!.detailsLabelText = (details ?? "") as String

			var closeDelay: Double?

			switch closeMode {
				case .AutocloseComputedDelay(_):
					closeDelay = Double.infinity
				case .AutocloseDelayed(let delay, _):
					closeDelay = delay
				default: ()
			}

			MBProgressHUDInstance.instance!.show(true)

			if var delay = closeDelay {
				if delay == Double.infinity {
					// compute autodelay based on text's length
					let len: Int =
    	                count(MBProgressHUDInstance.instance!.labelText) +
						count(MBProgressHUDInstance.instance!.detailsLabelText)
					delay = 1.5 + (Double(len) * 0.01)
				}

				MBProgressHUDInstance.instance!.hide(true, afterDelay: delay)
				MBProgressHUDInstance.instance = nil
			}
		}
	}

	public func showHUDAlert(#message: String, details: String? = nil) {
		showHUDWithMessage(message,
				details: details,
				closeMode: .ManualClose(true),
				spinnerMode: .NoSpinner)
	}

	/*
	 * hideHUDWithMessage hides an existing animated Progress HUD displaying the message and
	 * details provided first for a few seconds, calculated based on the length of the message.
	*/
	public func hideHUDWithMessage(message:String, details:String? = nil) {
		showHUDWithMessage(message,
				details: details,
				closeMode: .AutocloseComputedDelay(true),
				spinnerMode: .NoSpinner)
	}

	public func hideHUD() {
		synchronized(BaseScreenletHudLock) {
			if let instance = MBProgressHUDInstance.instance {
				instance.hide(true)
				MBProgressHUDInstance.instance = nil
			}
		}
	}


	//MARK: PRIVATE METHODS

	private func rootView(currentView:UIView) -> UIView {
		if currentView.superview == nil {
			return currentView;
		}

		return rootView(currentView.superview!)
	}

}
