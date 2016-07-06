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

public class RatingLikeView_default: BaseScreenletView, RatingViewModel {

    @IBOutlet weak var likeButton: UIButton!
    @IBOutlet weak var countLabel: UILabel!
    
    public var selectedUserScore: NSNumber?
    
    //MARK: BaseScreenletView
    
    public override func createProgressPresenter() -> ProgressPresenter {
        return NetworkActivityIndicatorPresenter()
    }
    
    override public var progressMessages: [String:ProgressMessages] {
        return [
            RatingScreenlet.LoadRatingsAction : [.Working : ""],
            RatingScreenlet.AddRatingAction : [.Working : ""],
            RatingScreenlet.DeleteRatingAction : [.Working : ""],
        ]
    }

    public var ratingEntry: RatingEntry? {
        didSet {
            self.countLabel.text = "Total: \(ratingEntry!.totalCount)"
            
            let image = NSBundle.imageInBundles(
                name: "default-thumb-up",
                currentClass: UserPortraitView_default.self)?.imageWithRenderingMode(.AlwaysTemplate)
            self.likeButton.setBackgroundImage(image, forState: .Normal)
            
            
            if ratingEntry!.userScore == -1 {
                self.likeButton.tintColor = UIColor.grayColor()
                self.likeButton.restorationIdentifier = RatingScreenlet.AddRatingAction
            } else {
                self.likeButton.tintColor = DefaultThemeBasicBlue
                self.likeButton.restorationIdentifier = RatingScreenlet.DeleteRatingAction
            }
            
        }
    }
    
    @IBAction func likeButtonClicked(sender: AnyObject) {
        self.selectedUserScore = self.ratingEntry?.userScore == -1 ? 1 : 0
        self.userActionWithSender(sender)
    }
    
}
