//
//  CardViewController.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class CardViewController: UIViewController {

	var onDone: (Void -> Void)?

	init(card: CardView, nibName: String) {
		super.init(nibName: nibName, bundle: nil)
		
		self.view.frame = CGRectMake(
				0, card.minimizedHeight,
				card.frame.width, self.view.frame.height)

		card.addSubview(self.view)
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

}
