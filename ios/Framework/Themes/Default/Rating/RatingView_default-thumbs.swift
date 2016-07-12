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

public class RatingView_default_thumbs: BaseScreenletView, RatingViewModel {
	
	@IBOutlet weak var negativeButton: UIButton! {
		didSet {
			let image = NSBundle.imageInBundles(
				name: "default-thumb-down",
				currentClass: RatingView_default_thumbs.self)?.imageWithRenderingMode(.AlwaysTemplate)
			self.negativeButton.setBackgroundImage(image, forState: .Normal)
		}
	}
	
	@IBOutlet weak var possitiveButton: UIButton! {
		didSet {
			let image = NSBundle.imageInBundles(
				name: "default-thumb-up",
				currentClass: RatingView_default_thumbs.self)?.imageWithRenderingMode(.AlwaysTemplate)
			self.possitiveButton.setBackgroundImage(image, forState: .Normal)
		}
	}
	
	@IBOutlet weak var negativeCountLabel: UILabel!
	@IBOutlet weak var possitiveCountLabel: UILabel!
	
	public var selectedUserScore: NSNumber?
	
	//MARK: BaseScreenletView
	
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
	
	//MARK: RatingViewModel
	
	public var ratingEntry: RatingEntry? {
		didSet {
			if let rating = ratingEntry {
				self.negativeCountLabel.text = NSString.localizedStringWithFormat(LocalizedString("default", key: "rating-total", obj: self), rating.ratings[0]) as String
				self.possitiveCountLabel.text = NSString.localizedStringWithFormat(LocalizedString("default", key: "rating-total", obj: self), rating.ratings[1]) as String
				
				let score = rating.userScore
				
				self.possitiveButton.tintColor = score > 0 ? DefaultThemeBasicBlue : UIColor.grayColor()
				self.negativeButton.tintColor = score == 0 ? DefaultThemeBasicBlue : UIColor.grayColor()
				
				self.possitiveButton.restorationIdentifier = score > 0 ?
					RatingScreenlet.DeleteRatingAction : RatingScreenlet.UpdateRatingAction
				self.negativeButton.restorationIdentifier = score == 0 ?
					RatingScreenlet.DeleteRatingAction : RatingScreenlet.UpdateRatingAction
			}
		}
	}
	
	@IBAction func possitiveButtonClicked(sender: UIButton) {
		self.selectedUserScore = 1
		self.userActionWithSender(sender)
	}
	@IBAction func negativeButtonClicked(sender: UIButton) {
		self.selectedUserScore = 0
		self.userActionWithSender(sender)
	}
}
