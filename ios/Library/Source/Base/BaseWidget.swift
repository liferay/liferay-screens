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

	@IBInspectable var Theme:UIImage? {
		didSet {
			if _runningOnInterfaceBuilder {
				updateCurrentPreviewImage()
				setNeedsLayout()
			}
		}
	}

	internal var widgetView: BaseWidgetView?


	//MARK: DISPLAY TEMPLATE METHODS

	/*
	 * onCreated is invoked after the widget is created. Override this method to set custom values for the widget
	 * properties.
	 */
	internal func onCreated() {
	}

	/*
	 * onPreCreate is invoked before the widget is created.
	 * properties.
	 */
	internal func onPreCreate() {
	}

	/*
	 * onHide is invoked when the widget is hidden from the app window.
	 */
	internal func onHide() {
	}

	/*
	 * onShow is invoked when the widget is displayed on the app window. Override this method for example to reset
	 * values when the widget is shown.
	 */
	internal func onShow() {
	}


	//MARK: SERVER RESPONSE TEMPLATE METHODS

	/*
	 * onServerError is invoked when there is an error communicating with the Liferay server.
	 */
	internal func onServerError(error: NSError) {
	}

	/*
	 * onServerResult is invoked when there is an result from a communication with the Liferay server. The type of the
	 * result will depend on the invocation done from specific subclasses.
	 */
	internal func onServerResult(dict:[String:AnyObject]) {
	}


	//MARK: USER ACTIONS TEMPLATE METHOD

	/*
	 * onCustomAction is invoked when a TouchUpInside even is fired from the UI.
	 */
	internal func onCustomAction(actionName:String?, sender:AnyObject?) {
	}

	//MARK: Operations template methods

	internal func onStartOperation() {
	}

	internal func onFinishOperation() {
	}


	//MARK: UIView METHODS

	override public func awakeFromNib() {
		super.awakeFromNib()

		onPreCreate()

		self.clipsToBounds = true;

		widgetView = loadWidgetView();

		onCreated()
	}

	override public func becomeFirstResponder() -> Bool {
		return widgetView!.becomeFirstResponder()
	}

	override public func didMoveToWindow() {
		if (self.window != nil) {
			self.onShow();
		}
		else {
			self.onHide();
		}
	}


	//MARK: Interface Builder management methods

	override public func prepareForInterfaceBuilder() {
		_runningOnInterfaceBuilder = true

		if Theme != nil {
			updateCurrentPreviewImage()
		}

		if _currentPreviewImage == nil {
			_currentPreviewImage = previewImageForTheme("default")
		}
	}

	override public func layoutSubviews() {
		super.layoutSubviews()

		if _runningOnInterfaceBuilder {
			if let currentPreviewImageValue = _currentPreviewImage {
				let imageRect = CGRectMake(0, 0, currentPreviewImageValue.size.width, currentPreviewImageValue.size.height)

				_previewLayer.frame = imageRect
				_previewLayer.contents = currentPreviewImageValue.CGImage

				if _previewLayer.superlayer != layer {
					// add to the hierarchy the first time
					layer.addSublayer(_previewLayer)
				}
			}
		}
	}


	//MARK: LRCallback

	public func onFailure(error: NSError!) {
		onServerError(error ?? NSError(domain: "LiferayWidget", code: 0, userInfo: nil))
	}

	public func onSuccess(result: AnyObject!) {
		if let objcDict = result as? NSDictionary {
			onServerResult(result as [String:AnyObject])
		}
		else {
			onServerResult(["result": result])
		}
	}

	//MARK: Internal

	internal func loadWidgetView() -> BaseWidgetView? {
		let view = self.createWidgetViewFromNib();

		if let viewValue = view {
			viewValue.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)
			viewValue.customAction = self.onCustomAction;

			self.addSubview(viewValue)

			return viewValue
		}

		return nil;
	}

	internal func currentThemeName() -> String {
		var result = "default"

		if (Theme != nil) {
			let selectedSignatureImage = Theme!
			for themeName in ThemeManager.instance().installedThemes() {
				if themeName != "default" {
					let installedSignatureImage = signatureImageForTheme(themeName)

					if installedSignatureImage.isBinaryEquals(selectedSignatureImage) {
						result = themeName
						break;
					}
				}
			}
		}

		return result
	}

	internal func widgetName() -> String {
		// In Beta 5, className will constain ModuleName.ClassName
		// just strip the first part

		var className = NSStringFromClass(self.dynamicType)

		className = className.componentsSeparatedByString(".")[1]
		className = className.componentsSeparatedByString("Widget")[0]

		return className
	}

	internal func previewImageForTheme(themeName:String) -> UIImage {
        let widgetView = createWidgetViewFromNib()
        
        if let widgetViewValue = widgetView {
            widgetViewValue.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)

            return previewImageFromView(widgetViewValue)
        }
        
		return loadImageFromIB(previewImageNameForTheme(themeName))
	}

	internal func previewImageNameForTheme(themeName:String) -> String {
		return "\(themeName)-preview-\(widgetName().lowercaseString)"
	}

	internal func signatureImageForTheme(themeName:String) -> UIImage {
		return loadImageFromIB(signatureImageNameForTheme(themeName))
	}

	internal func signatureImageNameForTheme(themeName:String) -> String {
		return "theme-\(themeName)"
	}

	internal func loadImageFromIB(imageName:String) -> UIImage {
		let bundle = NSBundle(forClass:self.dynamicType)

		let fileName = bundle.pathForResource(imageName, ofType: "png")

		return UIImage(contentsOfFile: fileName!)
	}

	internal func startOperationWithMessage(message:String, details:String? = nil) {
		showHUDWithMessage(message, details: details)
		onStartOperation()
		widgetView?.onStartOperation()
	}

	internal func finishOperationWithError(error:NSError, message:String, details:String? = nil) {
		showHUDWithMessage(message, details: details, closeMode:.NoAutoclose(true), spinnerMode:.NoSpinner)
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

    internal func previewImageFromView(view:UIView) -> UIImage {
        UIGraphicsBeginImageContextWithOptions(view.frame.size, false, 0.0)

        view.layer.renderInContext(UIGraphicsGetCurrentContext())
        let previewImage = UIGraphicsGetImageFromCurrentImageContext()
        
        UIGraphicsEndImageContext()
        
        return previewImage
    }

	private func createWidgetViewFromNib() -> BaseWidgetView? {
		let viewName = widgetName() + "View"

		let bundle = NSBundle(forClass:self.dynamicType)

		var nibName = viewName + "-" + currentThemeName()
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

		//??		foundView.backgroundColor = UIColor.clearColor()

		return foundView
	}

	private func updateCurrentPreviewImage() {
		ThemeManager.instance().loadThemes()

		let themeName = currentThemeName()

		_currentPreviewImage = previewImageForTheme(themeName)
	}

	private var _runningOnInterfaceBuilder:Bool = false

	private lazy var _previewLayer: CALayer = {
		return CALayer()
	}()

	private var _currentPreviewImage:UIImage?

}