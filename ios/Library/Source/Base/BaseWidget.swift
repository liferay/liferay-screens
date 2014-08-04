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
@IBDesignable class BaseWidget: UIView, LRCallback {

	@IBInspectable var Theme:UIImage? {
		didSet {
			if _runningOnInterfaceBuilder {
				ThemeManager.instance().loadThemes()
				updateCurrentPreviewImage()
				setNeedsLayout()
			}
		}
	}

	internal var widgetView: BaseWidgetView?


	//MARK: DISPLAY TEMPLATE METHODS

	/*
	 * onCreate is invoked after the widget is created. Override this method to set custom values for the widget
	 * properties.
	 */
	public func onCreate() {
	}

	/*
	 * onHide is invoked when the widget is hidden from the app window.
	 */
	public func onHide() {
	}

	/*
	 * onShow is invoked when the widget is displayed on the app window. Override this method for example to reset
	 * values when the widget is shown.
	 */
	public func onShow() {
	}

	//MARK: SERVER RESPONSE TEMPLATE METHODS

	/*
	 * onServerError is invoked when there is an error communicating with the Liferay server.
	 */
	public func onServerError(error: NSError) {
	}

	/*
	 * onServerResult is invoked when there is an result from a communication with the Liferay server. The type of the
	 * result will depend on the invocation done from specific subclasses.
	 */
	public func onServerResult(dict:[String:AnyObject]) {
	}

	//MARK: USER ACTIONS TEMPLATE METHOD

	/*
	 * onCustomAction is invoked when a TouchUpInside even is fired from the UI.
	 */
	public func onCustomAction(actionName:String?, sender:UIControl) {
	}

	//MARK: UIView METHODS

	override func awakeFromNib() {
		self.clipsToBounds = true;

		widgetView = loadWidgetView();

		onCreate()
	}

	override func becomeFirstResponder() -> Bool {
		return widgetView!.becomeFirstResponder()
	}

	override func didMoveToWindow() {
		if (self.window) {
			self.onShow();
		}
		else {
			self.onHide();
		}
	}

	//MARK: Interface Builder management methods

	override func prepareForInterfaceBuilder() {
		_currentPreviewImage = previewImageForTheme("default")
	}

	override func layoutSubviews() {
		super.layoutSubviews()

		if _runningOnInterfaceBuilder {
			if let currentPreviewImageValue = _currentPreviewImage {
				let imageRect = CGRectMake(0, 0, currentPreviewImageValue.size.width, currentPreviewImageValue.size.height)

				_previewLayer.bounds = imageRect
				_previewLayer.position = CGPointMake(bounds.size.width/2.0, bounds.size.height/2.0)

				_previewLayer.contents = currentPreviewImageValue.CGImage

				if _previewLayer.superlayer != layer {
					// add to the hierarchy the first time
					layer.addSublayer(_previewLayer)
				}
			}
		}
	}

	//MARK: LRCallback PRIVATE METHODS

	func onFailure(error: NSError!) {
		onServerError(error ? error : NSError(domain: "LiferayWidget", code: 0, userInfo: nil))
	}

	func onSuccess(result: AnyObject!) {
		if let objcDict = result as? NSDictionary {
			onServerResult(result as [String:AnyObject])
		}
		else {
			onServerResult(["result": result])
		}
	}

	//MARK: PRIVATE METHODS

	func loadWidgetView() -> BaseWidgetView? {
		let view = self.createWidgetViewFromNib();

		if let viewValue = view {
			viewValue.frame = CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)
			viewValue.customAction = self.onCustomAction;

			self.addSubview(viewValue)

			return viewValue
		}

		return nil;
	}

	private func createWidgetViewFromNib() -> BaseWidgetView? {
		let viewName = widgetName() + "View"

		let bundle = NSBundle(forClass:self.dynamicType)

		var nibName = viewName + "-" + currentThemeName()
		var nibPath = bundle.pathForResource(nibName, ofType:"nib")

		if !nibPath {
			nibName = viewName
			nibPath = bundle.pathForResource(nibName, ofType:"nib")

			if !nibPath {
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

	func currentThemeName() -> String {
		var result = "default"

		if (Theme) {
			let selectedSignatureData = UIImagePNGRepresentation(Theme!)!

			for themeName in ThemeManager.instance().installedThemes() {
				let installedSignatureImage = signatureImageForTheme(themeName)
				let installedSignatureData = UIImagePNGRepresentation(installedSignatureImage)!

				if installedSignatureData.isEqualToData(selectedSignatureData) {
					result = themeName
					break;
				}
			}
		}

		return result
	}

	private func updateCurrentPreviewImage() {
		let themeName = currentThemeName()

		_currentPreviewImage = previewImageForTheme(themeName)
	}

	internal func widgetName() -> String {
		// You may use NSStringFromClass if you annotate all *Widget classes with @objc(*Widget).
		// That way, we have control over underlaying ObjC autogenerated class
		//		let className = NSStringFromClass(self.dynamicType)
		let className = nameOfClass(self.dynamicType)

		return className.componentsSeparatedByString("Widget")[0]
	}

	internal func previewImageForTheme(themeName:String) -> UIImage {
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

		return UIImage(contentsOfFile: fileName)
	}

	private var _runningOnInterfaceBuilder:Bool {
		get {
			return widgetView == nil
		}
	}

	private lazy var _previewLayer: CALayer = {
		return CALayer()
	}()

	private var _currentPreviewImage:UIImage?

}