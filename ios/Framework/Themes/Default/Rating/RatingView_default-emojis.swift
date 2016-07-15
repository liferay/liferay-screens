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
import Cosmos

public class RatingView_default_emojis: BaseScreenletView, RatingViewModel {

	public var selectedUserScore: NSNumber?

	public var defaultStepCount: Int32 {
		return Int32(self.emojis.count)
	}

	var emojis: [UIButton] = []
	var labels: [UILabel] = []

	//MARK: BaseScreenletView

	public override func onCreated() {
		emojis = subviews.map({$0 as? UIButton}).flatMap({$0})
		labels = subviews.map({$0 as? UILabel}).flatMap({$0})
	}

	public override func createProgressPresenter() -> ProgressPresenter {
		return NetworkActivityIndicatorPresenter()
	}

	override public var progressMessages: [String:ProgressMessages] {
		return [
			RatingScreenlet.LoadRatingsAction : [.Working : ""],
			RatingScreenlet.UpdateRatingAction : [.Working : ""],
			RatingScreenlet.DeleteRatingAction : [.Working : ""],
		]
	}

	public var ratingEntry: RatingEntry? {
		didSet {
			if let rating = ratingEntry {
				emojis.map({
					$0.alpha = 0.5
					$0.restorationIdentifier = RatingScreenlet.UpdateRatingAction
					$0.addTarget(self, action: #selector(emojiClicked), forControlEvents: .TouchUpInside)
				})

				zip(zip(labels, emojis), rating.ratings).map({$0.0.0.text = "\($0.0.1.titleLabel!.text!) \($0.1)"})

				if rating.userScore != -1 {
					let index = rating.userScore == 1 ? emojis.count - 1 : Int(rating.userScore * Double(emojis.count))
					emojis[index].alpha = 1.0
					emojis[index].restorationIdentifier = RatingScreenlet.DeleteRatingAction
				}
			}
		}
	}

	func emojiClicked(sender: UIButton) {
		selectedUserScore = Double(emojis.indexOf(sender)!) / Double(emojis.count)
		userAction(name: sender.restorationIdentifier)
	}
}
