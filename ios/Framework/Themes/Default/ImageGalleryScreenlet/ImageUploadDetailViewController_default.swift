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

public class ImageUploadDetailViewController_default: UIViewController {

	public var imageUploadDetailview: ImageUploadDetailViewBase?

	//MARK: Initializers

	public init(imageUploadDetailview: ImageUploadDetailViewBase) {
		super.init(nibName: nil, bundle: nil)
		self.imageUploadDetailview = imageUploadDetailview
	}
	
	public required init?(coder aDecoder: NSCoder) {
		super.init(coder: aDecoder)
	}

	//MARK: UIViewController

    override public func viewDidLoad() {
        super.viewDidLoad()

		edgesForExtendedLayout = .None

		addNavBarButtons()

		addImageUploadView()
    }


	//MARK: Public methods

	public func addNavBarButtons() {
		let uploadButton = UIBarButtonItem(
				title: LocalizedString("default",key: "imagegallery-upload", obj: self),
				style: .Plain,
				target: self,
				action: #selector(startUploadClick))

		let cancelButton = UIBarButtonItem(
				title: LocalizedString("default",key: "imagegallery-cancel", obj: self),
				style: .Plain,
				target: self,
				action: #selector(cancelClick))

		navigationItem.rightBarButtonItem = uploadButton
		navigationItem.leftBarButtonItem = cancelButton
	}

	public func addImageUploadView() {
		imageUploadDetailview?.frame = view.frame
		view.addSubview(imageUploadDetailview!)

		imageUploadDetailview!.autoresizingMask = [.FlexibleWidth, .FlexibleHeight]
	}

	public func startUploadClick() {
		dismissViewControllerAnimated(true) {
			self.imageUploadDetailview?.startUpload()
		}
	}

	public func cancelClick() {
		dismissViewControllerAnimated(true) {}
	}
}
