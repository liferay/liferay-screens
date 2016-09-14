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

class CommentsViewController: CardViewController, CardDeckDelegate, CardDeckDataSource {

	@IBOutlet weak var commentListScreenlet: CommentListScreenlet?

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}

	var className: String?
	var classPK: Int64?



	//MARK: Init methods

	convenience init() {
		self.init(nibName: "CommentsViewController", bundle: nil)
	}

	func load () {
		commentListScreenlet?.className = self.className!
		commentListScreenlet?.classPK = self.classPK!
		commentListScreenlet?.loadList()
	}
}
