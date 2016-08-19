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


public class ImageUploadDetailView_default : ImageUploadDetailViewBase {

	@IBOutlet weak var scrollView: UIScrollView!

	public override var image: UIImage? {
		didSet {
			imagePreview?.image = image
		}
	}

	public override var imageTitle: String? {
		didSet {
			titleText?.text = imageTitle
		}
	}

	public override func awakeFromNib() {
		initialize()
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

	public override func didMoveToWindow() {
		if window != nil {

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
	}

	public override func willMoveToWindow(newWindow: UIWindow?) {

		NSNotificationCenter.defaultCenter().removeObserver(
				self, name: UIKeyboardWillShowNotification, object: nil)
		NSNotificationCenter.defaultCenter().removeObserver(
				self, name: UIKeyboardWillHideNotification, object: nil)
	}

	public func dismissKeyboard() {
		guard let descripText = descripText, titleText = titleText else {
			return
		}
		if descripText.isFirstResponder() {
			descripText.resignFirstResponder()
		}
		else if titleText.isFirstResponder() {
			titleText.resignFirstResponder()
		}
	}


	//MARK: Notifications

	public func keyboardWillShow(notification: NSNotification) {

		let keyboardHeight =
			notification.userInfo![UIKeyboardFrameBeginUserInfoKey]!.CGRectValue().height

		var scrollNewFrame = scrollView.frame
		scrollNewFrame.size.height = frame.height - keyboardHeight

		scrollView.frame = scrollNewFrame

		let bottomOffset = CGPoint(x: 0, y: scrollView.contentSize.height - scrollView.frame.height)
		scrollView.setContentOffset(bottomOffset, animated: true)
	}

	public func keyboardWillHide(notification: NSNotification) {
		let keyboardHeight =
			notification.userInfo![UIKeyboardFrameBeginUserInfoKey]!.CGRectValue().height

		var scrollNewFrame = scrollView.frame
		scrollNewFrame.size.height = frame.height + keyboardHeight

		scrollView.frame = scrollNewFrame
	}
}
