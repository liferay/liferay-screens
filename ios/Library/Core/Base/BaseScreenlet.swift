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
			_themeName = (newValue ?? "default").lowercaseString

			if _runningOnInterfaceBuilder {
				_themeName = updateCurrentPreviewImage()
			}
		}
		get {
			return _themeName
		}
	}

	internal var screenletView: BaseScreenletView?

	internal var screenletName: String {
		var className = NSStringFromClass(self.dynamicType)

		className = className.componentsSeparatedByString(".")[1]
		className = className.componentsSeparatedByString("Screenlet")[0]

		return className
	}

	internal var isRunningOnInterfaceBuilder: Bool {
		return _runningOnInterfaceBuilder
	}

	private var _themeName = "default"
	private var _runningOnInterfaceBuilder = false
	private var _currentPreviewImage: UIImage?
	private var _previewLayer: CALayer?

	private var _runningInteractors = [Interactor]()


	//MARK: UIView

	override public func awakeFromNib() {
		super.awakeFromNib()

		onPreCreate()

		clipsToBounds = true

		screenletView = loadScreenletView()

		onCreated()
	}

	override public func becomeFirstResponder() -> Bool {
		return screenletView!.becomeFirstResponder()
	}

	override public func didMoveToWindow() {
		if (window != nil) {
			onShow()
		}
		else {
			onHide()
		}
	}


	//MARK: Interface Builder management methods

	override public func prepareForInterfaceBuilder() {
		_runningOnInterfaceBuilder = true

		_previewLayer = CALayer()

		updateCurrentPreviewImage()
	}

	//MARK: Internal methods

	internal func loadScreenletView() -> BaseScreenletView? {
		let view = createScreenletViewFromNib()

		if let viewValue = view {
			//FIXME: full-autoresize value. Extract from UIViewAutoresizing
			let flexibleMask = UIViewAutoresizing(18)

			if viewValue.autoresizingMask == flexibleMask {
				viewValue.frame = self.bounds
			}
			else {
				viewValue.frame = centeredRectInView(self, size: viewValue.frame.size)
			}

			viewValue.onPerformUserAction = performUserAction

			addSubview(viewValue)
			sendSubviewToBack(viewValue)

			return viewValue
		}

		return nil
	}

	internal func previewImageForTheme(themeName:String) -> UIImage? {
		let bundle = NSBundle(forClass:self.dynamicType)
		let imageName = "\(themeName)-preview-\(screenletName.lowercaseString)@2x"

		if let imagePath = bundle.pathForResource(imageName, ofType: "png") {
			if let imageData = NSData(contentsOfFile: imagePath) {
				return UIImage(data: imageData, scale: 2.0)
			}
		}

		return nil
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
	 * performUserAction is invoked when a TouchUpInside even is fired from the UI.
	 */
	internal func performUserAction(#name: String?, sender: AnyObject? = nil) -> Bool {
		if let interactor = createInteractor(name: name, sender: sender) {
			_runningInteractors.append(interactor)

			return onUserAction(name: name, interactor: interactor, sender: sender)
		}

		println("WARN: No interactor created for action \(name)")

		return false
	}

	internal func performDefaultUserAction() -> Bool {
		return performUserAction(name: nil, sender: nil)
	}

	/*
	 * onUserAction is invoked when an interaction should be started
	 */
	internal func onUserAction(#name: String?, interactor: Interactor, sender: AnyObject?) -> Bool {
		return interactor.start()
	}

	internal func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		return nil
	}

	internal func endInteractor(interactor: Interactor) {
		synchronized(_runningInteractors) {
			if let foundIndex = find(self._runningInteractors, interactor) {
				self._runningInteractors.removeAtIndex(foundIndex)
			}
		}
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
			nibName = "\(viewName)_default"
			nibPath = bundle.pathForResource(nibName, ofType:"nib")

			if nibPath != nil {
				_themeName = "default"
			}
			else {
				println("ERROR: Xib file '\(nibName)' was not found for theme '\(_themeName)'")
				return nil
			}
		}

		let views = bundle.loadNibNamed(nibName, owner:self, options:nil)
		assert(views.count > 0, "Xib seems to be malformed. There're no views inside it")

		let foundView = (views[0] as BaseScreenletView)

		return foundView
	}

	private func updateCurrentPreviewImage() -> String {
		var appliedTheme = _themeName

		_currentPreviewImage = previewImageForTheme(_themeName)
		if _currentPreviewImage == nil {
			if let previewImage = previewImageForTheme("default") {
				_currentPreviewImage = previewImage
				appliedTheme = "default"
			}
		}

		if let screenletViewValue = screenletView {
			screenletViewValue.removeFromSuperview()
		}

		if let currentPreviewImageValue = _currentPreviewImage {
			_previewLayer!.frame = centeredRectInView(self, size: currentPreviewImageValue.size)
			_previewLayer!.contents = currentPreviewImageValue.CGImage

			if _previewLayer!.superlayer != layer {
				layer.addSublayer(_previewLayer!)
			}
		}
		else {
			screenletView = loadScreenletView()
		}

		setNeedsLayout()

		return appliedTheme
	}

}
