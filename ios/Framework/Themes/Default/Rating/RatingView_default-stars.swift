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

@objc(RatingView_default_stars)
open class RatingView_default_stars: BaseScreenletView, RatingViewModel {

	// MARK: Outlets

	@IBOutlet weak var averageRatingLabel: UILabel?

	@IBOutlet weak var userRatingStars: UIStackView?

	@IBOutlet weak var averageRatingStars: UIStackView?

	fileprivate var selectedUserScore: NSNumber?

	fileprivate let fullStar = Bundle.imageInBundles(name: "default-star", currentClass: RatingView_default_stars.self)

	fileprivate let halfStar = Bundle.imageInBundles(name: "default-star-half",
													 currentClass: RatingView_default_stars.self)

	fileprivate let emptyStar = Bundle.imageInBundles(name: "default-star-outline",
													  currentClass: RatingView_default_stars.self)

	// MARK: Actions

	@IBAction func updateUserRating(_ sender: UIButton) {

		let score = Double(sender.tag) / Double(defaultRatingsGroupCount)

		if self.selectedUserScore != NSNumber(value: score) {
			self.userAction(name: RatingScreenlet.UpdateRatingAction, sender: score)
		}
	}

	// MARK: BaseScreenletView

	override open func createProgressPresenter() -> ProgressPresenter {
		return NetworkActivityIndicatorPresenter()
	}

	override open var progressMessages: [String: ProgressMessages] {
		return [
			RatingScreenlet.LoadRatingsAction: [.working: ""],
			RatingScreenlet.UpdateRatingAction: [.working: ""],
			RatingScreenlet.DeleteRatingAction: [.working: ""]
		]
	}

	// MARK: RatingViewModel

	open var defaultRatingsGroupCount: Int32 = 5

	open var ratingEntry: RatingEntry? {
		didSet {
			if let rating = ratingEntry {
				averageRatingLabel?.text = LocalizedPlural("default", keySingular: "rating-ratings.one",
													  keyPlural: "rating-ratings.other", obj: self,
													  count: NSNumber(value: rating.totalCount))

				let userScore = rating.userScore < 0 ? 0 : rating.userScore * Double(defaultRatingsGroupCount)
				let averageScore = rating.average * Double(defaultRatingsGroupCount)

				setScore(score: userScore, stars: userRatingStars?.arrangedSubviews as! [UIButton])
				setScore(score: averageScore, stars: averageRatingStars?.arrangedSubviews as! [UIButton])
			}
		}
	}

	open func setScore(score: Double, stars: [UIButton]) {
		guard score <= Double(defaultRatingsGroupCount) else {
			print("Score cannot be greater of numbers of stars")
			return
		}

		let fullStarsNumber = Int(floor(score))
		let halfStarNumber = (score - Double(fullStarsNumber)) >= 0.5 ? 1 : 0

		let fullStars = stars[0..<fullStarsNumber]
		let halfStars = stars[fullStarsNumber..<fullStarsNumber + halfStarNumber]
		let emptyStars = stars[(fullStarsNumber + halfStarNumber)..<Int(defaultRatingsGroupCount)]

		fullStars.forEach { $0.setBackgroundImage(fullStar, for: .normal) }
		halfStars.forEach { $0.setBackgroundImage(halfStar, for: .normal) }
		emptyStars.forEach { $0.setBackgroundImage(emptyStar, for: .normal) }
	}
}
