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

private let xibName = "CommentEditViewController_default"

public class CommentEditViewController_default: UIViewController, UITextViewDelegate {

	@IBOutlet public var bodyTextView: UITextView?
	@IBOutlet public var confirmButton: UIButton?
	@IBOutlet public var cancelButton: UIButton?
	@IBOutlet public var scrollView: UIScrollView?

	public var confirmBodyClosure: (String? -> Void)?

	private var placeholderLabel : UILabel!

	private var initialBody: String?


	//MARK: Initializers

	public override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	public convenience init(body: String?) {
		func bundleForXib() -> NSBundle? {
			let bundles = NSBundle.allBundles(CommentEditViewController_default.self);

			return bundles.filter{$0.pathForResource(xibName, ofType:"nib") != nil}.first
		}

		self.init(nibName: xibName, bundle: bundleForXib())

		self.initialBody = body
	}

	required public init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}


	//MARK: UIViewController

	public override func viewDidLoad() {
		confirmButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-confirm", obj: self),
			forState: .Normal)
		cancelButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-cancel", obj: self),
			forState: .Normal)

		let notificationCenter = NSNotificationCenter.defaultCenter()
		notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard),
			name: UIKeyboardWillHideNotification, object: nil)
		notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard),
			name: UIKeyboardWillChangeFrameNotification, object: nil)

		if let textView = bodyTextView, font = textView.font {
			textView.text = initialBody
			textView.delegate = self

			//Add an UILabel as the placeholder of the UITextView
			placeholderLabel = UILabel()
			placeholderLabel.text = LocalizedString(
				"default", key: "comment-display-type", obj: self)
			placeholderLabel.font = UIFont.italicSystemFontOfSize(font.pointSize)
			placeholderLabel.sizeToFit()
			placeholderLabel.frame.origin = CGPointMake(5, font.pointSize / 2)
			placeholderLabel.textColor = UIColor(white: 0, alpha: 0.3)
			placeholderLabel.hidden = !textView.text.isEmpty
			textView.addSubview(placeholderLabel)

			bodyTextView?.becomeFirstResponder()
		}
	}


	//MARK: Keyboard action

	func adjustForKeyboard(notification: NSNotification) {
		if let keyboardScreenEndFrame =
				(notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.CGRectValue(),
				scroll = scrollView {

			let keyboardViewEndFrame =
				view.convertRect(keyboardScreenEndFrame, fromView: view.window)

			if notification.name == UIKeyboardWillHideNotification {
				scroll.contentInset = UIEdgeInsetsZero
			} else {
				scroll.contentInset =
					UIEdgeInsets(top: 0, left: 0, bottom: keyboardViewEndFrame.height, right: 0)
			}

			scroll.scrollIndicatorInsets = scroll.contentInset
		}
	}


	//MARK: UITextViewDelegate

	public func textViewDidChange(textView: UITextView) {
		placeholderLabel.hidden = !textView.text.isEmpty
	}


	//MARK: View actions

	@IBAction public func cancelButtonAction() {
		bodyTextView?.resignFirstResponder()
		confirmBodyClosure?(nil)
	}

	@IBAction public func confirmButtonAction() {
		bodyTextView?.resignFirstResponder()
		confirmBodyClosure?(bodyTextView?.text)
	}
}
