//
//  IntroductionViewController.swift
//  WesterosBank
//
//  Created by jmWork on 24/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
		page2.frame = CGRectMake(scrollView.frame.size.width * 1, scrollView.frame.origin.y, scrollView.frame.size)
		page3.frame = CGRectMake(scrollView.frame.size.width * 2, scrollView.frame.origin.y, scrollView.frame.size)
    }

	@IBAction func nextAction(sender: AnyObject) {
		if pageControl.currentPage + 1 == pageControl.numberOfPages {
			tourCompleted = true
			self.dismissViewControllerAnimated(true, completion: nil)
		}
		else {
			let newX = CGFloat((pageControl.currentPage + 1) * Int(scrollView.frame.size.width.native))
			let newRect = CGRectMake(newX, scrollView.contentOffset.y, scrollView.frame.size)
			scrollView.scrollRectToVisible(newRect, animated: true)
		}
	}

	func scrollViewDidScroll(scrollView: UIScrollView) {
		let width = scrollView.frame.size.width
		let xPos = scrollView.contentOffset.x + 10

		pageControl.currentPage = Int(xPos/width)
	}

}
