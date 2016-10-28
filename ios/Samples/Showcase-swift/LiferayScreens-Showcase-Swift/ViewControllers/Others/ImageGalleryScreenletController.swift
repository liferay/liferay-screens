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

	
	//MARK: IBOutlet
	
	@IBOutlet weak var screenlet: ImageGalleryScreenlet? {
		didSet {
			screenlet?.delegate = self
			screenlet?.mimeTypes = LiferayServerContext.stringPropertyForKey("imageGalleryMimeType")
			screenlet?.repositoryId =
				LiferayServerContext.longPropertyForKey("imageGalleryRepositoryId")
			screenlet?.folderId =
				LiferayServerContext.longPropertyForKey("imageGalleryFolderId")
		}
	}

	
	//MARK: IBAction

	@IBAction func segmentedControlValueChanged(sender: UISegmentedControl) {

		switch sender.selectedSegmentIndex {
			case 0:
				screenlet?.themeName = "default"
			case 1:
				screenlet?.themeName = "default-slideshow"
			case 2:
				screenlet?.themeName = "default-list"
			default:
				break
		}

		screenlet?.loadList()
	}

	@IBAction func startUpload() {
		screenlet?.startMediaSelectorAndUpload()
	}

	
	// MARK: ImageGalleryScreenletDelegate

	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageEntryDeleted imageEntry: ImageEntry) {
		LiferayLogger.logDelegateMessage(args: imageEntry)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet, onImageUploaded image: ImageEntry) {
		LiferayLogger.logDelegateMessage(args: image)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet, onImageUploadError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet, onImageEntriesError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageEntryDeleteError error: NSError) {
		LiferayLogger.logDelegateMessage(args: error)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageEntrySelected imageEntry: ImageEntry) {
		LiferayLogger.logDelegateMessage(args: imageEntry)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageEntriesResponse imageEntries: [ImageEntry]) {
		LiferayLogger.logDelegateMessage(args: imageEntries)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageUploadStart imageEntryUpload: ImageEntryUpload) {
		LiferayLogger.logDelegateMessage(args: imageEntryUpload)
	}
	
	public func screenlet(screenlet: ImageGalleryScreenlet,
	                      onImageUploadProgress imageEntryUpload: ImageEntryUpload,
	                      totalBytesSent: UInt64,
	                      totalBytesToSend: UInt64) {
		LiferayLogger.logDelegateMessage(args: imageEntryUpload)
	}
}
