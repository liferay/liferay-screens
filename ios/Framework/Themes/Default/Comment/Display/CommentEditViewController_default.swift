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

public class CommentEditViewController_default: UIViewController {

	@IBOutlet public var bodyTextView: UITextView?
	@IBOutlet public var confirmButton: UIButton?
	@IBOutlet public var cancelButton: UIButton?

	public var updatedBodyClosure: (String? -> Void)?

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

	public override func viewDidLoad() {
		confirmButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-confirm", obj: self),
			forState: .Normal)
		cancelButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-display-cancel", obj: self),
			forState: .Normal)

		bodyTextView?.text = initialBody
	}

	@IBAction public func cancelButtonAction() {
		updatedBodyClosure?(nil)
	}

	@IBAction public func confirmButtonAction() {
		updatedBodyClosure?(bodyTextView?.text)
	}
	
}
