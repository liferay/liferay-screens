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

public class DefaultTextField: UITextField {
	
	//MARK: IBInspectable
	
	@IBInspectable public var defaultColor: UIColor = UIColor.lightGrayColor()
	
	@IBInspectable public var highlightColor: UIColor = DefaultThemeBasicBlue

	@IBInspectable public var padding: CGFloat = 15
	
	@IBInspectable public var leftImage: UIImage? {
		didSet {
			if let image = leftImage {
				
				let icon = UIImageView(image: image)
				
				icon.frame = CGRectMake(0, 0, self.frame.height, image.size.height)
				icon.contentMode = .Center
				
				self.leftViewMode = .Always
				self.leftView = icon
			}
		}
	}
	
	
	//MARK: Initializers
	
	override init(frame: CGRect) {
		super.init(frame: frame)
		
		setup()
	}
	
	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
		
		setup()
	}
	
	public override func prepareForInterfaceBuilder() {
		setup()
	}
	
	
	//MARK: Internal methods
	
	internal func setup() {
		self.layer.cornerRadius = 4.0
		self.layer.borderWidth = 1.0
		self.layer.borderColor = defaultColor.CGColor
	}
	
	
	//MARK: UITextField
	
	public override func resignFirstResponder() -> Bool {
		self.layer.borderColor = defaultColor.CGColor
		
		return super.resignFirstResponder()
	}
	
	public override func becomeFirstResponder() -> Bool {
		self.layer.borderColor = highlightColor.CGColor
		
		return super.becomeFirstResponder()
	}

	public override func textRectForBounds(bounds: CGRect) -> CGRect {
		if let _ = leftView {
			return super.textRectForBounds(bounds)
		}

		return CGRect(x: padding, y: 0, width: bounds.width - padding, height: bounds.height)
	}

	public override func editingRectForBounds(bounds: CGRect) -> CGRect {
		if let _ = leftView {
			return super.editingRectForBounds(bounds)
		}

		return textRectForBounds(bounds)
	}
}
