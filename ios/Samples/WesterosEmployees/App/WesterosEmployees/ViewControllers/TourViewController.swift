//
//  IntroductionViewController.swift
//  WesterosBank
//
//  Created by jmWork on 24/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
				let newRect = CGRect(x: newX, y: scroll.contentOffset.y, size: scroll.frame.size)
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
