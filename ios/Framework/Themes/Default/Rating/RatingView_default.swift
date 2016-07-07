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

public class RatingThumbsView_default: BaseScreenletView, RatingViewModel {
    
    @IBOutlet weak var negativeButton: UIButton!
    @IBOutlet weak var possitiveButton: UIButton!
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
            self.negativeCountLabel.text = "Total: \(ratingEntry!.ratings[0])"
            self.possitiveCountLabel.text = "Total: \(ratingEntry!.ratings[1])"
            
            let possitiveImage = NSBundle.imageInBundles(
                name: "default-thumb-up",
                currentClass: UserPortraitView_default.self)?.imageWithRenderingMode(.AlwaysTemplate)
            let negativeImage = NSBundle.imageInBundles(
                name: "default-thumb-down",
                currentClass: UserPortraitView_default.self)?.imageWithRenderingMode(.AlwaysTemplate)
            self.possitiveButton.setBackgroundImage(possitiveImage, forState: .Normal)
            self.negativeButton.setBackgroundImage(negativeImage, forState: .Normal)
            
            if ratingEntry!.userScore == -1 {
                self.possitiveButton.tintColor = UIColor.grayColor()
                self.negativeButton.tintColor = UIColor.grayColor()
                self.possitiveButton.restorationIdentifier = RatingScreenlet.UpdateRatingAction
                self.negativeButton.restorationIdentifier = RatingScreenlet.UpdateRatingAction
            } else if ratingEntry!.userScore == 0 {
                self.possitiveButton.tintColor = UIColor.grayColor()
                self.negativeButton.tintColor = DefaultThemeBasicBlue
                self.possitiveButton.restorationIdentifier = RatingScreenlet.UpdateRatingAction
                self.negativeButton.restorationIdentifier = RatingScreenlet.DeleteRatingAction
            } else {
                self.possitiveButton.tintColor = DefaultThemeBasicBlue
                self.negativeButton.tintColor = UIColor.grayColor()
                self.possitiveButton.restorationIdentifier = RatingScreenlet.DeleteRatingAction
                self.negativeButton.restorationIdentifier = RatingScreenlet.UpdateRatingAction
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
