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


public class SignUpView_westeros_view: SignUpView_westeros {

	@IBOutlet public var screenNameField: UITextField?
	@IBOutlet public var jobTitleField: UITextField?


	public override func onSetTranslations() {
		super.onSetTranslations()
		
		signUpButton?.replaceAttributedTitle("SAVE", forState: .Normal)
	}

	public override var screenName: String? {
		get {
			return screenNameField?.text
		}
		set {
			screenNameField?.text = newValue
		}
	}

	public override var jobTitle: String? {
		get {
			return jobTitleField?.text
		}
		set {
			jobTitleField?.text = newValue
		}
	}

}
