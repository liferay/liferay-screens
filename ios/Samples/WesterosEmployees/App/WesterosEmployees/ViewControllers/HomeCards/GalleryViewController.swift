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

class GalleryViewController: CardViewController, ImageGalleryScreenletDelegate, CardDeckDataSource, CardDeckDelegate {

	var selectedImageEntry: ImageEntry?

	var uploadImageViewController: UploadImageViewController? {
		didSet {
			addChildViewController(uploadImageViewController!)
			uploadImageViewController?.onImageSelected = self.onImageSelected
		}
	}

	var loaded: Bool = false

	// MARK: Outlets

	@IBOutlet weak var imageGalleryScreenlet: ImageGalleryScreenlet? {
		didSet {
			imageGalleryScreenlet?.delegate = self
			imageGalleryScreenlet?.presentingViewController = self
            imageGalleryScreenlet?.offlinePolicy = "cache-first"

			imageGalleryScreenlet?.repositoryId = LiferayServerContext.groupId
			imageGalleryScreenlet?.folderId =
				LiferayServerContext.longPropertyForKey("galleryFolderId")
		}
	}

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}

	// MARK: CardViewController

	override func pageWillDisappear() {
		hideUploadCard()
	}

	override func pageWillAppear() {
		if !loaded {
			imageGalleryScreenlet?.loadList()
			loaded = true
		}
	}

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		uploadImageViewController = UploadImageViewController()
	}

	// MARK: Initializers

	convenience init() {
		self.init(nibName: "GalleryViewController", bundle: nil)
	}

	// MARK: Private methods

	func onImageSelected(_ image: UIImage) {
		let title = "westeros-\(UUID().uuidString).png"
		let imageUpload = ImageEntryUpload(image: image, title: title)
		self.imageGalleryScreenlet?.showDetailUploadView(imageUpload)
	}

	func hideUploadCard() {
		if let uploadCard = cardDeck?.cards[safe: 0] {
			if uploadCard.pageCount > 1 {
				uploadCard.removePageAtIndex(1)
				uploadCard.moveLeft()
			}
			uploadCard.changeToState(.minimized)
		}
	}

	// MARK: ImageGalleryScreenletDelegate

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntrySelected imageEntry: ImageEntry) {
		self.selectedImageEntry = imageEntry
		cardView?.moveRight()
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadDetailViewCreated uploadView: ImageUploadDetailViewBase) -> Bool {
		self.cardDeck?.cards[safe: 0]?.addPage(uploadView)
		self.cardDeck?.cards[safe: 0]?.moveRight()
		return true
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadStart image: ImageEntryUpload) {
		hideUploadCard()
	}

	// MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 1
	}

	func cardDeck(_ cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Upload image"
	}

	func cardDeck(_ cardDeck: CardDeckView, controllerForCard position: CardPosition) -> CardViewController? {
		return uploadImageViewController
	}

	// MARK: CardDeckDelegate

	func cardDeck(_ cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if let firstCardDeck = self.cardView?.superview {
			card.normalHeight = firstCardDeck.frame.height * 0.4
		}
	}

	func cardDeck(_ cardDeck: CardDeckView, colorForCardIndex index: Int) -> UIColor? {
		return DefaultResources.OddColorBackground
	}

	func cardDeck(_ cardDeck: CardDeckView, colorForButtonIndex index: Int) -> UIColor? {
		return DefaultResources.EvenColorBackground
	}

	func cardDeck(_ cardDeck: CardDeckView, buttonImageForCardIndex index: Int) -> UIImage? {
		return UIImage(named: "icon_DOWN_W")
	}
}
