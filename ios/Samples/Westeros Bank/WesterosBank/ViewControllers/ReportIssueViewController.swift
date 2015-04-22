//
//  ReportIssueViewController.swift
//  WesterosBank
//
//  Created by jmWork on 22/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit

class ReportIssueViewController: UIViewController {

	var onDone: (Void -> Void)?

	override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
		super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
	}

	convenience init() {
		self.init(nibName:"ReportIssueViewController", bundle:nil)
	}

	required init(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

}
