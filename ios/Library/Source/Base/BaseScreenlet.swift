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
import QuartzCore


/*!
 * BaseScreenlet is the base class from which all Screenlet classes must inherit.
 * A screenlet is the container for a screenlet view.
 */
@IBDesignable public class BaseScreenlet: UIView {

	@IBInspectable public var themeName: String? {
		set {
			let newName = (newValue ?? "default").lowercaseString

			if ThemeManager.exists(themeName: newName) {
				_themeName = newName
			}
			else {
				_themeName = "default"
			}

			if runningOnInterfaceBuilder {
				updateCurrentPreviewImage()
				setNeedsLayout()
			}
		}
		get {
			return _themeName
		}
	}

	internal var screenletView: BaseScreenletView?

	internal var screenletName: String {
		// In Beta 5, className will constain ModuleName.ClassName
		// just strip the first part

		var className = NSStringFromClass(self.dynamicType)

		className = className.componentsSeparatedByString(".")[1]
		className = className.componentsSeparatedByString("Screenlet")[0]

		return className
	}

	private var _themeName = "default"
	private var runningOnInterfaceBuilder = false
	private var currentPreviewImage:UIImage?
	private lazy var previewLayer = CALayer()


	//MARK: UIView

	override public func awakeFromNib() {
		super.awakeFromNib()

		onPreCreate()

		clipsToBounds = true;

		screenletView = loadScreenletView();

		onCreated()
	}

	override public func becomeFirstResponder() -> Bool {
		return screenletView!.becomeFirstResponder()
	}

	override public func didMoveToWindow() {
		if (window != nil) {
			onShow();
		}
		else {
			onHide();
		}
	}


	//MARK: Interface Builder management methods

	override public func prepareForInterfaceBuilder() {
		runningOnInterfaceBuilder = true

		updateCurrentPreviewImage()

		if currentPreviewImage == nil {
			if let previewImage = previewImageForTheme("default") {
				currentPreviewImage = previewImage
			}
		}
	}

	override public func layoutSubviews() {
		super.layoutSubviews()

		if runningOnInterfaceBuilder {
			if let currentPreviewImageValue = currentPreviewImage {
				previewLayer.frame = centeredRectInScreenlet(size: currentPreviewImageValue.size)
				previewLayer.contents = currentPreviewImageValue.CGImage

				if previewLayer.superlayer != layer {
					// add to the hierarchy the first time
					layer.addSublayer(previewLayer)
				}
			}
		}
	}


	//MARK: Internal methods

	internal func loadScreenletView() -> BaseScreenletView? {
		let view = createScreenletViewFromNib();

		if let viewValue = view {
			//FIXME: full-autoresize value. Extract from UIViewAutoresizing
			let flexibleMask = UIViewAutoresizing(18)

			if viewValue.autoresizingMask == flexibleMask {
				viewValue.frame = self.bounds
			}
			else {
				viewValue.frame = centeredRectInScreenlet(size: viewValue.frame.size)
			}

			viewValue.onUserAction = onUserAction;

			addSubview(viewValue)
			sendSubviewToBack(viewValue)

			return viewValue
		}

		return nil;
	}

	internal func previewImageForTheme(themeName:String) -> UIImage? {
		var result: UIImage?

		if let previewImagePath = previewImagePathForTheme(themeName) {
			result = UIImage(contentsOfFile: previewImagePath)
		}
		else if let screenletView = createScreenletViewFromNib() {
			result = previewImageFromView(screenletView)
		}

		return result
	}

	internal func previewImagePathForTheme(themeName:String) -> String? {
		let imageName = "\(themeName)-preview-\(screenletName.lowercaseString)"

		return NSBundle(forClass:self.dynamicType).pathForResource(imageName, ofType: "png")
	}

	internal func startOperationWithMessage(message:String, details:String? = nil) {
		showHUDWithMessage(message, details: details)
		onStartOperation()
		screenletView?.onStartOperation()
	}

	internal func finishOperationWithError(error:NSError, message:String, details:String? = nil) {
		showHUDWithMessage(message,
			details: details,
			closeMode:.ManualClose(true),
			spinnerMode:.NoSpinner)
		onFinishOperation()
		screenletView?.onFinishOperation()
	}

	internal func finishOperationWithMessage(message:String, details:String? = nil) {
		hideHUDWithMessage(message, details: details)
		onFinishOperation()
		screenletView?.onFinishOperation()
	}

	internal func finishOperation() {
		hideHUD()
		onFinishOperation()
		screenletView?.onFinishOperation()
	}

	internal func previewImageFromView(view: UIView) -> UIImage {
		let previewWidth = min(view.frame.size.width, self.frame.size.width)
		let previewHeight = min(view.frame.size.height, self.frame.size.height)

		UIGraphicsBeginImageContextWithOptions(CGSizeMake(previewWidth, previewHeight), false, 0.0)

		view.layer.renderInContext(UIGraphicsGetCurrentContext())
		let previewImage = UIGraphicsGetImageFromCurrentImageContext()

		UIGraphicsEndImageContext()

		return previewImage
	}


	//MARK: Templated/event methods: intended to be overwritten by children classes

	/*
	 * onCreated is invoked after the screenlet is created. 
	 * Override this method to set custom values for the screenlet properties.
	 */
	internal func onCreated() {
	}

	/*
	 * onPreCreate is invoked before the screenlet is created.
	 * Override this method to set create new UI components programatically.
	 *
	 */
	internal func onPreCreate() {
	}

	/*
	 * onHide is invoked when the screenlet is hidden from the app window.
	 */
	internal func onHide() {
	}

	/*
	 * onShow is invoked when the screenlet is displayed on the app window. 
	 * Override this method for example to reset values when the screenlet is shown.
	 */
	internal func onShow() {
	}

	/*
	 * onUserAction is invoked when a TouchUpInside even is fired from the UI.
	 */
	internal func onUserAction(actionName:String?, sender:AnyObject?) {
	}

	/**
	 * onStartOperation is called just before a screenlet request is sent to server
	 */
	internal func onStartOperation() {
	}

	/**
	 * onFinishOperation is called when the server response arrives
	 */
	internal func onFinishOperation() {
	}


	//MARK: Private

	private func createScreenletViewFromNib() -> BaseScreenletView? {
		let viewName = screenletName + "View"

		let bundle = NSBundle(forClass:self.dynamicType)

		var nibName = "\(viewName)_\(_themeName)"
		var nibPath = bundle.pathForResource(nibName, ofType:"nib")

		if nibPath == nil {
			nibName = viewName
			nibPath = bundle.pathForResource(nibName, ofType:"nib")

			if nibPath == nil {
				println("ERROR: Xib file '\(nibName)' was not found for theme '\(_themeName)'")
				return nil
			}
		}

		let views = bundle.loadNibNamed(nibName, owner:self, options:nil)
		assert(views.count > 0, "Xib seems to be malformed. There're no views inside it");

		let foundView = (views[0] as BaseScreenletView)

		return foundView
	}

	private func updateCurrentPreviewImage() {
		currentPreviewImage = previewImageForTheme(_themeName)
	}

	private func centeredRectInScreenlet(#size: CGSize) -> CGRect {
		return CGRectMake(
				(self.frame.size.width - size.width) / 2,
				(self.frame.size.height - size.height) / 2,
				size.width,
				size.height)
	}

}
