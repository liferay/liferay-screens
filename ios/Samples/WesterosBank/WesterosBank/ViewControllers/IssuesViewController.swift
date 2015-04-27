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

class IssuesViewController: CardViewController, DDLListScreenletDelegate {

	@IBOutlet weak var screenlet: DDLListScreenlet!

	override init(card: CardView, nibName: String) {
		super.init(card: card, nibName: nibName)
	}

	convenience init(card: CardView) {
		self.init(card: card, nibName:"IssuesViewController")
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	override func viewDidLoad() {
		screenlet.delegate = self
	}

	override func viewWillAppear(animated: Bool) {
		screenlet.loadList()
	}

}
