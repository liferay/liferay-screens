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
import PureLayout

///Base view controller class for view controllers that work inside a card.
///Override this class
class CardViewController: UIViewController {

	var onDone: (Void -> Void)?

	///Card which holds the controlled view of this controller
	let cardView: CardView?

	private var didSetupConstraints = false

	//MARK: Init methods

	///Creates a CardViewController, from a given nib name, inyecting its view into the card
	/// - parameters:
	///    - card: card where the controlled view will be inyected
	///    - nibName: name of the nib for this ViewController
	init(card: CardView, nibName: String) {
		self.cardView = card

		super.init(nibName: nibName, bundle: nil)

		card.addPage(self.view)

		card.presentingController = self
	}

	required init?(coder aDecoder: NSCoder) {
		self.cardView = nil

		super.init(coder: aDecoder)
	}


	//MARK: card view state methods

	///Method triggered when the card is going to appear on screen (maximized and normal states).
	///You should override this method.
	func cardWillAppear() {
	}

	///Method triggered when the card is going to disappear of screen (background and minimized
	///states). You should override this method.
	func cardWillDisappear() {
	}

}
