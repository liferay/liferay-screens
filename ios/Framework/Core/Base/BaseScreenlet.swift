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

			screenletView?.themeName = _themeName
		}
		get {
			return _themeName
		}
	}

	public weak var screenletView: BaseScreenletView?

	public weak var presentingViewController: UIViewController? {
		didSet {
			screenletView?.presentingViewController = self.presentingViewController
		}
	}

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

			viewValue.onPerformAction = { [weak self] name, sender in
				return self!.performAction(name: name, sender: sender)
			}

			viewValue.presentingViewController = self.presentingViewController
			viewValue.themeName = _themeName

			viewValue.presentingViewController = self.presentingViewController

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
	public func onCreated() {
	}

	/*
	 * onPreCreate is invoked before the screenlet is created.
	 * Override this method to set create new UI components programatically.
	 *
	 */
	public func onPreCreate() {
	}

	/*
	 * onHide is invoked when the screenlet is hidden from the app window.
	 */
	public func onHide() {
	}

	/*
	 * onShow is invoked when the screenlet is displayed on the app window. 
	 * Override this method for example to reset values when the screenlet is shown.
	 */
	public func onShow() {
	}

	/*
	 * performAction is invoked when a we want to start an interaction (use case)
	 * Typically, it's called from TouchUpInside UI event or when the programmer wants to
	 * start the interaction programatically.
	 */
	public func performAction(#name: String?, sender: AnyObject? = nil) -> Bool {
		if let interactor = createInteractor(name: name, sender: sender) {
			_runningInteractors.append(interactor)

			return onAction(name: name, interactor: interactor, sender: sender)
		}

		println("WARN: No interactor created for action \(name)")

		return false
	}

	public func performDefaultAction() -> Bool {
		return performAction(name: nil, sender: nil)
	}

	/*
	 * onAction is invoked when an interaction should be started
	 */
	public func onAction(#name: String?, interactor: Interactor, sender: AnyObject?) -> Bool {
		return interactor.start()
	}

	public func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		return nil
	}

	public func endInteractor(interactor: Interactor) {
		synchronized(_runningInteractors) {
			if let foundIndex = find(self._runningInteractors, interactor) {
				self._runningInteractors.removeAtIndex(foundIndex)
			}
		}
	}

	/**
	 * onStartOperation is called just before a screenlet request is sent to server
	 */
	public func onStartOperation() {
	}

	/**
	 * onFinishOperation is called when the server response arrives
	 */
	public func onFinishOperation() {
	}


	//MARK: Private

	private func createScreenletViewFromNib() -> BaseScreenletView? {

		func tryLoadForTheme(themeName: String, inBundles bundles: [NSBundle]) -> BaseScreenletView? {
			for bundle in bundles {
				let viewName = self.screenletName + "View"
				var nibName = "\(viewName)_\(themeName)"
				var nibPath = bundle.pathForResource(nibName, ofType:"nib")

				if nibPath != nil {
					let views = bundle.loadNibNamed(nibName,
							owner:self,
							options:nil)

					assert(views.count > 0, "Malformed xib \(nibName). Without views")

					return (views[0] as? BaseScreenletView)
				}
			}

			return nil;
		}

		let bundles = NSBundle.allBundles(self.dynamicType);

		if let foundView = tryLoadForTheme(_themeName, inBundles: bundles) {
			return foundView
		}

		if let foundView = tryLoadForTheme("default", inBundles: bundles) {
			return foundView
		}

		println("ERROR: Xib file doesn't found for screenlet '\(self.screenletName)' and theme '\(_themeName)'")

		return nil
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
