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

	@IBOutlet weak var scrollView: UIScrollView?
	@IBOutlet weak var pageControl: UIPageControl?

	override func viewDidLoad() {
		super.viewDidLoad()
		scrollView?.delegate = self
	}

	override func viewDidLayoutSubviews() {
		super.viewDidLayoutSubviews()
		if let scroll = scrollView {
			scroll.contentSize = CGSize(width: scroll.frame.width * 3, height: scroll.frame.width)
		}
	}

	@IBAction func nextAction(_ sender: AnyObject) {
		if let control = pageControl, let scroll = scrollView {
			if control.currentPage + 1 == control.numberOfPages {
				tourCompleted = true
				self.dismiss(animated: true, completion: nil)
			}
			else {
				let newX = CGFloat((control.currentPage + 1) * Int(scroll.frame.size.width))
				let newRect = CGRectMake(newX, y: scroll.contentOffset.y, size: scroll.frame.size)
				scroll.scrollRectToVisible(newRect, animated: true)
			}
		}
	}

	func scrollViewDidScroll(_ scrollView: UIScrollView) {
		let width = scrollView.frame.size.width
		let xPos = scrollView.contentOffset.x + 10

		pageControl?.currentPage = Int(xPos/width)
	}
}
