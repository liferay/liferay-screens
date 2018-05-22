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
import LiferayScreens

class UserView: UIView {

	// MARK: Outlets

	@IBOutlet weak var contentView: UIView?
	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var usernameLabel: UILabel?
	@IBOutlet weak var jobTitleLabel: UILabel?
	@IBOutlet weak var emailLabel: UILabel?
	@IBOutlet weak var nicknameLabel: UILabel?

	var user: User? {
		didSet {
			userPortraitScreenlet?.load(userId: user!.userId)
			usernameLabel?.text = "\(user!.firstName) \(user!.lastName)"
			jobTitleLabel?.text = user!.jobTitle
			emailLabel?.text = user!.email
			nicknameLabel?.text = user!.screenName
		}
	}

	// MARK: Initializers

	override init(frame: CGRect) {
		super.init(frame: frame)
		setup()
	}

	required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
		setup()
	}

	// MARK: Private methods

	fileprivate func setup() {
		let nib = Bundle.main.loadNibNamed("UserView", owner: self, options: nil)
		if let view = nib?.last as? UIView {
			self.contentView = view
			self.addSubview(view)

			view.translatesAutoresizingMaskIntoConstraints = false

			//Pin all edges from Screenlet View to the Screenlet's edges
			let top = NSLayoutConstraint(item: view, attribute: .top, relatedBy: .equal,
			                             toItem: self, attribute: .top, multiplier: 1, constant: 0)
			let bottom = NSLayoutConstraint(item: view, attribute: .bottom, relatedBy: .equal,
			                                toItem: self, attribute: .bottom, multiplier: 1, constant: 0)
			let leading = NSLayoutConstraint(item: view, attribute: .leading, relatedBy: .equal,
			                                 toItem: self, attribute: .leading, multiplier: 1, constant: 0)
			let trailing = NSLayoutConstraint(item: view, attribute: .trailing, relatedBy: .equal,
			                                  toItem: self, attribute: .trailing, multiplier: 1, constant: 0)

			NSLayoutConstraint.activate([top, bottom, leading, trailing])
		}
	}
}
