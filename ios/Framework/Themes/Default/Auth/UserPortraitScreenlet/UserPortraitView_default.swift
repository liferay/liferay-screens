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


public class UserPortraitView_default: BaseScreenletView,
		UserPortraitViewModel,
		UIActionSheetDelegate,
		UIImagePickerControllerDelegate, UINavigationControllerDelegate {

	@IBOutlet weak public var activityIndicator: UIActivityIndicatorView?
	@IBOutlet weak public var portraitImage: UIImageView?
	@IBOutlet weak var editButton: UIButton!

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
	override public var editable: Bool {
		didSet {
			self.editButton.hidden = !editable
			if editable {
				self.superview?.clipsToBounds = false
			}
		}
	}

	public var portraitURL: NSURL? {
		get {
			return loadedURL
		}
		set {
			if let urlValue = newValue {
				loadPortrait(URL: urlValue)
			}
			else {
				loadPlaceholder()
			}
		}
	}

	public var portraitLoaded: ((UIImage?, NSError?) -> (UIImage?))?


	private(set) var loadedURL: NSURL?

	private let imagePicker = UIImagePickerController()


	//MARK: BaseScreenletView

	override public func onCreated() {
		super.onCreated()

		imagePicker.delegate = self
		imagePicker.allowsEditing = true
		imagePicker.modalPresentationStyle = .FullScreen
	}

	override public func onStartOperation() {
		objc_sync_enter(self)

		// use tag to track the start count
		if activityIndicator?.tag == 0 {
			activityIndicator?.startAnimating()
		}

		activityIndicator?.tag++

		objc_sync_exit(self)
	}

	override public func onFinishOperation() {
		if activityIndicator?.tag > 0 {
			objc_sync_enter(self)

			activityIndicator?.tag--

			if activityIndicator?.tag == 0 {
				activityIndicator?.stopAnimating()
			}

			objc_sync_exit(self)
		}
	}

	override public func onShow() {
		portraitImage?.layer.borderWidth = borderWidth
		portraitImage?.layer.borderColor = (borderColor ?? DefaultThemeBasicBlue).CGColor
		portraitImage?.layer.cornerRadius = DefaultThemeButtonCornerRadius
	}

	override public func onPreAction(#name: String?, sender: AnyObject?) -> Bool {
		if name == "edit-portrait" {

			let takeNewPicture = LocalizedString("default", "userportrait-take-new-picture", self)
			let chooseExisting = LocalizedString("default", "userportrait-choose-existing-picture", self)

			let sheet = UIActionSheet(
					title: "Change portrait",
					delegate: self,
					cancelButtonTitle: "Cancel",
					destructiveButtonTitle: nil, otherButtonTitles: takeNewPicture, chooseExisting)
			sheet.showInView(self)

			return false
		}

		return true
	}

	public func actionSheet(
			actionSheet: UIActionSheet,
			clickedButtonAtIndex buttonIndex: Int) {

		let newPicture = 1
		let chooseExisting = 2

		switch buttonIndex {
		case newPicture:
			imagePicker.sourceType = .Camera

		case chooseExisting:
			imagePicker.sourceType = .SavedPhotosAlbum

		default:
			return
		}

		if let vc = self.presentingViewController {
			vc.presentViewController(imagePicker, animated: true, completion: {})
		}
		else {
			println("ERROR: You neet to set the presentingViewController before using UIActionSheet")
		}
	}

	public func loadPlaceholder() {
		self.portraitImage?.image = NSBundle.imageInBundles(
				name: "default-portrait-placeholder",
				currentClass: self.dynamicType)
	}


	public func loadPortrait(URL url: NSURL) {
		// ignore AFNetworking's cache by now
		// TODO contribute to UIImageView+AFNetworking to support "If-Modified-Since" header
		let request = NSURLRequest(
				URL: url,
				cachePolicy: .ReloadIgnoringLocalCacheData,
				timeoutInterval: 60.0)

		onStartOperation()

		portraitImage?.setImageWithURLRequest(request, placeholderImage: nil, success: {
			(request: NSURLRequest!, response: NSHTTPURLResponse!, image: UIImage!) -> Void in
				self.loadedURL = url

				if self.portraitLoaded == nil {
					self.portraitImage?.image = image
				}
				else {
					if let finalImageValue = self.portraitLoaded!(image, nil) {
						self.portraitImage?.image = finalImageValue
					}
					else {
						self.portraitImage?.image = image
					}
				}

				self.onFinishOperation()

			},
			failure: {
				(request: NSURLRequest!, response: NSHTTPURLResponse!, error: NSError!) -> Void in
					self.loadPlaceholder()
					self.loadedURL = nil
					self.portraitLoaded?(nil, error)
					self.onFinishOperation()
			})
	}


	//MARK: UIImagePickerControllerDelegate

    public func imagePickerController(
			picker: UIImagePickerController,
			didFinishPickingMediaWithInfo info: [NSObject : AnyObject]) {

		let editedImage = info[UIImagePickerControllerEditedImage] as? UIImage

		imagePicker.dismissViewControllerAnimated(true) {}

		userAction(name: "upload-portrait", sender: editedImage)
	}


    public func imagePickerControllerDidCancel(picker: UIImagePickerController) {
		imagePicker.dismissViewControllerAnimated(true) {}
	}

}
