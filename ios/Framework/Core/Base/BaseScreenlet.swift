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


@objc public protocol BaseScreenletDelegate: NSObjectProtocol {


	/// Called when we want to return a custom interactor (use case) with the given action name.
	///
	/// - Parameters:
	///   - screenlet
	///   - customInteractorForAction: action name.
	///   - withSender: source of the event.
	/// - Returns: custom interactor.
	optional func screenlet(screenlet: BaseScreenlet,
		customInteractorForAction: String,
		withSender: AnyObject?) -> Interactor?

}


/// BaseScreenlet is the base class from which all Screenlet classes must inherit.
/// A screenlet is the container for a screenlet view.
@IBDesignable public class BaseScreenlet: UIView {

	public static let DefaultAction = "defaultAction"
	public static let DefaultThemeName = "default"

	@IBOutlet public weak var delegate: BaseScreenletDelegate?

	@IBInspectable public var themeName: String? {
		set {
			_themeName = (newValue ?? BaseScreenlet.DefaultThemeName).lowercaseString

			if _runningOnInterfaceBuilder {
				_themeName = updateCurrentPreviewImage()
			}
			else {
				onPreCreate()
				loadScreenletView()
				onCreated()
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

	internal var isRunningOnInterfaceBuilder: Bool {
		return _runningOnInterfaceBuilder
	}

	private var _themeName = BaseScreenlet.DefaultThemeName
	private var _runningOnInterfaceBuilder = false
	private var _currentPreviewImage: UIImage?
	private var _previewLayer: CALayer?

	private var _runningInteractors = [String:[Interactor]]()

	private var _progressPresenter: ProgressPresenter?
	
	
	//MARK: Initializers

	/// Initializer for instantiate screenlets from code with its frame and theme name.
	///
	/// - Parameters:
	///   - frame: size and position of the screenlet view.
	///   - themeName: name of the theme to be used. If nil, default theme will be used.
	public init(frame: CGRect, themeName: String?) {
		super.init(frame: frame)
		
		clipsToBounds = true
		
		self.themeName = themeName
	}

	override convenience init(frame: CGRect) {
		self.init(frame: frame, themeName: nil)
	}

	required public init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: UIView

	override public func awakeFromNib() {
		super.awakeFromNib()

		clipsToBounds = true

		if themeName == BaseScreenlet.DefaultThemeName {
			onPreCreate()
			loadScreenletView()
		presentingViewController = UIApplication.sharedApplication().keyWindow?.rootViewController

			onCreated()
		}
	}

	override public func becomeFirstResponder() -> Bool {
		return screenletView!.becomeFirstResponder()
	}

	override public func didMoveToWindow() {
		if window != nil {
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

	internal func loadScreenletView() {
		let view = createScreenletViewFromNib()

		if let viewValue = view {
			viewValue.onPerformAction = { [weak self] name, sender in
				return self!.performAction(name: name, sender: sender)
			}

			viewValue.screenlet = self
			viewValue.presentingViewController = self.presentingViewController
			viewValue.themeName = _themeName
			
			if let oldView = self.screenletView {
				oldView.removeFromSuperview()
			}

			self._progressPresenter = viewValue.createProgressPresenter()
			self.screenletView = viewValue
			
			viewValue.translatesAutoresizingMaskIntoConstraints = false

			addSubview(viewValue)
			sendSubviewToBack(viewValue)
			
			//Pin all edges from Screenlet View to the Screenlet's edges
			let top = NSLayoutConstraint(item: viewValue, attribute: .Top, relatedBy: .Equal,
			                             toItem: self, attribute: .Top, multiplier: 1, constant: 0)
			let bottom = NSLayoutConstraint(item: viewValue, attribute: .Bottom, relatedBy: .Equal,
			                                toItem: self, attribute: .Bottom, multiplier: 1, constant: 0)
			let leading = NSLayoutConstraint(item: viewValue, attribute: .Leading, relatedBy: .Equal,
			                                 toItem: self, attribute: .Leading, multiplier: 1, constant: 0)
			let trailing = NSLayoutConstraint(item: viewValue, attribute: .Trailing, relatedBy: .Equal,
			                                  toItem: self, attribute: .Trailing, multiplier: 1, constant: 0)
			
			NSLayoutConstraint.activateConstraints([top, bottom, leading, trailing])
			
			viewValue.layoutIfNeeded()
		}
		else {
			self._progressPresenter = nil
			self.screenletView = nil
		}
	}

	/// previewImageForTheme loads the preview image for the screenlet with the given theme.
	///
	/// - Parameter themeName: screenlet theme.
	/// - Returns: preview image.
	internal func previewImageForTheme(themeName:String) -> UIImage? {
		let bundles = NSBundle.allBundles(self.dynamicType)

		for b in bundles {
			let imageName = "\(themeName)-preview-\(ScreenletName(self.dynamicType).lowercaseString)@2x"

			if let imagePath = b.pathForResource(imageName, ofType: "png") {
				if let imageData = NSData(contentsOfFile: imagePath) {
					return UIImage(data: imageData, scale: 2.0)
				}
			}
		}

		return nil
	}


	//MARK: Templated/event methods: intended to be overwritten by children classes

	/// onCreated is invoked after the screenlet is created.
	/// Override this method to set custom values for the screenlet properties.
	public func onCreated() {
	}

	/// onPreCreate is invoked before the screenlet is created.
	/// Override this method to set create new UI components programatically.
	public func onPreCreate() {
	}

	/// onHide is invoked when the screenlet is hidden from the app window.
	public func onHide() {
	}

	/// onShow is invoked when the screenlet is displayed on the app window.
	/// Override this method for example to reset values when the screenlet is shown.
	public func onShow() {
	}

	/// performAction is invoked when we want to start an interaction (use case)
	/// Typically, it's called from TouchUpInside UI event or when the programmer wants to
	/// start the interaction programatically.
	///
	/// - Parameters:
	///   - name: action name.
	///   - sender: source of the event.
	/// - Returns: false interactor ready to be started.
	public func performAction(name name: String, sender: AnyObject? = nil) -> Bool {
		guard !isRunningOnInterfaceBuilder else {
			return false
		}

		let customInteractor = self.delegate?.screenlet?(self,
				customInteractorForAction: name,
				withSender: sender)

		let standardInteractor = self.createInteractor(
				name: name,
				sender: sender)

		if let interactor = customInteractor ?? standardInteractor {
			trackInteractor(interactor, withName: name)

			if let message = screenletView?.progressMessageForAction(name, messageType: .Working) {
				showHUDWithMessage(message, forInteractor: interactor)
			}

			return onAction(name: name, interactor: interactor, sender: sender)
		}

		return false
	}

	/// performDefaultAction is invoked when we want to start the default use case.
	///
	/// - Returns: call to performAction with the default action.
	public func performDefaultAction() -> Bool {
		return performAction(name: BaseScreenlet.DefaultAction, sender: nil)
	}


	/// onAction is invoked when an interaction should be started.
	///
	/// - Parameters:
	///   - name: action name.
	///   - interactor: custom or standard interactor.
	///   - sender: source of the event.
	/// - Returns: call for starting the interactor.
	public func onAction(name name: String, interactor: Interactor, sender: AnyObject?) -> Bool {
		onStartInteraction()
		screenletView?.onStartInteraction()

		return interactor.start()
	}

	/// isActionRunnung checks if there is another action running at the same time.
	///
	/// - Parameter name: action name.
	/// - Returns: true if there is another one running.
	public func isActionRunning(name: String) -> Bool {
		var firstInteractor: Interactor? = nil

		synchronized(_runningInteractors) {
			firstInteractor = self._runningInteractors[name]?.first
		}

		return firstInteractor != nil
	}

	/// cancelInteractorsForAction cancels all the interactors with the given action name.
	///
	/// - Parameter name: action name.
	public func cancelInteractorsForAction(name: String) {
		let interactors = _runningInteractors[name] ?? []

		for interactor in interactors {
			interactor.cancel()
		}
	}

	/// createInteractor creates the proper interactor with the given action name.
	///
	/// - Parameters:
	///   - name: action name.
	///   - sender: source of the event.
	/// - Returns: interactor.
	public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		return nil
	}

	/// endInteractor is invoked when an interaction should be ended.
	///
	/// - Parameters:
	///   - interactor: a started interactor.
	///   - error: nil if there is no error.
	public func endInteractor(interactor: Interactor, error: NSError?) {

		func getMessage() -> String? {
			if let error = error as? ValidationError {
				return error.localizedDescription
			}

			return screenletView?.progressMessageForAction(
					interactor.actionName ?? BaseScreenlet.DefaultAction,
					messageType: error == nil ? .Success : .Failure)
		}

		untrackInteractor(interactor)

		let result: AnyObject? = interactor.interactionResult()
		onFinishInteraction(result, error: error)
		screenletView?.onFinishInteraction(result, error: error)
		hideHUDWithMessage(getMessage(), forInteractor: interactor, withError: error)
	}

	/// onStartInteraction is called just before a screenlet request is sent to server.
	public func onStartInteraction() {
	}

	/// onFinishInteraction is called when the server response arrives
	public func onFinishInteraction(result: AnyObject?, error: NSError?) {
	}


	//MARK: HUD methods

	/// showHUDWithMessage shows a HUD with the given message for the given use case.
	///
	/// - Parameters:
	///   - message: HUD message.
	///   - interactor: interaction (use case).
	public func showHUDWithMessage(message: String?,
			forInteractor interactor: Interactor) {
		
		_progressPresenter?.showHUDInView(rootView(self),
			message: message,
			forInteractor: interactor)
	}

	/// hideHUDWithMessage hides a HUD with the given message for the given use case with an error.
	///
	/// - Parameters:
	///   - message: HUD message.
	///   - interactor: interaction (use case).
	///   - error: interaction error.
	public func hideHUDWithMessage(message: String?,
			forInteractor interactor: Interactor,
			withError error: NSError?) {
		
		_progressPresenter?.hideHUDFromView(rootView(self),
			message: message,
			forInteractor: interactor,
			withError: error)
	}


	//MARK: Public methods

	/// refreshTranslations refreshes all translations of the screenlet view.
	public func refreshTranslations() {
		screenletView?.onSetTranslations()
	}


	//MARK: Private methods

	private func createScreenletViewFromNib() -> BaseScreenletView? {

		let viewName = "\(ScreenletName(self.dynamicType))View"

		if let foundView = NSBundle.viewForThemeOrDefault(
				name: viewName,
				themeName: _themeName,
				currentClass: self.dynamicType) as? BaseScreenletView {

			return foundView
		}

		print("ERROR: Xib file doesn't found for screenlet '\(ScreenletName(self.dynamicType))' and theme '\(_themeName)'\n")

		return nil
	}

	private func updateCurrentPreviewImage() -> String {
		var appliedTheme = _themeName

		_currentPreviewImage = previewImageForTheme(_themeName)
		if _currentPreviewImage == nil {
			if let previewImage = previewImageForTheme(BaseScreenlet.DefaultThemeName) {
				_currentPreviewImage = previewImage
				appliedTheme = BaseScreenlet.DefaultThemeName
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
			loadScreenletView()
		}

		setNeedsLayout()

		return appliedTheme
	}

	private func rootView(currentView:UIView) -> UIView {
		if currentView.superview == nil {
			return currentView;
		}

		return rootView(currentView.superview!)
	}

	private func trackInteractor(interactor: Interactor, withName name: String) {
		synchronized(_runningInteractors) {
			var interactors = self._runningInteractors[name]
			if interactors?.count ?? 0 == 0 {
				interactors = [Interactor]()
			}

			interactors?.append(interactor)

			self._runningInteractors[name] = interactors
			interactor.actionName = name
		}
	}

	private func untrackInteractor(interactor: Interactor) {
		synchronized(_runningInteractors) {
			let name = interactor.actionName!
			let interactors = self._runningInteractors[name] ?? []

			if let foundIndex = interactors.indexOf(interactor) {
				self._runningInteractors[name]?.removeAtIndex(foundIndex)
			}
			else {
				print("ERROR: There's no interactors tracked for name \(interactor.actionName!)\n")
			}
		}
	}

}
