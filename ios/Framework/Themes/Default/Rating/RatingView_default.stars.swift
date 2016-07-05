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

public class RatingStarView_default: BaseScreenletView, RatingViewModel {
    
    @IBOutlet weak var userRatingBar: CosmosView! {
        didSet {
            userRatingBar.didFinishTouchingCosmos = {
                let score = $0 / Double(self.userRatingBar.settings.totalStars)
                
                if (self.selectedUserScore != score) {
                    self.selectedUserScore = score
                    self.userAction(name: RatingScreenlet.AddRatingAction)
                }
            }
        }
    }
    
    @IBOutlet weak var averageRatingBar: CosmosView!
        
    override public var editable: Bool {
        didSet {
            
        }
    }
    
    public var selectedUserScore: NSNumber?
    
    public var ratingEntry: RatingEntry? {
        didSet {
            averageRatingBar.rating = ratingEntry!.average * Double(self.averageRatingBar.settings.totalStars)
            averageRatingBar.text = "\(ratingEntry!.totalCount) Ratings"
            userRatingBar.rating = ratingEntry!.userScore * Double(self.userRatingBar.settings.totalStars)
            selectedUserScore = ratingEntry!.userScore
        }
    }
}
