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

@IBDesignable public class RatingScreenlet: BaseScreenlet {
    
    public static let DeleteRatingAction = "deleteRating"
    public static let UpdateRatingAction = "updateRating"
    public static let LoadRatingsAction = "loadRatings"
    
    @IBInspectable public var entryId: Int64 = 0
    
    @IBInspectable public var classPK: Int64 = 0
    
    @IBInspectable public var className: String = ""
    
    @IBInspectable public var stepCount: Int32 = 2
    
    @IBInspectable public var autoLoad: Bool = true
    
    @IBInspectable public var editable: Bool = false {
        didSet {
            (screenletView as? RatingViewModel)?.editable = self.editable
        }
    }
    
    public var viewModel: RatingViewModel {
        return screenletView as! RatingViewModel
    }
    
    private func createLoadRatingsInteractor() -> LoadRatingsInteractor {
        let interactor = LoadRatingsInteractor(screenlet: self)
        
        interactor.onSuccess = {
            if let result = interactor.resultRating {
                self.className = result.className
                self.classPK = result.classPK
                
                self.viewModel.ratingEntry = result
            }
        }
        
        interactor.onFailure = {print($0)}
        
        return interactor
    }
    
    private func createDeleteRatingInteractor() -> DeleteRatingInteractor {
        let interactor = DeleteRatingInteractor(screenlet: self)
        
        interactor.onSuccess = {
            self.viewModel.ratingEntry = interactor.resultRating
        }
        
        interactor.onFailure = {print($0)}
        
        return interactor
    }
    
    private func createUpdateRatingInteractor() -> UpdateRatingInteractor {
        let interactor = UpdateRatingInteractor(screenlet: self)
        
        interactor.onSuccess = {
            self.viewModel.ratingEntry = interactor.resultRating
        }
        
        interactor.onFailure = {print($0)}
        
        return interactor
    }
    
    private func validateParameters() -> Bool {
        return entryId != 0 || (classPK != 0 && className != "")
    }
    
    //MARK: Public methods
    
    public override func onShow() {
        if (autoLoad && SessionContext.isLoggedIn && validateParameters()) {
            loadRatings()
        }
    }
    
    override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
        switch name {
        case RatingScreenlet.LoadRatingsAction:
            return createLoadRatingsInteractor()
        case RatingScreenlet.DeleteRatingAction:
            return createDeleteRatingInteractor()
        case RatingScreenlet.UpdateRatingAction:
            return createUpdateRatingInteractor()
        default:
            return nil
        }
    }
    
    public override func performDefaultAction() -> Bool {
        return performAction(name: RatingScreenlet.LoadRatingsAction, sender: nil)
    }
    
    public func loadRatings() -> Bool {
        return self.performDefaultAction()
    }
}
