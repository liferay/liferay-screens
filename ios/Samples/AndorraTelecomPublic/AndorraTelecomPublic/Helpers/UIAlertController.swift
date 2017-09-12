//
//  UIAlertController.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 04/09/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import UIKit
import Hokusai

extension Hokusai {
    
}

extension UIAlertController {

    func colorfull() {
        let subview = (self.view.subviews.first?.subviews.first?.subviews.first!)! as UIView
        
        for view in subview.subviews {
            view.backgroundColor = UIColor.pink
        }
        
        self.view.tintColor = UIColor.lightPurple
    }

}
