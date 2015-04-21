//
//  SignUpViewController.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class SignUpViewController: UIViewController {

	var onDone: (Void -> Void)?

	@IBAction func close(sender: AnyObject) {
		onDone?()
	}
	override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	convenience init() {
		self.init(nibName:"SignUpViewController", bundle:nil)
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

}
