//
//  UIView.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 05/09/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import UIKit

extension UIView {
    
    func animatedChangeBackgroundColor(isHidden: Bool? = nil, color: UIColor = UIColor(white: 1.0, alpha: 0.7)) {
        if let hidden = isHidden {
            self.isHidden = hidden
        }
        UIView.animate(withDuration: 0.8 * 0.4,
                       delay: 0.0,
                       options: UIViewAnimationOptions.curveEaseOut,
                       animations: {
                        self.backgroundColor = color
        },
                       completion: nil)
    }
    
    func dismissChangeBackgroundColor(isHidden: Bool? = nil) {
        UIView.animate(withDuration: 0.8,
                       delay: 0.0,
                       usingSpringWithDamping: 100.0,
                       initialSpringVelocity: 0.6,
                       options: [.beginFromCurrentState, .allowUserInteraction, .overrideInheritedOptions, .curveEaseOut],
                       animations: {
                        self.backgroundColor = UIColor.clear

        },
                       completion: {(finished) in
                        if let hidden = isHidden {
                            self.isHidden = hidden
                        }
        })
    }
    
    func animatedChangeBackgroundColor(view: UIView, isUserInteraccionEnable: Bool? = nil, color: UIColor = UIColor(white: 1.0, alpha: 0.7)) {
        if let userInteraccion = isUserInteraccionEnable {
            view.isUserInteractionEnabled = userInteraccion
        }
        UIView.animate(withDuration: 0.8 * 0.4,
                       delay: 0.0,
                       options: UIViewAnimationOptions.curveEaseOut,
                       animations: {
                        view.backgroundColor = color
        },
                       completion: nil)
    }
    
    func dismissChangeBackgroundColor(view: UIView, isUserInteraccionEnable: Bool? = nil) {
        UIView.animate(withDuration: 0.8,
                       delay: 0.0,
                       usingSpringWithDamping: 100.0,
                       initialSpringVelocity: 0.6,
                       options: [.beginFromCurrentState, .allowUserInteraction, .overrideInheritedOptions, .curveEaseOut],
                       animations: {
                        view.backgroundColor = UIColor.clear
                        
        },
                       completion: {(finished) in
                        if let userInteraccion = isUserInteraccionEnable {
                            view.isUserInteractionEnabled = userInteraccion
                        }
        }
        )
    }

}
