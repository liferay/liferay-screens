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

public class AddRatingInteractor: Interactor, LRCallback {
    
    let classPK: Int64
    let className: String
    let stepCount: Int32
    let score: Double
    
    var resultRating: RatingEntry?
    
    init(screenlet: BaseScreenlet, classPK: Int64, className: String, score: Double, stepCount: Int32) {
        self.classPK = classPK
        self.className = className
        self.stepCount = stepCount
        self.score = score
        super.init(screenlet: screenlet)
    }
    
    public override func start() -> Bool {
        let session = SessionContext.createSessionFromCurrentSession()
        session?.callback = self
        
        let service = LRScreensratingsentryService_v70(session: session)
        
        do {
            try service.updateRatingEntryWithClassPK(classPK, className: className, score: score, stepCount: stepCount)
            return true
        } catch {
            return false
        }
    }
    
    public func onFailure(error: NSError!) {
        self.onFailure?(error)
    }
    
    public func onSuccess(result: AnyObject!) {
        resultRating = RatingEntry(attributes: result as! [String: AnyObject])
        self.onSuccess?()
    }
    
}
