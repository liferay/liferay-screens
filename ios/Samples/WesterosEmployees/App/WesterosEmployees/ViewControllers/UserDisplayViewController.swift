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

public class UserDisplayViewController: UIViewController {


	//MARK: Outlets

	@IBOutlet weak var userPortraitScreenlet: UserPortraitScreenlet?
	@IBOutlet weak var userNameLabel: UILabel?
	@IBOutlet weak var jobTitleLabel: UILabel?
	@IBOutlet weak var emailLabel: UILabel?
	@IBOutlet weak var nickNameLabel: UILabel?
	@IBOutlet weak var signOutButton: UIButton? {
		didSet {
			signOutButton?.layer.borderWidth = 3.0
			signOutButton?.layer.borderColor = WesterosThemeBasicRed.CGColor
		}
	}


	//MARK: View actions

	@IBAction func goBackButtonClick() {
		self.dismissViewControllerAnimated(true, completion: nil)
	}

	@IBAction func signOutButtonClick() {
		SessionContext.logout()
		self.dismissViewControllerAnimated(true, completion: nil)
	}


	//MARK: UIViewController

	public override func viewDidLoad() {
		userPortraitScreenlet?.load(userId: SessionContext.currentContext!.userId!)

		let firstName = SessionContext.currentContext!.userAttribute("firstName") as! String
		let middleName = SessionContext.currentContext!.userAttribute("middleName") as! String
		let lastName = SessionContext.currentContext!.userAttribute("lastName") as! String
		userNameLabel?.text = "\(firstName) \(middleName) \(lastName)"
			.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
			.stringByReplacingOccurrencesOfString("  ", withString: " ")

		jobTitleLabel?.text = SessionContext.currentContext!.userAttribute("jobTitle") as? String

		emailLabel?.text = SessionContext.currentContext!.userAttribute("emailAddress") as? String

		nickNameLabel?.text = SessionContext.currentContext!.userAttribute("screenName") as? String
	}

}
