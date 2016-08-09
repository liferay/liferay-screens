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
import LiferayScreens
import UIKit


public class ImageGalleryScreenletController : UIViewController, ImageGalleryScreenletDelegate {

	@IBOutlet weak var screenlet: ImageGalleryScreenlet!

    public override func viewDidLoad() {
        super.viewDidLoad()

		let startUploadButton = UIBarButtonItem(
				barButtonSystemItem: .Add,
				target: self,
				action: #selector(startUpload))

		navigationItem.rightBarButtonItem = startUploadButton

		screenlet.delegate = self
    }

	@IBAction func segmentedControlValueChanged(sender: UISegmentedControl) {

		switch sender.selectedSegmentIndex {
		case 0:
			screenlet.themeName = "default"
		case 1:
			screenlet.themeName = "default-slideshow"
		case 2:
			screenlet.themeName = "default-list"
		default:
			break
		}

		screenlet.loadList()
	}

	public func startUpload() {
		screenlet.startMediaSelectorAndUpload()
	}

	// MARK: ImageGalleryScreenletDelegate

	public func screenlet(screenlet: ImageGalleryScreenlet, onImageEntriesError error: NSError) {
		print("Error: \(error)")
	}

	public func screenlet(
			screenlet: ImageGalleryScreenlet,
			onImageEntrySelected imageEntry: ImageEntry) {

		print("Image selected: \(imageEntry.imageUrl)")
	}

	public func screenlet(
			screenlet: ImageGalleryScreenlet,
			onImageUploadStart image: ImageEntryUpload) {

		print("Image upload started \(image.title)")
	}

	public func screenlet(screenlet: ImageGalleryScreenlet, onImageUploadError error: NSError) {
		print("Error uploading image\(error)")
	}

	public func screenlet(
			screenlet: ImageGalleryScreenlet,
			onImageUploadProgress image: ImageEntryUpload,
			totalBytesSent: UInt64,
			totalBytesToSend: UInt64) {
		
		let percent = Float(totalBytesSent) / Float(totalBytesToSend) * 100.0
		print("\(image.title) => \(totalBytesSent) : \(totalBytesToSend) -> \(percent)")
	}

	public func screenlet(screenlet: ImageGalleryScreenlet, onImageUploaded image: ImageEntry) {
		print("\(image.title) uploaded")
	}
}