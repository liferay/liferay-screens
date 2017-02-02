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

#if LIFERAY_SCREENS_FRAMEWORK
	import AFNetworking
#endif


// Global initial load
private func loadPlaceholderCache(done: (UIImage? -> ())? = nil) -> UIImage? {
	var image: UIImage?

	dispatch_async {
		image = NSBundle.imageInBundles(
			name: "default-portrait-placeholder",
			currentClass: UserPortraitView_default.self)

		UserPortraitView_default.defaultPlaceholder = image

		dispatch_main() {
			done?(image)
		}
	}

	// returns nil because the loading is asynchronous
	return nil
}


public class UserPortraitView_default: BaseScreenletView,
	UserPortraitViewModel,
	UIActionSheetDelegate,
	UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	public static var defaultPlaceholder: UIImage? = loadPlaceholderCache()


	//MARK: Outlets

	@IBOutlet weak public var activityIndicator: UIActivityIndicatorView?

	@IBOutlet weak public var portraitImage: UIImageView?

	@IBOutlet weak var editButton: UIButton?

	override public var editable: Bool {
		didSet {
			self.editButton?.hidden = !editable
			if editable {
				self.superview?.clipsToBounds = false
			}
		}
	}

	override public var progressMessages: [String:ProgressMessages] {
		return [
			"load-portrait" : [
				.Working : ""
			],
			"upload-portrait" : [
				.Working : "",
				.Failure : LocalizedString("default", key: "userportrait-uploading-error", obj: self)
			]]
	}

	private let imagePicker = UIImagePickerController()

	//MARK: SignUpViewModel

	public var image: UIImage? {
		get {
			return portraitImage?.image
		}
		set {
			if let image = newValue {
				portraitImage?.image = image
			}
			else {
				loadPlaceholder()
			}
		}
	}

	public var borderWidth: CGFloat = 1.0 {
		didSet {
			portraitImage?.layer.borderWidth = borderWidth
		}
	}

	public var borderColor: UIColor? {
		didSet {
			portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		}
	}


	//MARK: BaseScreenletView

	override public func createProgressPresenter() -> ProgressPresenter {
		return UserPortraitDefaultProgressPresenter(spinner: activityIndicator!)
	}

	override public func onCreated() {
		super.onCreated()

		imagePicker.delegate = self
		imagePicker.allowsEditing = true
		imagePicker.modalPresentationStyle = .FullScreen
	}

	override public func onShow() {
		portraitImage?.layer.borderWidth = borderWidth
		portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		portraitImage?.layer.cornerRadius = DefaultThemeButtonCornerRadius
	}

	override public func onPreAction(name name: String, sender: AnyObject?) -> Bool {
		if name == "edit-portrait" {
			let takeNewPicture = LocalizedString("default", key: "userportrait-take-new-picture", obj: self)
			let chooseExisting = LocalizedString("default", key: "userportrait-choose-existing-picture", obj: self)

			let alert = MediaSelector(
					viewController: self.presentingViewController!,
					types: [.ImageEdited : chooseExisting, .Camera : takeNewPicture],
					cancelMessage: "Cancel",
					alertTitle: "Change portrait") { (image, _) in

				self.userAction(name: "upload-portrait", sender: image)
			}

			alert.show()

			return false
		}

		return true
	}


	//MARK: Public methods

	public func loadPlaceholder() {
		dispatch_main() {
			if let placeholder = UserPortraitView_default.defaultPlaceholder {
				self.portraitImage?.image = placeholder
			}
			else {
				loadPlaceholderCache {
					self.portraitImage?.image = $0
				}
			}
		}
	}

}
