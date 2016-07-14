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


class RatingScreenletViewController: UIViewController {

	@IBOutlet weak var screenlet: RatingScreenlet!
	@IBOutlet weak var switchControl: UISwitch!

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
	@IBAction func segmentedControlChanged(sender: UISegmentedControl) {
		switch sender.selectedSegmentIndex {
		case 1:
			screenlet.entryId = 32049
			screenlet.themeName = "default-like"
		case 2:
			screenlet.entryId = 31904
			screenlet.themeName = "default-stars"
		default:
			screenlet.entryId = 31869
			screenlet.themeName = "default-thumbs"
		}

		screenlet.loadRatings()
		screenlet.editable = switchControl.on
	}

    @IBAction func switchChange(sender: UISwitch) {
		screenlet.editable = sender.on
    }
}
