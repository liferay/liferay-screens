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
