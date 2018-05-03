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

class RatingScreenletViewController: UIViewController, RatingScreenletDelegate {

	// MARK: Outlets

	@IBOutlet weak var screenlet: RatingScreenlet? {
		didSet {
			screenlet?.delegate = self
			screenlet?.entryId = LiferayServerContext.longPropertyForKey("ratingThumbsEntryId")
		}
	}

	// MARK: Actions

	@IBAction func segmentedControlChanged(_ sender: UISegmentedControl) {
		switch sender.selectedSegmentIndex {
			case 1:
				screenlet?.entryId = LiferayServerContext.longPropertyForKey("ratingLikeEntryId")
				screenlet?.themeName = "default-like"
				screenlet?.ratingsGroupCount = 1
			case 2:
				screenlet?.entryId = LiferayServerContext.longPropertyForKey("ratingStarsEntryId")
				screenlet?.themeName = "default-stars"
				screenlet?.ratingsGroupCount = 5
			case 3:
				screenlet?.entryId = LiferayServerContext.longPropertyForKey("ratingEmojisEntryId")
				screenlet?.themeName = "default-emojis"
				screenlet?.ratingsGroupCount = 5
			default:
				screenlet?.entryId = LiferayServerContext.longPropertyForKey("ratingThumbsEntryId")
				screenlet?.themeName = "default-thumbs"
				screenlet?.ratingsGroupCount = 2
		}

		screenlet?.loadRatings()
	}

    @IBAction func switchChange(_ sender: UISwitch) {
		screenlet?.editable = sender.isOn
    }

	// MARK: RatingScreenletDelegate

	func screenlet(_ screenlet: RatingScreenlet, onRatingRetrieve rating: RatingEntry) {
		LiferayLogger.logDelegateMessage(args: rating)
	}

	func screenlet(_ screenlet: RatingScreenlet, onRatingDeleted rating: RatingEntry) {
		LiferayLogger.logDelegateMessage(args: rating)
	}

	func screenlet(_ screenlet: RatingScreenlet, onRatingUpdated rating: RatingEntry) {
		LiferayLogger.logDelegateMessage(args: rating)
	}

	func screenlet(_ screenlet: RatingScreenlet, onRatingError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
}
