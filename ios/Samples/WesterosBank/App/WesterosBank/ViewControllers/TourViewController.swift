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

class TourViewController: UIViewController, UIScrollViewDelegate {

	@IBOutlet weak var scrollView: UIScrollView!
	@IBOutlet weak var contentView: UIView!
	@IBOutlet weak var pageControl: UIPageControl!

	@IBOutlet weak var page1: UIView!
	@IBOutlet weak var page2: UIView!
	@IBOutlet weak var page3: UIView!

    override func viewDidLoad() {
        super.viewDidLoad()

		scrollView.contentSize = contentView.frame.size

		page1.frame = scrollView.frame
		page2.frame = CGRectMake(scrollView.frame.size.width * 1, y: scrollView.frame.origin.y, size: scrollView.frame.size)
		page3.frame = CGRectMake(scrollView.frame.size.width * 2, y: scrollView.frame.origin.y, size: scrollView.frame.size)
    }

	@IBAction func nextAction(_ sender: AnyObject) {
		if pageControl.currentPage + 1 == pageControl.numberOfPages {
			tourCompleted = true
			self.dismiss(animated: true, completion: nil)
		}
		else {
			let newX = CGFloat((pageControl.currentPage + 1) * Int(scrollView.frame.width))
			let newRect = CGRectMake(newX, y: scrollView.contentOffset.y, size: scrollView.frame.size)
			scrollView.scrollRectToVisible(newRect, animated: true)
		}
	}

	func scrollViewDidScroll(_ scrollView: UIScrollView) {
		let width = scrollView.frame.size.width
		let xPos = scrollView.contentOffset.x + 10

		pageControl.currentPage = Int(xPos/width)
	}

}
