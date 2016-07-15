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

	public var defaultRatingsGroupCount: Int32 {
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
				emojis.forEach({
					$0.alpha = 0.5
					$0.restorationIdentifier = RatingScreenlet.UpdateRatingAction
					$0.addTarget(self, action: #selector(emojiClicked), forControlEvents: .TouchUpInside)
				})

				for i in 0 ..< emojis.count {
					labels[i].text = "\(emojis[i].titleLabel!.text!) \(rating.ratings[i])"
				}

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
