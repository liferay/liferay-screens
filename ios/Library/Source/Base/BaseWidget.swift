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
 * BaseWidget is the base class from which all Widget classes must inherit.
 * A widget is the container for a widget view.
 */
@IBDesignable public class BaseWidget: UIView, LRCallback {

	internal func onServerError(error: NSError) {
	}
	internal func onServerResult(result: [String:AnyObject]) {
	}
	public func onFailure(error: NSError!) {
	}
	public func onSuccess(result: AnyObject!) {
	}



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

	internal var widgetView: BaseWidgetView?
	internal var serverOperation: ServerOperation?

	internal var widgetName: String {
		// In Beta 5, className will constain ModuleName.ClassName
		// just strip the first part

		var className = NSStringFromClass(self.dynamicType)

		className = className.componentsSeparatedByString(".")[1]
		className = className.componentsSeparatedByString("Widget")[0]

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

		widgetView = loadWidgetView();

		onCreated()
	}

	override public func becomeFirstResponder() -> Bool {
		return widgetView!.becomeFirstResponder()
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
				previewLayer.frame = centeredRectInWidget(size: currentPreviewImageValue.size)
				previewLayer.contents = currentPreviewImageValue.CGImage

				if previewLayer.superlayer != layer {
					// add to the hierarchy the first time
					layer.addSublayer(previewLayer)
				}
			}
		}
	}


	//MARK: Internal methods

	internal func loadWidgetView() -> BaseWidgetView? {
		let view = createWidgetViewFromNib();

		if let viewValue = view {
			viewValue.frame = centeredRectInWidget(size: viewValue.frame.size)
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
		else if let widgetView = createWidgetViewFromNib() {
			result = previewImageFromView(widgetView)
		}

		return result
	}

	internal func previewImagePathForTheme(themeName:String) -> String? {
		let imageName = "\(themeName)-preview-\(widgetName.lowercaseString)"

		return NSBundle(forClass:self.dynamicType).pathForResource(imageName, ofType: "png")
	}

	internal func startOperationWithMessage(message:String, details:String? = nil) {
		showHUDWithMessage(message, details: details)
		onStartOperation()
		widgetView?.onStartOperation()
	}

	internal func finishOperationWithError(error:NSError, message:String, details:String? = nil) {
		showHUDWithMessage(message,
			details: details,
			closeMode:.ManualClose,
			spinnerMode:.NoSpinner)
		onFinishOperation()
		widgetView?.onFinishOperation()
	}

	internal func finishOperationWithMessage(message:String, details:String? = nil) {
		hideHUDWithMessage(message, details: details)
		onFinishOperation()
		widgetView?.onFinishOperation()
	}

	internal func finishOperation() {
		hideHUD()
		onFinishOperation()
		widgetView?.onFinishOperation()
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
	 * onCreated is invoked after the widget is created. 
	 * Override this method to set custom values for the widget properties.
	 */
	internal func onCreated() {
	}

	/*
	 * onPreCreate is invoked before the widget is created.
	 * Override this method to set create new UI components programatically.
	 *
	 */
	internal func onPreCreate() {
	}

	/*
	 * onHide is invoked when the widget is hidden from the app window.
	 */
	internal func onHide() {
	}

	/*
	 * onShow is invoked when the widget is displayed on the app window. 
	 * Override this method for example to reset values when the widget is shown.
	 */
	internal func onShow() {
	}

	/*
	 * onUserAction is invoked when a TouchUpInside even is fired from the UI.
	 */
	internal func onUserAction(actionName:String?, sender:AnyObject?) {
	}

	/**
	 * onStartOperation is called just before a widget request is sent to server
	 */
	internal func onStartOperation() {
	}

	/**
	 * onFinishOperation is called when the server response arrives
	 */
	internal func onFinishOperation() {
	}


	//MARK: Private

	private func createWidgetViewFromNib() -> BaseWidgetView? {
		let viewName = widgetName + "View"

		let bundle = NSBundle(forClass:self.dynamicType)

		var nibName = "\(viewName)_\(_themeName)"
		var nibPath = bundle.pathForResource(nibName, ofType:"nib")

		if nibPath == nil {
			nibName = viewName
			nibPath = bundle.pathForResource(nibName, ofType:"nib")

			if nibPath == nil {
				println("ERROR: Xib file \(nibName) was not found")
				return nil
			}
		}

		let views = bundle.loadNibNamed(nibName, owner:self, options:nil)
		assert(views.count > 0, "Xib seems to be malformed. There're no views inside it");

		let foundView = (views[0] as BaseWidgetView)

		return foundView
	}

	private func updateCurrentPreviewImage() {
		currentPreviewImage = previewImageForTheme(_themeName)
	}

	private func centeredRectInWidget(#size: CGSize) -> CGRect {
		return CGRectMake(
				(self.frame.size.width - size.width) / 2,
				(self.frame.size.height - size.height) / 2,
				size.width,
				size.height)
	}

}
