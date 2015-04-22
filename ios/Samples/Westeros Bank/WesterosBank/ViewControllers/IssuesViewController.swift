//
//  IssuesViewController.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class IssuesViewController: UIViewController {

	override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	convenience init() {
		self.init(nibName:"IssuesViewController", bundle:nil)
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

}
