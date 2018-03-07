//
//  UIImage+GrayScale.swift
//  Hippo
//
//  Created by 전수열 on 10/9/14.
//  Copyright (c) 2014 Joyfl. All rights reserved.
//

import UIKit

extension UIImage {
	func grayScaleImage() -> UIImage {
		let imageRect = CGRect(x: 0, y: 0, width: self.size.width, height: self.size.height)
		let colorSpace = CGColorSpaceCreateDeviceGray()

		let width = Int(self.size.width)
		let height = Int(self.size.height)
		let context = CGContext(data: nil, width: width, height: height, bitsPerComponent: 8, bytesPerRow: 0, space: colorSpace, bitmapInfo: 0)
		context!.draw(self.cgImage!, in: imageRect)

		let imageRef = context!.makeImage()
		return UIImage(cgImage: imageRef!)
	}
}
