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

public class ImageUploadDetailViewController_default: ImageUploadDetailViewControllerBase {

	@IBOutlet weak var scrollView: UIScrollView!

	public override var image: UIImage? {
		didSet {
			imagePreview?.image = image
		}
	}

	public override var title: String? {
		didSet {
			titleText?.text = self.title
		}
	}


	// MARK: UIViewController

    override public func viewDidLoad() {
        super.viewDidLoad()

		addNavBarButtons()
		initialize()
    }

	public override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)

		NSNotificationCenter.defaultCenter().addObserver(
				self,
				selector: #selector(keyboardWillShow(_:)),
				name: UIKeyboardWillShowNotification,
				object: nil)

		NSNotificationCenter.defaultCenter().addObserver(
				self,
				selector: #selector(keyboardWillHide(_:)),
				name: UIKeyboardWillHideNotification,
				object: nil)
	}

	public override func viewWillDisappear(animated: Bool) {
		super.viewWillDisappear(animated)

		NSNotificationCenter.defaultCenter().removeObserver(
				self, name: UIKeyboardWillShowNotification, object: nil)
		NSNotificationCenter.defaultCenter().removeObserver(
				self, name: UIKeyboardWillHideNotification, object: nil)
	}


	// MARK: Private methods

	public func addNavBarButtons() {
		let uploadButton = UIBarButtonItem(
				title: "Upload",
				style: .Plain,
				target: self,
				action: #selector(startUploadClick))

		let cancelButton = UIBarButtonItem(
				title: "Cancel",
				style: .Plain,
				target: self,
				action: #selector(cancelClick))

		navigationItem.rightBarButtonItem = uploadButton
		navigationItem.leftBarButtonItem = cancelButton
	}

	public func initialize() {
		descripText?.layer.borderColor = UIColor.grayColor().colorWithAlphaComponent(0.4).CGColor
		descripText?.layer.borderWidth = 0.5
		descripText?.layer.cornerRadius = 7

		let dismissKeyboardGesture = UITapGestureRecognizer(
				target: self,
				action: #selector(dismissKeyboard))

		scrollView.addGestureRecognizer(dismissKeyboardGesture)
	}

	public func dismissKeyboard() {
		guard let descripText = descripText, titleText = titleText
			else {
				return
		}
		if descripText.isFirstResponder() {
			descripText.resignFirstResponder()
		}
		else if titleText.isFirstResponder() {
			titleText.resignFirstResponder()
		}
	}


	// MARK: Actions

	public func startUploadClick() {
		dismissViewControllerAnimated(true) {
			self.startUpload()
		}
	}

	public func cancelClick() {
		dismissViewControllerAnimated(true) {}
	}


	//MARK: Notifications

	func keyboardWillShow(notification: NSNotification) {

		let keyboardHeight =
				notification.userInfo![UIKeyboardFrameBeginUserInfoKey]!.CGRectValue().height

		var scrollNewFrame = scrollView.frame
		scrollNewFrame.size.height = view.frame.height - keyboardHeight

		scrollView.frame = scrollNewFrame

		let bottomOffset = CGPoint(x: 0, y: scrollView.contentSize.height - scrollView.frame.height)
		scrollView.setContentOffset(bottomOffset, animated: true)
	}

	func keyboardWillHide(notification: NSNotification) {
		let keyboardHeight =
				notification.userInfo![UIKeyboardFrameBeginUserInfoKey]!.CGRectValue().height

		var scrollNewFrame = scrollView.frame
		scrollNewFrame.size.height = view.frame.height + keyboardHeight

		scrollView.frame = scrollNewFrame
	}


}
