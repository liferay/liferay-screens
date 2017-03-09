//
//  UserPortraitView_initials.swift
//  LiferayScreens-Showcase-Swift
//
//  Created by Victor Galán on 07/03/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens

class UserPortraitView_initials: UserPortraitView_default {

	@IBOutlet weak var initalsLabel: UILabel!

	override func loadPlaceholder(for user: User) {
		portraitImage?.image = nil

		let nameInitial = String(user.firstName.characters.first!).uppercased()
		let surnameInitial = String(user.lastName.characters.first!).uppercased()

		initalsLabel.text = "\(nameInitial)\(surnameInitial)"
		initalsLabel.isHidden = false
	}
}
