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

	public var confirmBodyClosure: (String? -> Void)?

	private var placeholderLabel : UILabel!

	private var initialBody: String?

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

	public func textViewDidChange(textView: UITextView) {
		placeholderLabel.hidden = !textView.text.isEmpty
	}

	public override func viewDidLoad() {
		confirmButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-confirm", obj: self),
			forState: .Normal)
		cancelButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-cancel", obj: self),
			forState: .Normal)

		if let textView = bodyTextView {
			textView.text = initialBody
			textView.delegate = self

			//Add an UILabel as the placeholder of the UITextView
			placeholderLabel = UILabel()
			placeholderLabel.text = LocalizedString(
				"default", key: "comment-display-type", obj: self)
			placeholderLabel.font = UIFont.italicSystemFontOfSize(textView.font!.pointSize)
			placeholderLabel.sizeToFit()
			textView.addSubview(placeholderLabel)
			placeholderLabel.frame.origin = CGPointMake(5, textView.font!.pointSize / 2)
			placeholderLabel.textColor = UIColor(white: 0, alpha: 0.3)
			placeholderLabel.hidden = !textView.text.isEmpty

			bodyTextView?.becomeFirstResponder()
		}
	}

	@IBAction public func cancelButtonAction() {
<<<<<<< HEAD
		updatedBodyClosure?(nil)
	}

	@IBAction public func confirmButtonAction() {
		updatedBodyClosure?(bodyTextView?.text)
=======
		bodyTextView?.resignFirstResponder()
		confirmBodyClosure?(nil)
	}

	@IBAction public func confirmButtonAction() {
		bodyTextView?.resignFirstResponder()
		confirmBodyClosure?(bodyTextView?.text)
>>>>>>> eab8708... LSR-859 change closure name in edit view controller
	}
	
}
