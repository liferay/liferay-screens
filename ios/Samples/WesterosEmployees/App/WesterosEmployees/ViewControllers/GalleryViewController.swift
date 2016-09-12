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
import LiferayScreens

class GalleryViewController: CardViewController, ImageGalleryScreenletDelegate {

	var selectedImageEntry: ImageEntry?

	//MARK: Outlets

	@IBOutlet weak var imageGalleryScreenlet: ImageGalleryScreenlet? {
		didSet {
			imageGalleryScreenlet?.delegate = self
			imageGalleryScreenlet?.presentingViewController = self
		}
	}


	//MARK: Init methods

	convenience init() {
		self.init(nibName: "GalleryViewController", bundle: nil)
	}


	//MARK: Public methods

	func load() {
		imageGalleryScreenlet?.loadList()
	}

	func startGalleryUpload() {
		imageGalleryScreenlet?.startMediaSelectorAndUpload()
	}


	//MARK: CardViewController

	override var cardView: CardView? {
		didSet {
			cardView?.addPage(self.view)
			cardView?.presentingController = self
			cardView?.secondaryButton.addTarget(self, action: #selector(GalleryViewController.startGalleryUpload), forControlEvents: .TouchUpInside)
		}
	}


	//MARK: ImageGalleryScreenletDelegate

	func screenlet(screenlet: ImageGalleryScreenlet, onImageEntrySelected imageEntry: ImageEntry) {
		self.selectedImageEntry = imageEntry
		cardView?.moveRight()
	}
}
