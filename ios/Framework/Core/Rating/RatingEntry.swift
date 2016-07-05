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


@objc public class RatingEntry : NSObject {
    
    public let attributes :[String:AnyObject]
    
    public var totalCount: Int {
        return attributes["totalCount"]! as! Int
    }
    
    public var average: Double {
        return attributes["average"]! as! Double
    }
    
    public var userScore: Double {
        return attributes["userScore"]! as! Double
    }
    
    public var classPK: Int64 {
        return (attributes["classPK"]! as! String).asLong!
    }
    
    public var className: String {
        return attributes["className"]! as! String
    }
    
    public var ratings: [Int] {
        return attributes["ratings"]! as! [Int]
    }
    
    //MARK: Init
    
    public init(attributes: [String:AnyObject]) {
        self.attributes = attributes
    }
    
}
