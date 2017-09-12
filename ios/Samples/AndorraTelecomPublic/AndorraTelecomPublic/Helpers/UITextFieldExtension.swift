//
//  UITextFieldExtension.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 19/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import UIKit

@IBDesignable
class UITextFieldExtended: UITextField {
    
    @IBInspectable var inset: CGFloat = 0
    @IBInspectable var leftImage : UIImage? {
        didSet {
            if let image = leftImage{
                isImageSelected = true
                leftViewMode = .always
                let imageView = UIImageView(frame: CGRect(x: 15, y: 0, width: 20, height: 20))
                imageView.image = image
                imageView.tintColor = tintColor
                let view = UIView(frame : CGRect(x: 0, y: 0, width: 25, height: 20))
                view.addSubview(imageView)
                leftView = view
            }else {
                isImageSelected = false
                leftViewMode = .never
            }
            
        }
    }
    var isImageSelected: Bool?
    
    override func textRect(forBounds bounds: CGRect) -> CGRect {
        if isImageSelected == true {
            return bounds.insetBy(dx: 35 + inset, dy: 0)
        } else {
            return bounds.insetBy(dx: inset, dy: 0)
        }
    }
    
    override func editingRect(forBounds bounds: CGRect) -> CGRect {
        return textRect(forBounds: bounds)
    }
    
}
